package com.saliery.nutrilog.app.presentation.onboarding

object OnboardingStepNavigator {
    fun next(step: OnboardingStep): OnboardingStep =
        when (step) {
            OnboardingStep.WELCOME -> OnboardingStep.SEX_AGE
            OnboardingStep.SEX_AGE -> OnboardingStep.BODY
            OnboardingStep.BODY -> OnboardingStep.ACTIVITY
            OnboardingStep.ACTIVITY -> OnboardingStep.GOAL
            OnboardingStep.GOAL -> OnboardingStep.SUMMARY
            OnboardingStep.SUMMARY -> OnboardingStep.SUMMARY
        }

    fun previous(step: OnboardingStep): OnboardingStep =
        when (step) {
            OnboardingStep.WELCOME -> OnboardingStep.WELCOME
            OnboardingStep.SEX_AGE -> OnboardingStep.WELCOME
            OnboardingStep.BODY -> OnboardingStep.SEX_AGE
            OnboardingStep.ACTIVITY -> OnboardingStep.BODY
            OnboardingStep.GOAL -> OnboardingStep.ACTIVITY
            OnboardingStep.SUMMARY -> OnboardingStep.GOAL
        }
}