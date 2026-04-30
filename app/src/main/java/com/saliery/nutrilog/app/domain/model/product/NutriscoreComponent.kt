package com.saliery.nutrilog.app.domain.model.product

data class NutriscoreComponent(
    val componentId: String?,
    val isNegative: Boolean,
    val points: Int?,
    val maxPoints: Int?,
    val unit: String?,
    val value: Double?
)