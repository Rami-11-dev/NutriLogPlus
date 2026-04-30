package com.saliery.nutrilog.app.presentation.helper

import com.saliery.nutrilog.app.domain.model.user.ActivityLevel
import com.saliery.nutrilog.app.domain.model.user.Goal
import com.saliery.nutrilog.app.domain.model.user.Sex
import com.saliery.nutrilog.app.domain.model.user.User
import kotlinx.datetime.LocalDate

fun previewUser(): User {

    return User(
        weightKg = 60.0,
        heightCm = 180.0,
        birthDate = LocalDate.parse("2000-12-20"),
        sex = Sex.MALE,
        activityLevel = ActivityLevel.MODERATE,
        goal = Goal.MAINTAIN
    )
}