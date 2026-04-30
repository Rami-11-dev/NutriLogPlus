package com.saliery.nutrilog.app.domain.model.business

data class CalorieBalance(
    val difference: Double,
    val isDeficit: Boolean,
    val isSurplus: Boolean
)