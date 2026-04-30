package com.saliery.nutrilog.app.presentation.onboarding

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.core.R

enum class OnboardingStep {
    WELCOME,
    SEX_AGE,
    BODY,
    ACTIVITY,
    GOAL,
    SUMMARY
}