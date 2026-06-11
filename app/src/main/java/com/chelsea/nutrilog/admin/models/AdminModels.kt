package com.chelsea.nutrilog.admin.models

import com.chelsea.nutrilog.foodLog.models.FoodProposal

data class AdminDashboardState(
    val pendingProposals: List<FoodProposal> = emptyList(),
    val approvedCount: Int = 0,
    val rejectedCount: Int = 0
)
