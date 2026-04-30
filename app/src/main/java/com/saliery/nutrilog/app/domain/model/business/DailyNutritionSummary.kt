package com.saliery.nutrilog.app.domain.model.business

data class DailyNutritionSummary(
    val totalCalories: Double,
    val totalProteins: Double,
    val totalFats: Double,
    val totalCarbs: Double
)