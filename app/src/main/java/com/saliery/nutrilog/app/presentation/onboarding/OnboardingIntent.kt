package com.saliery.nutrilog.app.presentation.onboarding

import com.saliery.nutrilog.app.domain.model.user.ActivityLevel
import com.saliery.nutrilog.app.domain.model.user.Goal
import com.saliery.nutrilog.app.domain.model.user.Sex
import kotlinx.datetime.LocalDate

sealed interface OnboardingIntent {
    data object NextClicked : OnboardingIntent
    data object BackClicked : OnboardingIntent

    data class SexSelected(val sex: Sex) : OnboardingIntent
    data class BirthDateSelected(val value: LocalDate) : OnboardingIntent
    data class HeightChanged(val value: Double) : OnboardingIntent
    data class WeightChanged(val value: Double) : OnboardingIntent
    data class ActivitySelected(val activityLevel: ActivityLevel) : OnboardingIntent
    data class GoalSelected(val goal: Goal) : OnboardingIntent

    data object FinishClicked : OnboardingIntent
    data object ErrorShown : OnboardingIntent
}