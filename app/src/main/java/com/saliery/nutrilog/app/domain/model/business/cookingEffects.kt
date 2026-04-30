package com.saliery.nutrilog.app.domain.model.business

val cookingEffects = mapOf(
    CookingMethod.RAW to CookingEffect(
        calorieMultiplier = 1.0,
        waterRetentionMultiplier = 1.0,
        vitaminRetentionMultiplier = 1.0
    ),
    CookingMethod.BOILED to CookingEffect(
        calorieMultiplier = 1.0,
        waterRetentionMultiplier = 1.1,
        vitaminRetentionMultiplier = 0.85
    ),
    CookingMethod.STEAMED to CookingEffect(
        calorieMultiplier = 1.0,
        waterRetentionMultiplier = 1.05,
        vitaminRetentionMultiplier = 0.9
    ),
    CookingMethod.FRIED to CookingEffect(
        calorieMultiplier = 1.2,
        waterRetentionMultiplier = 0.9,
        vitaminRetentionMultiplier = 0.7
    ),
    CookingMethod.BAKED to CookingEffect(
        calorieMultiplier = 1.0,
        waterRetentionMultiplier = 0.85,
        vitaminRetentionMultiplier = 0.8
    ),
    CookingMethod.GRILLED to CookingEffect(
        calorieMultiplier = 1.0,
        waterRetentionMultiplier = 0.8,
        vitaminRetentionMultiplier = 0.8
    )
)