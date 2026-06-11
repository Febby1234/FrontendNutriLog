package com.chelsea.nutrilog.foodLog.models

import com.google.gson.annotations.SerializedName

data class Food(
    @SerializedName("food_id") val foodId: Int,
    @SerializedName("name") val name: String,
    @SerializedName("calories_per_serving") val caloriesPerServing: Double,
    @SerializedName("protein_g") val proteinG: Double,
    @SerializedName("fat_g") val fatG: Double,
    @SerializedName("carbs_g") val carbsG: Double
)

data class FoodProposal(
    @SerializedName("proposal_id") val proposalId: Int = 0,
    @SerializedName("proposed_food_name") val proposedFoodName: String = "",
    @SerializedName("proposed_calories") val proposedCalories: Double = 0.0,
    @SerializedName("status") val status: String = "",
    @SerializedName("submitted_by") val submittedBy: Int = 0,
    @SerializedName("created_at") val createdAt: String? = null
)

data class LogItem(
    val itemId: Int,
    val foodId: Int,
    val servingQuantity: Float,
    val consumedCalories: Double,
    val food: Food? = null
)

data class DailyLog(
    val logId: Int,
    val userId: Int,
    val date: String,
    val items: List<LogItem> = emptyList(),
    val totalCaloriesConsumed: Double = 0.0,
    val totalProtein: Double = 0.0,
    val totalFat: Double = 0.0,
    val totalCarbs: Double = 0.0
)

data class CreateFoodLogRequest(
    val foodId: Int,
    val servingQuantity: Float,
    val date: String
)

// Perbarui bagian request di file nutrilog/foodLog/models/FoodModels.kt
data class ProposeFoodRequest(
    @SerializedName("proposedFoodName") val proposedFoodName: String,
    @SerializedName("proposedCalories") val proposedCalories: Double,
    @SerializedName("proteinG") val proteinG: Double,
    @SerializedName("fatG") val fatG: Double,
    @SerializedName("carbsG") val carbsG: Double
)