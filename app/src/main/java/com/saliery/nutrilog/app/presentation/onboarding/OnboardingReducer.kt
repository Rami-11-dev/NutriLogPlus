package com.saliery.nutrilog.app.presentation.onboarding

class OnboardingReducer {

    fun reduce(
        state: OnboardingState,
        intent: OnboardingIntent
    ): OnboardingState {

        return when (intent) {

            is OnboardingIntent.SexSelected -> {
                val newDraft = state.draft.copy(sex = intent.sex)
                state.copy(
                    draft = newDraft,
                    canGoNext = canProceed(state.currentStep, newDraft)
                )
            }

            is OnboardingIntent.BirthDateSelected -> {
                val newDraft = state.draft.copy(birthDate = intent.value)
                state.copy(
                    draft = newDraft,
                    canGoNext = canProceed(state.currentStep, newDraft)
                )
            }

            is OnboardingIntent.HeightChanged -> {
                val newDraft = state.draft.copy(heightCm = intent.value)
                state.copy(
                    draft = newDraft,
                    canGoNext = canProceed(state.currentStep, newDraft)
                )
            }

            is OnboardingIntent.WeightChanged -> {
                val newDraft = state.draft.copy(weightKg = intent.value)
                state.copy(
                    draft = newDraft,
                    canGoNext = canProceed(state.currentStep, newDraft)
                )
            }

            is OnboardingIntent.ActivitySelected -> {
                val newDraft = state.draft.copy(activityLevel = intent.activityLevel)
                state.copy(
                    draft = newDraft,
                    canGoNext = canProceed(state.currentStep, newDraft)
                )
            }

            is OnboardingIntent.GoalSelected -> {
                val newDraft = state.draft.copy(goal = intent.goal)
                state.copy(
                    draft = newDraft,
                    canGoNext = canProceed(state.currentStep, newDraft)
                )
            }

            OnboardingIntent.NextClicked -> {
                val nextStep = nextStep(state.currentStep)
                state.copy(
                    currentStep = nextStep,
                    canGoNext = canProceed(nextStep, state.draft)
                )
            }

            OnboardingIntent.BackClicked -> {
                val previousStep = previousStep(state.currentStep)
                state.copy(
                    currentStep = previousStep,
                    canGoNext = canProceed(previousStep, state.draft)
                )
            }

            OnboardingIntent.FinishClicked -> {
                state.copy(isLoading = true, errorMessage = null)
            }

            OnboardingIntent.ErrorShown -> {
                state.copy(errorMessage = null)
            }
        }
    }

    private fun nextStep(step: OnboardingStep): OnboardingStep =
        when (step) {
            OnboardingStep.WELCOME -> OnboardingStep.SEX_AGE
            OnboardingStep.SEX_AGE -> OnboardingStep.BODY
            OnboardingStep.BODY -> OnboardingStep.ACTIVITY
            OnboardingStep.ACTIVITY -> OnboardingStep.GOAL
            OnboardingStep.GOAL -> OnboardingStep.SUMMARY
            OnboardingStep.SUMMARY -> OnboardingStep.SUMMARY
        }

    private fun previousStep(step: OnboardingStep) =
        when (step) {
            OnboardingStep.WELCOME -> OnboardingStep.WELCOME
            OnboardingStep.SEX_AGE -> OnboardingStep.WELCOME
            OnboardingStep.BODY -> OnboardingStep.SEX_AGE
            OnboardingStep.ACTIVITY -> OnboardingStep.BODY
            OnboardingStep.GOAL -> OnboardingStep.ACTIVITY
            OnboardingStep.SUMMARY -> OnboardingStep.GOAL
        }

    private fun canProceed(
        step: OnboardingStep, draft: UserDraft
    ): Boolean {

        return when (step) {
            OnboardingStep.WELCOME -> true
            OnboardingStep.SEX_AGE ->
                draft.sex != null && draft.birthDate != null
            OnboardingStep.BODY ->
                draft.heightCm?.let { it in 1.0..250.0 } == true &&
                        draft.weightKg?.let { it > 0 } == true
            OnboardingStep.ACTIVITY -> draft.activityLevel != null
            OnboardingStep.GOAL -> draft.goal != null
            OnboardingStep.SUMMARY -> true
        }
    }
}