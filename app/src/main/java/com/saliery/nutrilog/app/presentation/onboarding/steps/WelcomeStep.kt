package com.saliery.nutrilog.app.presentation.onboarding.steps

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.Wallpapers
import com.saliery.nutrilog.R
import com.saliery.nutrilog.app.presentation.common.BaseBlock
import com.saliery.nutrilog.app.presentation.common.OnboardingStepScaffold
import com.saliery.nutrilog.app.presentation.onboarding.OnboardingStep
import com.saliery.nutrilog.ui.theme.NutriLogTheme
import com.saliery.nutrilog.ui.theme.OnboardingGlassTokens

@Composable
fun WelcomeStep() {

    OnboardingStepScaffold(
        step = OnboardingStep.WELCOME,
        stepName = stringResource(R.string.step_1_name),
        title = stringResource(R.string.step_1_title),
        subtitle = stringResource(R.string.step_1_subtitle),
        isScrollable = true
    ) {

        BaseBlock(
            title = stringResource(R.string.privacy_first_str),
            subtitle = stringResource(R.string.your_profile_and_nutrition_data_on_your_device_str),
            supporting = null,
            leadingIcon = painterResource(R.drawable.privacy_svgrepo_com),
            iconTint = OnboardingGlassTokens.TextSecondary,
            iconAmbient = OnboardingGlassTokens.Glow,
            iconSpot = OnboardingGlassTokens.Glow
        ) { }

        BaseBlock(
            title = stringResource(R.string.trusted_food_data_str),
            subtitle = stringResource(R.string.powered_by_usda_and_off_str),
            supporting = stringResource(R.string.local_database_str),
            leadingIcon = painterResource(R.drawable.usda_logo_color),
            isIconColorful = true,
            iconAmbient = Color.Green.copy(alpha = 0.45f),
            iconSpot = Color.Green.copy(alpha = 0.45f)
        ) { }

        BaseBlock(
            title = stringResource(R.string.smart_food_recognition_str),
            subtitle = stringResource(R.string.ai_helps_str),
            supporting = stringResource(R.string.brand_new_local_model_str),
            leadingIcon = painterResource(R.drawable.ultralytics_yolo_icon),
            isIconColorful = true,
            iconAmbient = Color.Blue.copy(alpha = 0.35f),
            iconSpot = Color.Blue.copy(alpha = 0.45f)
        ) { }

        BaseBlock(
            title = stringResource(R.string.build_your_own_recipes_str),
            subtitle = stringResource(R.string.create_meals_from_ingredients_str),
            supporting = null,
            leadingIcon = painterResource(R.drawable.recipe_keeper_svgrepo_com),
            iconTint = OnboardingGlassTokens.TextSecondary,
            iconAmbient = OnboardingGlassTokens.Glow,
            iconSpot = OnboardingGlassTokens.Glow
        ) { }

        BaseBlock(
            title = stringResource(R.string.track_your_progress_str),
            subtitle = stringResource(R.string.stay_consistent_str),
            supporting = null,
            leadingIcon = painterResource(R.drawable.user_progress_svgrepo_com),
            iconTint = OnboardingGlassTokens.TextSecondary,
            iconAmbient = OnboardingGlassTokens.Glow,
            iconSpot = OnboardingGlassTokens.Glow
        ) { }
    }
}

@Preview(
    wallpaper = Wallpapers.GREEN_DOMINATED_EXAMPLE,
    locale = "en"
)
@Composable
private fun WelcomeStepPreview() {

    NutriLogTheme(
        darkTheme = true
    ) {
        WelcomeStep()
    }
}