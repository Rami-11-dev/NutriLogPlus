package com.saliery.nutrilog.app.domain.model.user

import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable

@Serializable
data class User(
    val weightKg: Double,
    val heightCm: Double,
    val birthDate: LocalDate,
    val sex: Sex,
    val activityLevel: ActivityLevel,
    val goal: Goal
)
