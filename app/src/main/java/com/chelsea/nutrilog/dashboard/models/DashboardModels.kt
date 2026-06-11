package com.chelsea.nutrilog.dashboard.models

import com.chelsea.nutrilog.foodLog.models.DailyLog

data class DashboardSummary(
    val todayCalories: Double,
    val targetCalories: Double,
    val bmr: Double,
    val caloriesRemaining: Double,
    val healthProfile: HealthProfileDTO,
    val macronutrients: MacronutrientsDTO,
    val todayItems: List<FoodItemDTO> = emptyList(),
    val recentLogs: List<DailyLog> = emptyList()
)

data class HealthProfileDTO(
    val profileId: Int,
    val age: Int,
    val gender: String,
    val weightKg: Float,
    val heightCm: Float,
    val dailyTargetCalories: Double,
    val bmrResult: Double
)

data class MacronutrientsDTO(
    val protein: Double = 0.0,
    val fat: Double = 0.0,
    val carbs: Double = 0.0
)

data class FoodItemDTO(
    val itemId: Int,
    val foodId: Int,
    val name: String,
    val servingQuantity: Float,
    val consumedCalories: Double,
    val caloriesPerServing: Double,
    val proteinG: Double,
    val fatG: Double,
    val carbsG: Double
)

data class NutritionDataPoint(
    val date: String,
    val calories: Double,
    val protein: Double,
    val fat: Double,
    val carbs: Double
)
