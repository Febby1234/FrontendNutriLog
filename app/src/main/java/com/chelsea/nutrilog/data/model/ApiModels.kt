package com.chelsea.nutrilog.data.model


import com.google.gson.annotations.SerializedName

// ============ AUTH MODELS ============

data class RegisterRequest(
    val email: String,
    val password: String,
    val name: String,
    val age: Int,
    val weight: Float,
    val height: Float,
    val gender: String
)

data class LoginRequest(
    val email: String,
    val password: String
)

data class AuthResponse(
    val message: String,
    val token: String,
    val user: UserData
)

data class UserData(
    @SerializedName("userId")
    val userId: Int,
    val email: String,
    val name: String,
    val age: Int,
    val weight: Float,
    val height: Float,
    val gender: String,
    val role: String,
    val bmr: Int,
    val dailyTargetCalories: Int
)

data class UserProfile(
    @SerializedName("userId")
    val userId: Int,
    val email: String,
    val name: String,
    val age: Int,
    val weight: Float,
    val height: Float,
    val gender: String,
    val role: String,
    val bmr: Int,
    val dailyTargetCalories: Int
)

// ============ FOOD MODELS ============

data class FoodItem(
    @SerializedName("food_id")
    val foodId: Int,
    val name: String,
    @SerializedName("calories_per_serving")
    val caloriesPerServing: Float,
    @SerializedName("protein_g")
    val proteinG: Float,
    @SerializedName("fat_g")
    val fatG: Float,
    @SerializedName("carbs_g")
    val carbsG: Float
)

// ============ LOG MODELS ============

data class AddFoodLogRequest(
    @SerializedName("foodId")
    val foodId: Int,
    @SerializedName("servingQuantity")
    val servingQuantity: Float,
    val date: String  // Format: "2024-01-15"
)

data class DailyLogResponse(
    @SerializedName("logId")
    val logId: Int?,
    @SerializedName("userId")
    val userId: Int,
    val date: String,
    val items: List<LogItem>,
    @SerializedName("totalCaloriesConsumed")
    val totalCaloriesConsumed: Float
)

data class LogItem(
    @SerializedName("itemId")
    val itemId: Int,
    @SerializedName("foodId")
    val foodId: Int,
    val name: String,
    @SerializedName("servingQuantity")
    val servingQuantity: Float,
    @SerializedName("consumedCalories")
    val consumedCalories: Float,
    @SerializedName("caloriesPerServing")
    val caloriesPerServing: Float,
    @SerializedName("proteinG")
    val proteinG: Float,
    @SerializedName("fatG")
    val fatG: Float,
    @SerializedName("carbsG")
    val carbsG: Float
)

// ============ DASHBOARD MODELS ============

data class DashboardSummary(
    @SerializedName("todayCalories")
    val todayCalories: Int,
    @SerializedName("targetCalories")
    val targetCalories: Int,
    val bmr: Int,
    @SerializedName("caloriesRemaining")
    val caloriesRemaining: Int,
    val macronutrients: Macronutrients,
    @SerializedName("healthProfile")
    val healthProfile: HealthProfile,
    @SerializedName("todayItems")
    val todayItems: List<LogItem>,
    @SerializedName("recentLogs")
    val recentLogs: List<RecentLog>
)

data class Macronutrients(
    val protein: Float,
    val fat: Float,
    val carbs: Float
)

data class HealthProfile(
    @SerializedName("profileId")
    val profileId: Int,
    val age: Int,
    val gender: String,
    @SerializedName("weightKg")
    val weightKg: Float,
    @SerializedName("heightCm")
    val heightCm: Float,
    @SerializedName("dailyTargetCalories")
    val dailyTargetCalories: Int,
    @SerializedName("bmrResult")
    val bmrResult: Int
)

data class RecentLog(
    @SerializedName("logId")
    val logId: Int,
    val date: String,
    @SerializedName("totalCalories")
    val totalCalories: Int,
    @SerializedName("itemCount")
    val itemCount: Int
)

data class MessageResponse(
    val message: String
)