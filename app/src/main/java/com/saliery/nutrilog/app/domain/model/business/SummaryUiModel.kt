package com.saliery.nutrilog.app.domain.model.business

data class SummaryUiModel(
    val bmi: Float,
    val bmiLabel: String,
    val dailyCalories: Int,
    val bmr: Int,
    val activityMultiplier: Float,
    val goalAdjustmentPercent: Int,
    val macros: MacroTargets,
    val waterRecommendation: DailyWaterRecommendation
)
