package com.saliery.nutrilog.app.presentation.onboarding

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.Wallpapers
import com.saliery.nutrilog.app.domain.model.business.DailyWaterRecommendation
import com.saliery.nutrilog.app.domain.model.business.MacroTargets
import com.saliery.nutrilog.app.domain.model.business.SummaryUiModel
import com.saliery.nutrilog.app.presentation.onboarding.steps.ActivityStep
import com.saliery.nutrilog.app.presentation.onboarding.steps.BodyStep
import com.saliery.nutrilog.app.presentation.onboarding.steps.GoalStep
import com.saliery.nutrilog.app.presentation.onboarding.steps.SexAgeStep
import com.saliery.nutrilog.app.presentation.onboarding.steps.SummaryStep
import com.saliery.nutrilog.app.presentation.onboarding.steps.WelcomeStep
import com.saliery.nutrilog.ui.theme.NutriLogTheme
import com.saliery.nutrilog.ui.theme.OnboardingGlassTokens

@Composable
fun OnboardingScreen(
    state: OnboardingState,
    onIntent: (OnboardingIntent) -> Unit,

) {


    Scaffold(
        containerColor = OnboardingGlassTokens.ScreenBackground,
        bottomBar = {
            OnboardingBottomBar(
                step = state.currentStep,
                canGoNext = state.canGoNext,
                isLoading = state.isLoading,
                onBack = { onIntent(OnboardingIntent.BackClicked) },
                onNext = {
                    if (state.currentStep == OnboardingStep.SUMMARY) {
                        onIntent(OnboardingIntent.FinishClicked)
                    } else {
                        onIntent(OnboardingIntent.NextClicked)
                    }
                }
            )
        }
    ) { padding ->
        AnimatedContent(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            targetState = state.currentStep,
            label = "onboarding-step"
        ) { step ->
            when (step) {
                OnboardingStep.WELCOME -> WelcomeStep()
                OnboardingStep.SEX_AGE -> SexAgeStep(
                    draft = state.draft,
                    onSexSelected = { onIntent(OnboardingIntent.SexSelected(it)) },
                    onBirthDateSelected = { onIntent(OnboardingIntent.BirthDateSelected(it)) }
                )
                OnboardingStep.BODY -> BodyStep(
                    draft = state.draft,
                    onHeightChanged = { onIntent(OnboardingIntent.HeightChanged(it)) },
                    onWeightChanged = { onIntent(OnboardingIntent.WeightChanged(it)) }
                )
                OnboardingStep.ACTIVITY -> ActivityStep(
                    draft = state.draft,
                    onActivitySelected = { onIntent(OnboardingIntent.ActivitySelected(it)) }
                )
                OnboardingStep.GOAL -> GoalStep(
                    draft = state.draft,
                    onGoalSelected = { onIntent(OnboardingIntent.GoalSelected(it)) }
                )
                OnboardingStep.SUMMARY -> {
                    state.summary?.let { summary ->
                        SummaryStep(
                            draft = state.draft,
                            summaryUiModel = summary
                        )
                    }
                }
            }
        }
    }
}

@Preview(
    wallpaper = Wallpapers.YELLOW_DOMINATED_EXAMPLE,
    locale = "ru",
    showSystemUi = true
)
@Composable
private fun OnboardingScreenPreview(
    state: OnboardingState = OnboardingState(
        currentStep = OnboardingStep.SUMMARY,
        summary = SummaryUiModel(
            bmi = 23.2f,
            bmiLabel = "NORMAL",
            dailyCalories = 2200,
            bmr = 2200,
            activityMultiplier = 1.5f,
            goalAdjustmentPercent = 5,
            macros = MacroTargets(
                proteinGrams = 160.0,
                fatGrams = 70.0,
                carbGrams = 240.0
            ),
            waterRecommendation = DailyWaterRecommendation(
                waterLiters = 2.3,
                waterMl = 2300.0,
                baseAmount = 2000.0,
                activityAdjustment = 1.5,
                goalAdjustment = 1.0,
                ageAdjustment = 1.0,
                sexAdjustment = 1.0
            )
        )
    ),
    onIntent: (OnboardingIntent) -> Unit = {},
    onCompleted: () -> Unit = {}
) {
    NutriLogTheme(
        darkTheme = true,
        dynamicColor = true
    ) {
        OnboardingScreen(
            state = state,
            onIntent = onIntent
        )
    }
}