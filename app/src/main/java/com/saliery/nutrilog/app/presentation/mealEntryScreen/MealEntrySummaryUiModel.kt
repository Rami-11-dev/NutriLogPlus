package com.saliery.nutrilog.app.presentation.mealEntryScreen

data class MealEntrySummaryUiModel(
    val totalCalories: Double = 0.0,
    val totalProteins: Double = 0.0,
    val totalFats: Double = 0.0,
    val totalCarbs: Double = 0.0,

    val caloriesTarget: Double? = null,
    val proteinTarget: Double? = null,
    val fatTarget: Double? = null,
    val carbTarget: Double? = null
)
