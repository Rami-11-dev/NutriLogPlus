package com.saliery.nutrilog.app.presentation.onboarding

import com.saliery.nutrilog.app.domain.model.user.ActivityLevel
import com.saliery.nutrilog.app.domain.model.user.Goal
import com.saliery.nutrilog.app.domain.model.user.Sex
import com.saliery.nutrilog.app.domain.model.user.User
import kotlinx.datetime.LocalDate

data class UserDraft(
    val sex: Sex? = null,
    val heightCm: Double? = null,
    val weightKg: Double? = null,
    val activityLevel: ActivityLevel? = null,
    val goal: Goal? = null,
    val birthDate: LocalDate? = null
)

fun UserDraft.toUserOrNull(): User? {
    return User(
        weightKg = weightKg ?: return null,
        heightCm = heightCm ?: return null,
        birthDate = birthDate ?: return null,
        sex = sex ?: return null,
        activityLevel = activityLevel ?: return null,
        goal = goal ?: return null
    )
}
