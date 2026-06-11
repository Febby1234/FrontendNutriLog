package com.chelsea.nutrilog.foodLog

import com.chelsea.nutrilog.foodLog.models.Food
import com.chelsea.nutrilog.foodLog.models.DailyLog
import javax.inject.Inject

/**
 * FoodService menangani business logic terkait makanan
 * - Validasi data makanan
 * - Kalkulasi nutrisi
 * - Formatting data untuk UI
 */
class FoodService @Inject constructor() {
    
    /**
     * Validasi apakah food item valid
     */
    fun validateFood(food: Food): Boolean {
        return food.name.isNotBlank() &&
               food.caloriesPerServing > 0 &&
               food.proteinG >= 0 &&
               food.fatG >= 0 &&
               food.carbsG >= 0
    }
    
    /**
     * Format food untuk tampilan
     */
    fun formatFoodForDisplay(food: Food): String {
        return "${food.name} - ${food.caloriesPerServing.toInt()} kcal"
    }
    
    /**
     * Kalkulasi total nutrisi dari daily log
     */
    fun calculateDailyNutrition(dailyLog: DailyLog): Map<String, Double> {
        var totalProtein = 0.0
        var totalFat = 0.0
        var totalCarbs = 0.0
        
        dailyLog.items.forEach { item ->
            item.food?.let { food ->
                val multiplier = item.servingQuantity
                totalProtein += food.proteinG * multiplier
                totalFat += food.fatG * multiplier
                totalCarbs += food.carbsG * multiplier
            }
        }
        
        return mapOf(
            "protein" to totalProtein,
            "fat" to totalFat,
            "carbs" to totalCarbs,
            "calories" to dailyLog.totalCaloriesConsumed
        )
    }
}
