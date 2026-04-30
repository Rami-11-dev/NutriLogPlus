package com.saliery.nutrilog.app.presentation.onboarding

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.twotone.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.Wallpapers
import androidx.compose.ui.unit.dp
import com.saliery.nutrilog.R
import com.saliery.nutrilog.ui.theme.NutriLogTheme

@Composable
fun OnboardingBottomBar(
    step: OnboardingStep,
    canGoNext: Boolean,
    isLoading: Boolean,
    onBack: () -> Unit,
    onNext: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        if (step != OnboardingStep.WELCOME) {
            TextButton(
                onClick = onBack
            ) {
                Text(text = stringResource(R.string.back_btn))
            }
        } else {
            Spacer(modifier = Modifier.width(1.dp))
        }

        Button(
            onClick = onNext,
            enabled = canGoNext && !isLoading
        ) {
            Text(
                text = if (step == OnboardingStep.SUMMARY) stringResource(R.string.start_tracking_btn_str)
                else stringResource(R.string.next_btn)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Icon(
                imageVector = Icons.AutoMirrored.TwoTone.ArrowForward,
                contentDescription = null,
                modifier = Modifier.size(18.dp)
            )
        }
    }
}

@Preview(wallpaper = Wallpapers.RED_DOMINATED_EXAMPLE, locale = "en")
@Composable
private fun OnboardingBottomBarPreview(
    step: OnboardingStep = OnboardingStep.SUMMARY,
    canGoNext: Boolean = true,
    isLoading: Boolean = false,
    onBack: () -> Unit = {},
    onNext: () -> Unit = {}
) {
    NutriLogTheme(
        darkTheme = true,
        dynamicColor = true
    ) {
        OnboardingBottomBar(
            step = step,
            canGoNext = canGoNext,
            isLoading = isLoading,
            onBack = onBack,
            onNext = onNext
        )
    }
}