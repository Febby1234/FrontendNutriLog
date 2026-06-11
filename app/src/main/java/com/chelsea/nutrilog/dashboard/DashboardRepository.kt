package com.chelsea.nutrilog.dashboard

import com.chelsea.nutrilog.core.api.ApiService
import com.chelsea.nutrilog.dashboard.models.*
import com.chelsea.nutrilog.foodLog.models.DailyLog
import javax.inject.Inject

class DashboardRepository @Inject constructor(
    private val apiService: ApiService
) {
    suspend fun getDashboardSummary(): Result<DashboardSummary> = try {
        Result.success(apiService.getDashboardSummary())
    } catch (e: Exception) {
        Result.failure(e)
    }
    
    suspend fun getNutritionData(days: Int = 7): Result<List<DailyLog>> = try {
        Result.success(apiService.getNutritionData(days))
    } catch (e: Exception) {
        Result.failure(e)
    }
}
