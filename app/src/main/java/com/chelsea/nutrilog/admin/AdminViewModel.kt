package com.chelsea.nutrilog.admin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chelsea.nutrilog.admin.models.AdminDashboardState
import com.chelsea.nutrilog.foodLog.models.FoodProposal
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class AdminUiState(
    val isLoading: Boolean = false,
    val pendingProposals: List<FoodProposal> = emptyList(),
    val error: String? = null,
    val successMessage: String? = null
)

@HiltViewModel
class AdminViewModel @Inject constructor(
    private val adminRepository: AdminRepository,
    private val adminService: AdminService
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(AdminUiState())
    val uiState: StateFlow<AdminUiState> = _uiState.asStateFlow()
    
    init {
        loadPendingProposals()
    }
    
    fun loadPendingProposals() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            val result = adminRepository.getPendingFoods()
            
            result.onSuccess { proposals ->
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    pendingProposals = proposals
                )
            }
            result.onFailure { exception ->
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = exception.message ?: "Failed to load proposals"
                )
            }
        }
    }
    
    fun approveProposal(proposalId: Int) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            val result = adminRepository.approveFoodProposal(proposalId)
            
            result.onSuccess { _ ->
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    successMessage = "Proposal approved!"
                )
                loadPendingProposals()
            }
            result.onFailure { exception ->
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = exception.message ?: "Failed to approve proposal"
                )
            }
        }
    }
    
    fun rejectProposal(proposalId: Int) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            val result = adminRepository.rejectFoodProposal(proposalId)
            
            result.onSuccess { _ ->
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    successMessage = "Proposal rejected!"
                )
                loadPendingProposals()
            }
            result.onFailure { exception ->
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = exception.message ?: "Failed to reject proposal"
                )
            }
        }
    }
    
    fun clearMessages() {
        _uiState.value = _uiState.value.copy(
            successMessage = null,
            error = null
        )
    }
}
