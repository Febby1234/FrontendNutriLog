package com.chelsea.nutrilog.admin

import com.chelsea.nutrilog.core.api.ApiService
import com.chelsea.nutrilog.foodLog.models.FoodProposal
import javax.inject.Inject

class AdminRepository @Inject constructor(
    private val apiService: ApiService
) {
    suspend fun getPendingFoods(): Result<List<FoodProposal>> = try {
        Result.success(apiService.getPendingFoods())
    } catch (e: Exception) {
        Result.failure(e)
    }
    
    suspend fun approveFoodProposal(proposalId: Int): Result<FoodProposal> = try {
        Result.success(apiService.approveFoodProposal(proposalId))
    } catch (e: Exception) {
        Result.failure(e)
    }
    
    suspend fun rejectFoodProposal(proposalId: Int): Result<FoodProposal> = try {
        Result.success(apiService.rejectFoodProposal(proposalId))
    } catch (e: Exception) {
        Result.failure(e)
    }
}
