package com.chelsea.nutrilog.foodLog

import com.chelsea.nutrilog.core.api.ApiService
import com.chelsea.nutrilog.foodLog.models.*
import javax.inject.Inject

class FoodRepository @Inject constructor(
    private val apiService: ApiService
) {
    suspend fun searchFood(query: String): Result<List<Food>> = try {
        Result.success(apiService.searchFood(query))
    } catch (e: Exception) {
        Result.failure(e)
    }
    
    suspend fun getAllFoods(): Result<List<Food>> = try {
        Result.success(apiService.getAllFoods())
    } catch (e: Exception) {
        Result.failure(e)
    }
    
    suspend fun getFoodById(id: Int): Result<Food> = try {
        Result.success(apiService.getFoodById(id))
    } catch (e: Exception) {
        Result.failure(e)
    }
    
    suspend fun proposeFoodItem(request: ProposeFoodRequest): Result<FoodProposal> = try {
        Result.success(apiService.proposeFoodItem(request))
    } catch (e: Exception) {
        Result.failure(e)
    }
    
    suspend fun addFoodLog(request: CreateFoodLogRequest): Result<DailyLog> = try {
        Result.success(apiService.addFoodLog(request))
    } catch (e: Exception) {
        Result.failure(e)
    }
    
    suspend fun getTodayLog(): Result<DailyLog> = try {
        Result.success(apiService.getTodayLog())
    } catch (e: Exception) {
        Result.failure(e)
    }
    
    suspend fun getLogByDate(date: String): Result<DailyLog> = try {
        Result.success(apiService.getLogByDate(date))
    } catch (e: Exception) {
        Result.failure(e)
    }
    
    suspend fun deleteLog(logId: Int): Result<Unit> = try {
        Result.success(apiService.deleteLog(logId))
    } catch (e: Exception) {
        Result.failure(e)
    }
}
