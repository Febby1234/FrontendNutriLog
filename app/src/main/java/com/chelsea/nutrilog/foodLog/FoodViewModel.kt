package com.chelsea.nutrilog.foodLog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chelsea.nutrilog.foodLog.models.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import java.time.LocalDate

data class FoodUiState(
    val isLoading: Boolean = false,
    val foods: List<Food> = emptyList(),
    val searchResults: List<Food> = emptyList(),
    val todayLog: DailyLog? = null,
    val error: String? = null,
    val searchQuery: String = "",
    val successMessage: String? = null
)

@HiltViewModel
class FoodViewModel @Inject constructor(
    private val foodRepository: FoodRepository,
    private val foodService: FoodService
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(FoodUiState())
    val uiState: StateFlow<FoodUiState> = _uiState.asStateFlow()
    
    init {
        loadAllFoods()
        loadTodayLog()
    }
    
    private fun loadAllFoods() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            val result = foodRepository.getAllFoods()
            
            result.onSuccess { foods ->
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    foods = foods,
                    error = null
                )
            }
            result.onFailure { exception ->
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = exception.message ?: "Failed to load foods"
                )
            }
        }
    }
    
    fun searchFood(query: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isLoading = true,
                searchQuery = query,
                error = null
            )
            
            val result = foodRepository.searchFood(query)
            
            result.onSuccess { foods ->
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    searchResults = foods
                )
            }
            result.onFailure { exception ->
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = exception.message ?: "Search failed"
                )
            }
        }
    }
    
    fun loadTodayLog() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            val result = foodRepository.getTodayLog()
            
            result.onSuccess { log ->
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    todayLog = log,
                    error = null
                )
            }
            result.onFailure { exception ->
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = exception.message
                )
            }
        }
    }

    fun addFoodLog(foodId: Int, servingQuantity: Float) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)

            // Format tanggal yang aman untuk semua versi Android
            val dateFormat = java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault())
            val todayDate = dateFormat.format(java.util.Date())

            val request = CreateFoodLogRequest(
                foodId = foodId,
                servingQuantity = servingQuantity,
                date = todayDate
            )

            val result = foodRepository.addFoodLog(request)

            result.onSuccess { log ->
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    todayLog = log,
                    error = null,
                    successMessage = "Food logged successfully!"
                )
            }
            result.onFailure { exception ->
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = exception.message ?: "Failed to add food log"
                )
            }
        }
    }

    // Perbarui fungsi proposeFoodItem di dalam FoodViewModel.kt
    fun proposeFoodItem(foodName: String, calories: Double, protein: Double, fat: Double, carbs: Double) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null, successMessage = null)

            val request = ProposeFoodRequest(
                proposedFoodName = foodName,
                proposedCalories = calories,
                proteinG = protein,
                fatG = fat,
                carbsG = carbs
            )

            val result = foodRepository.proposeFoodItem(request) // Pastikan repository memanggil apiService.proposeFoodItem

            result.onSuccess {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = null,
                    successMessage = "Food proposal submitted successfully!"
                )
            }
            result.onFailure { exception ->
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = exception.message ?: "Failed to propose food"
                )
            }
        }
    }
    fun clearSuccessMessage() {
        _uiState.value = _uiState.value.copy(successMessage = null)
    }
}
