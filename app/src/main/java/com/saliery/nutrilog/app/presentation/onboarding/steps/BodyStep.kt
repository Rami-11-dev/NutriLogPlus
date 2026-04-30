package com.saliery.nutrilog.app.presentation.onboarding.steps

import android.util.Log
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.Wallpapers
import androidx.compose.ui.unit.dp
import com.saliery.nutrilog.R
import com.saliery.nutrilog.app.presentation.common.GlassNumberWheelCard
import com.saliery.nutrilog.app.presentation.common.OnboardingStepScaffold
import com.saliery.nutrilog.app.presentation.onboarding.OnboardingStep
import com.saliery.nutrilog.app.presentation.onboarding.UserDraft
import com.saliery.nutrilog.ui.theme.NutriLogTheme
import com.saliery.nutrilog.ui.theme.OnboardingGlassTokens
import timber.log.Timber

@Composable
fun BodyStep(
    draft: UserDraft,
    onHeightChanged: (Double) -> Unit,
    onWeightChanged: (Double) -> Unit,
) {

    OnboardingStepScaffold(
        step = OnboardingStep.BODY,
        stepName = stringResource(R.string.step_3_name),
        title = stringResource(R.string.step_3_title),
        subtitle = stringResource(R.string.step_3_subtitle),
        modifier = Modifier
    ) {

        Spacer(modifier = Modifier.height(8.dp))

        GlassNumberWheelCard(
            isDouble = true,
            trailingUnitIcon = painterResource(R.drawable.height_24px),
            trailingUnitText = stringResource(R.string.centimeters_short_str),
            minValue = 50,
            maxValue = 250,
            defaultValue = draft.heightCm ?: 180.0,
            onValueChanged = onHeightChanged
        )

        Text(
            text = stringResource(R.string.measure_height_tip_str),
            color = OnboardingGlassTokens.TextSecondary,
        )

        GlassNumberWheelCard(
            trailingUnitIcon = painterResource(R.drawable.weight_24px),
            trailingUnitText = stringResource(R.string.kilograms_short_str),
            minValue = 15,
            maxValue = 500,
            defaultValue = draft.weightKg ?: 60.0,
            onValueChanged = onWeightChanged
        )

        Text(
            text = stringResource(R.string.weigh_tip_str),
            color = OnboardingGlassTokens.TextSecondary,
        )
    }
}

@Preview(
    wallpaper = Wallpapers.GREEN_DOMINATED_EXAMPLE,
    showBackground = false,
    locale = "ru"
)
@Composable
private fun BodyStepPreview() {
    NutriLogTheme(
        darkTheme = true,
        dynamicColor = true
    ) {
        BodyStep(
            draft = UserDraft(),
            onHeightChanged = {},
            onWeightChanged = {}
        )
    }
}