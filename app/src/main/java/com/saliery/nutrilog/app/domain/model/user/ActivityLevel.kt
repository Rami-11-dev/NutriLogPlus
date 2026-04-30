package com.saliery.nutrilog.app.domain.model.user

import kotlinx.serialization.Serializable

@Serializable
enum class ActivityLevel(val multiplier: Double, val waterMultiplier: Double) {
    LOW(1.2, 1.0),
    LIGHT(1.375, 1.1),
    MODERATE(1.55, 1.25),
    HIGH(1.725, 1.5),
    EXTREME(1.9, 2.0)
}