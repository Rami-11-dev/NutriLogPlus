package com.saliery.nutrilog.app.presentation.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saliery.nutrilog.app.domain.model.business.SummaryUiModel
import com.saliery.nutrilog.app.domain.model.user.Goal
import com.saliery.nutrilog.app.domain.model.user.User
import com.saliery.nutrilog.app.domain.usecase.CalculateBmiUseCase
import com.saliery.nutrilog.app.domain.usecase.CalculateBmrUseCase
import com.saliery.nutrilog.app.domain.usecase.CalculateDailyCaloriesUseCase
import com.saliery.nutrilog.app.domain.usecase.CalculateDailyWaterUseCase
import com.saliery.nutrilog.app.domain.usecase.CalculateMacroTargets
import com.saliery.nutrilog.app.domain.usecase.SaveUserUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class OnboardingViewModel(
    private val reducer: OnboardingReducer,
    private val saveUserUseCase: SaveUserUseCase,
    private val calculateBmrUseCase: CalculateBmrUseCase,
    private val calculateDailyCaloriesUseCase: CalculateDailyCaloriesUseCase,
    private val calculateMacroTargets: CalculateMacroTargets,
    private val calculateDailyWaterUseCase: CalculateDailyWaterUseCase,
    private val calculateBmiUseCase: CalculateBmiUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(
        OnboardingState(canGoNext = true)
    )
    val state: StateFlow<OnboardingState> = _state.asStateFlow()

    fun onIntent(intent: OnboardingIntent) {
        when (intent) {
            OnboardingIntent.FinishClicked -> finishOnboarding()
            OnboardingIntent.NextClicked -> handleNextClicked()
            else -> _state.update { reducer.reduce(it, intent) }
        }
    }

    private fun handleNextClicked() {
        val currentState = _state.value
        val nextStep = OnboardingStepNavigator.next(currentState.currentStep)

        if (nextStep == OnboardingStep.SUMMARY) {
            val summary = buildSummary(currentState.draft) ?: return

            _state.update {
                reducer.reduce(it, OnboardingIntent.NextClicked)
                    .copy(summary = summary)
            }
        } else {
            _state.update { reducer.reduce(it, OnboardingIntent.NextClicked) }
        }
    }

    private fun buildSummary(draft: UserDraft): SummaryUiModel? {
        val user = draft.toUserOrNull() ?: return null

        val bmr = calculateBmrUseCase(user)
        val dailyCalories = calculateDailyCaloriesUseCase(user)
        val macros = calculateMacroTargets(dailyCalories, user.goal)
        val water = calculateDailyWaterUseCase(user)
        val bmi = calculateBmiUseCase(user)

        return SummaryUiModel(
            bmi = bmi.toFloat(),
            bmiLabel = "",
            dailyCalories = dailyCalories.toInt(),
            bmr = bmr.toInt(),
            activityMultiplier = user.activityLevel.multiplier.toFloat(),
            goalAdjustmentPercent = when (user.goal) {
                Goal.LOSE_WEIGHT -> -15
                Goal.MAINTAIN -> 0
                Goal.GAIN_WEIGHT -> 10
            },
            macros = macros,
            waterRecommendation = water
        )
    }

    private fun finishOnboarding() {
        val user = _state.value.draft.toUserOrNull() ?: return

        viewModelScope.launch {
            _state.update {
                it.copy(isLoading = true, errorMessage = null)
            }

            runCatching {
                saveUserUseCase(user)
            }.onSuccess {
                _state.update {
                    it.copy(
                        isLoading = false,
                        isCompleted = true
                    )
                }
            }.onFailure { throwable ->
                _state.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = throwable.message ?: "Unknown error"
                    )
                }
            }
        }
    }
}