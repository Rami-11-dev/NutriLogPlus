package com.saliery.nutrilog.app.domain.model.business

data class CookingEffect(
    val calorieMultiplier: Double = 1.0,
    val waterRetentionMultiplier: Double = 1.0,
    val vitaminRetentionMultiplier: Double = 1.0
)
