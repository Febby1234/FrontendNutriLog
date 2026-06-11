package com.chelsea.nutrilog.dashboard

import com.chelsea.nutrilog.dashboard.models.*
import javax.inject.Inject
import kotlin.math.pow

/**
 * NutritionService menangani business logic terkait nutrisi dan dashboard
 * - Kalkulasi BMR (Basal Metabolic Rate)
 * - Konversi data untuk chart
 * - Analisis tren nutrisi
 */
class NutritionService @Inject constructor() {
    
    /**
     * Hitung BMR menggunakan Mifflin-St Jeor formula
     * BMR = (10 × weight) + (6.25 × height) - (5 × age) + sex_offset
     * sex_offset: +5 untuk male, -161 untuk female
     */
    fun calculateBMR(profile: HealthProfileDTO): Double {
        val sexOffset = if (profile.gender.lowercase() == "male") 5.0 else -161.0
        return (10 * profile.weightKg) + (6.25 * profile.heightCm) - (5 * profile.age) + sexOffset
    }
    
    /**
     * Hitung TDEE (Total Daily Energy Expenditure)
     * TDEE = BMR × Activity Factor
     * Asumsi moderate activity = 1.55
     */
    fun calculateTDEE(bmr: Double, activityFactor: Double = 1.55): Double {
        return bmr * activityFactor
    }
    
    /**
     * Kalkulasi progress terhadap target
     */
    fun calculateCalorieProgress(consumed: Double, target: Double): Int {
        return ((consumed / target) * 100).toInt().coerceIn(0, 200)
    }
    
    /**
     * Konversi DailyLog ke NutritionDataPoint untuk chart
     */
    fun convertToNutritionDataPoint(log: com.chelsea.nutrilog.foodLog.models.DailyLog): NutritionDataPoint {
        var protein = 0.0
        var fat = 0.0
        var carbs = 0.0
        
        log.items.forEach { item ->
            item.food?.let { food ->
                val multiplier = item.servingQuantity
                protein += food.proteinG * multiplier
                fat += food.fatG * multiplier
                carbs += food.carbsG * multiplier
            }
        }
        
        return NutritionDataPoint(
            date = log.date,
            calories = log.totalCaloriesConsumed,
            protein = protein,
            fat = fat,
            carbs = carbs
        )
    }
    
    /**
     * Kalkulasi rata-rata kalori dari list DailyLog
     */
    fun calculateAverageCalories(logs: List<com.chelsea.nutrilog.foodLog.models.DailyLog>): Double {
        return if (logs.isEmpty()) 0.0 
        else logs.map { it.totalCaloriesConsumed }.average()
    }
    
    /**
     * Cek apakah user mencapai target kalori
     */
    fun isCalorieTargetMet(consumed: Double, target: Double, tolerancePercent: Double = 10.0): Boolean {
        val tolerance = target * (tolerancePercent / 100.0)
        return consumed in (target - tolerance)..(target + tolerance)
    }
}
