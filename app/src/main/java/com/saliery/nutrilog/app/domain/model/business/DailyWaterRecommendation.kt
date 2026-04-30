package com.saliery.nutrilog.app.domain.model.business

data class DailyWaterRecommendation(
    val waterLiters: Double,
    val waterMl: Double,
    val baseAmount: Double,
    val activityAdjustment: Double,
    val goalAdjustment: Double,
    val ageAdjustment: Double,
    val sexAdjustment: Double
)
