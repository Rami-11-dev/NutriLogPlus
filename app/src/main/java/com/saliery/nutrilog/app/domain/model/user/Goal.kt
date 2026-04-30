package com.saliery.nutrilog.app.domain.model.user

import kotlinx.serialization.Serializable

@Serializable
enum class Goal {
    LOSE_WEIGHT,
    MAINTAIN,
    GAIN_WEIGHT
}