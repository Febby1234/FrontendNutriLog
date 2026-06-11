package com.chelsea.nutrilog.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chelsea.nutrilog.dashboard.models.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class DashboardUiState(
    val isLoading: Boolean = false,
    val summary: DashboardSummary? = null,
    val nutritionData: List<NutritionDataPoint> = emptyList(),
    val error: String? = null,
    val selectedDaysFilter: Int = 7
)

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val dashboardRepository: DashboardRepository,
    private val nutritionService: NutritionService
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(DashboardUiState())
    val uiState: StateFlow<DashboardUiState> = _uiState.asStateFlow()
    
    init {
        loadDashboardData()
    }
    
    fun loadDashboardData() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            val result = dashboardRepository.getDashboardSummary()
            
            result.onSuccess { summary ->
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    summary = summary
                )
                loadNutritionDataForDays(_uiState.value.selectedDaysFilter)
            }
            result.onFailure { exception ->
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = exception.message ?: "Failed to load dashboard"
                )
            }
        }
    }
    
    fun refreshDashboard() {
        loadDashboardData()
    }
    
    fun loadNutritionDataForDays(days: Int) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isLoading = true,
                selectedDaysFilter = days
            )
            
            val result = dashboardRepository.getNutritionData(days)
            
            result.onSuccess { logs ->
                val nutritionData = logs.map { log ->
                    nutritionService.convertToNutritionDataPoint(log)
                }
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    nutritionData = nutritionData
                )
            }
            result.onFailure { exception ->
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = exception.message ?: "Failed to load nutrition data"
                )
            }
        }
    }
}
