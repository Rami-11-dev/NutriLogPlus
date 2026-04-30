package com.saliery.nutrilog.app.presentation.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.Wallpapers
import androidx.compose.ui.unit.dp
import com.saliery.nutrilog.R
import com.saliery.nutrilog.app.presentation.onboarding.OnboardingStep
import com.saliery.nutrilog.ui.theme.NutriLogTheme
import com.saliery.nutrilog.ui.theme.OnboardingGlassTokens


@Composable
fun OnboardingStepScaffold(
    step: OnboardingStep,
    stepName: String,
    title: String,
    subtitle: String,
    modifier: Modifier = Modifier,
    isScrollable: Boolean = false,
    content: @Composable ColumnScope.() -> Unit
) {
    val stepIndex = step.ordinal + 1
    val totalSteps = OnboardingStep.entries.size
    val verticalScrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        GlassCard(
            modifier = Modifier.fillMaxWidth(),
            cornerRadius = 28.dp,
            glow = false
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stepName,
                        style = MaterialTheme.typography.labelLarge,
                        color = OnboardingGlassTokens.Primary
                    )
                    Text(
                        text = stringResource(R.string.step_num_of_steps_str, stepIndex, totalSteps),
                        style = MaterialTheme.typography.labelLarge,
                        color = OnboardingGlassTokens.TextSecondary
                    )
                }

                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.headlineMedium,
                        color = OnboardingGlassTokens.TextPrimary
                    )
                    Text(
                        text = subtitle,
                        style = MaterialTheme.typography.bodyLarge,
                        color = OnboardingGlassTokens.TextSecondary
                    )
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(
                        state = verticalScrollState,
                        enabled = isScrollable
                    ),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                Spacer(Modifier.width(8.dp))
                content()
            }
        }
    }
}

@Preview(
    showBackground = false,
    wallpaper = Wallpapers.GREEN_DOMINATED_EXAMPLE
)
@Composable
private fun OnboardingStepScaffoldPreview() {
    NutriLogTheme(
        darkTheme = true
    ) {
        OnboardingStepScaffold(
            step = OnboardingStep.SEX_AGE,
            title = "sex and age",
            stepName = "test",
            subtitle = "subtitle",
        ) {
            Column() {
                Text(text = "hello world")
                Button(
                    onClick = {}
                ) {
                    Text("button")
                }
            }
        }
    }
}