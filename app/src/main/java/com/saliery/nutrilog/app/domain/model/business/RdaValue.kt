package com.saliery.nutrilog.app.domain.model.business

import com.saliery.nutrilog.app.domain.model.user.Sex

data class AgeRange(
    val minAge: Int,
    val maxAge: Int = Int.MAX_VALUE
) {
    fun contains(age: Int): Boolean = age in minAge..maxAge
}

data class RdaValue(
    val ageRange: AgeRange,
    val sex: Sex,
    val rdaValue: Double,
    val upperLimit: Double? = null
)
