package com.saliery.nutrilog.app.domain.model.user

import java.time.LocalDateTime

data class WeightEntryModel(
    val id: Long,
    val weightKg: Double,
    val dateTime: LocalDateTime
)
