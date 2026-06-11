package com.chelsea.nutrilog.admin

import com.chelsea.nutrilog.foodLog.models.FoodProposal
import javax.inject.Inject

/**
 * AdminService menangani business logic untuk validasi makanan
 * - Validasi proposal makanan
 * - Format data untuk admin view
 */
class AdminService @Inject constructor() {
    
    /**
     * Validasi apakah proposal layak untuk disetujui
     */
    fun isProposalValid(proposal: FoodProposal): Boolean {
        return proposal.proposedFoodName.isNotBlank() &&
               proposal.proposedCalories > 0 &&
               proposal.status == "PENDING"
    }
    
    /**
     * Validasi kalori untuk realistis
     */
    fun isCalorieRealistic(calories: Double): Boolean {
        return calories in 1.0..2000.0 // Realistic range untuk food items
    }
    
    /**
     * Format proposal untuk tampilan admin
     */
    fun formatProposalForDisplay(proposal: FoodProposal): String {
        return "${proposal.proposedFoodName} (${proposal.proposedCalories.toInt()} kcal) - ${proposal.status}"
    }
}
