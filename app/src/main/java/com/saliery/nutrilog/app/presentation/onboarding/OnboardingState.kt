package com.saliery.nutrilog.app.presentation.onboarding

import com.saliery.nutrilog.app.domain.model.business.SummaryUiModel

data class OnboardingState(
    val currentStep: OnboardingStep = OnboardingStep.WELCOME,
    val draft: UserDraft = UserDraft(),
    val summary: SummaryUiModel? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val canGoNext: Boolean = false,
    val isCompleted: Boolean = false
)
