package com.saliery.nutrilog.app.presentation.home.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.Wallpapers
import androidx.compose.ui.unit.dp
import com.saliery.nutrilog.R
import com.saliery.nutrilog.app.presentation.common.GlassCard
import com.saliery.nutrilog.app.presentation.helper.previewHomeState
import com.saliery.nutrilog.app.presentation.home.HomeState
import com.saliery.nutrilog.app.presentation.home.HomeWaterCardUiModel
import com.saliery.nutrilog.app.presentation.mealEntryScreen.components.AmountStepper
import com.saliery.nutrilog.app.presentation.mealEntryScreen.components.GlassActionButton
import com.saliery.nutrilog.ui.theme.NutriLogTheme
import com.saliery.nutrilog.ui.theme.OnboardingGlassTokens
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.rememberHazeState

@Composable
fun WaterSummaryCard(
    state: HomeState.LoadingState,
    model: HomeWaterCardUiModel?,
    hazeState: HazeState,
    modifier: Modifier = Modifier,
    glassOfWaterAdd: () -> Unit,
    waterMlAdd: (Double) -> Unit,
) {
    var customAmountText by remember { mutableStateOf("240") }

    GlassCard(
        hazeState = hazeState,
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            // Diagram
            WaterProgressCircle(
                progress = model?.progress ?: 0f,
                currentMl = model?.currentMl ?: 0.0
            )

            // Content
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = stringResource(R.string.base_water_str),
                    style = MaterialTheme.typography.titleMedium,
                    color = OnboardingGlassTokens.TextPrimary
                )
                Text(
                    text = stringResource(R.string.current_of_ml_str,
                        model?.currentMl?.toInt() ?: 0, model?.goalMl?.toInt() ?: 0),
                    style = MaterialTheme.typography.bodyMedium,
                    color = OnboardingGlassTokens.TextSecondary
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Controls
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // Button to add fast base amount (1 cup 240 ml)
//                    GlassActionButton(
//                        icon = painterResource(R.drawable.cup_svgrepo_com),
//                        buttonSize = 48.dp,
//                        onClick = { glassOfWaterAdd() },
//                        containerColor = OnboardingGlassTokens.Primary.copy(alpha = 0.2f),
//                        tint = OnboardingGlassTokens.Primary
//                    )

                    // Custom amount of water
                    AmountStepper(
                        value = customAmountText,
                        unit = stringResource(R.string.ml_unit_short_str),
                        onValueChange = { newValue ->
                            // Validation: only numbers and upper limited to 2000ml
                            if (newValue.all { it.isDigit() } && newValue.length <= 4) {
                                val amount = newValue.toDoubleOrNull() ?: 0.0
                                if (amount <= 2000) {
                                    customAmountText = newValue
                                }
                            }
                        },
                        onIncrease = {
                            val current = customAmountText.toIntOrNull() ?: 0
                            customAmountText = (current + 50).toString()
                        },
                        onDecrease = {
                            val current = customAmountText.toIntOrNull() ?: 0
                            if (current > 0) {
                                customAmountText = (current - 50).coerceAtLeast(0).toString()
                            }
                        }
                    )
                }

                if (customAmountText.isNotEmpty() && customAmountText != "0") {
                    TextButton(
                        onClick = {
                            waterMlAdd(customAmountText.toDoubleOrNull() ?: 0.0)
                            customAmountText = "240"
                        },
                        modifier = Modifier.align(Alignment.End)
                    ) {
                        Text(stringResource(R.string.add_str), color = OnboardingGlassTokens.Primary)
                    }
                }
            }
        }
    }
}

@Composable
private fun WaterProgressCircle(
    progress: Float,
    currentMl: Double,
    modifier: Modifier = Modifier
) {
    val animatedProgress by animateFloatAsState(targetValue = progress, label = "waterProgress")

    Box(
        modifier = modifier.size(100.dp),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val strokeWidth = 8.dp.toPx()
            val innerSize = size.copy(width = size.width - strokeWidth, height = size.height - strokeWidth)
            val topLeft = Offset(strokeWidth / 2, strokeWidth / 2)

            // Track
            drawCircle(
                color = Color.White.copy(alpha = 0.05f),
                style = Stroke(width = strokeWidth)
            )

            // Progress
            drawArc(
                color = Color(0xFF4FC3F7), // Water Blue
                startAngle = -90f,
                sweepAngle = 360f * animatedProgress,
                useCenter = false,
                topLeft = topLeft,
                size = innerSize,
                style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
            )
        }

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                painter = painterResource(R.drawable.water_drop_svgrepo_com_2_),
                contentDescription = null,
                tint = Color(0xFF4FC3F7),
                modifier = Modifier.size(20.dp)
            )
            Text(
                text = "${(progress * 100).toInt()}%",
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Bold,
                color = OnboardingGlassTokens.TextPrimary
            )
        }
    }
}

@Preview(
    wallpaper = Wallpapers.GREEN_DOMINATED_EXAMPLE,
    locale = "en"
)
@Composable
private fun WaterSummaryCardPreview() {

    NutriLogTheme(
        darkTheme = true
    ) {
        WaterSummaryCard(
            state = HomeState.LoadingState.Idle,
            model = previewHomeState().waterCard,
            hazeState = rememberHazeState(),
            glassOfWaterAdd = {},
            waterMlAdd = {}
        )
    }
}