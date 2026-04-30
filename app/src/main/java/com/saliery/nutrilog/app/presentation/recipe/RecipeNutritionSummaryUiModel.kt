package com.saliery.nutrilog.app.presentation.recipe

data class RecipeNutritionSummaryUiModel(
    val totalCalories: Double = 0.0,
    val totalProteins: Double = 0.0,
    val totalFats: Double = 0.0,
    val totalCarbs: Double = 0.0,
    val perServingCalories: Double = 0.0,
    val perServingProteins: Double = 0.0,
    val perServingFats: Double = 0.0,
    val perServingCarbs: Double = 0.0
)
