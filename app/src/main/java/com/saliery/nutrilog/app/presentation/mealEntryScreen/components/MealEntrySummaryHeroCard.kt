package com.saliery.nutrilog.app.presentation.mealEntryScreen.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.Wallpapers
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.saliery.nutrilog.R
import com.saliery.nutrilog.app.presentation.common.GlassCard
import com.saliery.nutrilog.app.presentation.helper.trimSmart
import com.saliery.nutrilog.app.presentation.mealEntryScreen.MealEntrySummaryUiModel
import com.saliery.nutrilog.ui.theme.NutriLogTheme
import com.saliery.nutrilog.ui.theme.OnboardingGlassTokens
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.rememberHazeState

@Composable
fun MealEntrySummaryHeroCard(
    summary: MealEntrySummaryUiModel,
    hazeState: HazeState,
    modifier: Modifier = Modifier
) {
    GlassCard(
        modifier = modifier.fillMaxWidth(),
        hazeState = hazeState,
        glow = true,
        contentPadding = PaddingValues(20.dp)
    ) {

        val kcalUnit = stringResource(R.string.kcal_unit_str)

        Column(
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {
                Column {
                    Text(
                        text = stringResource(R.string.energy_value_str),
                        style = MaterialTheme.typography.labelMedium,
                        color = OnboardingGlassTokens.TextSecondary
                    )
                    Text(
                        text = "${summary.totalCalories.trimSmart()} ${kcalUnit.uppercase()}",
                        style = MaterialTheme.typography.headlineMedium.copy(
                            fontWeight = FontWeight.Black,
                            fontSize = 28.sp
                        ),
                        color = OnboardingGlassTokens.Primary
                    )
                }

                Icon(
                    painter = painterResource(R.drawable.leaf_eco_svgrepo_com),
                    contentDescription = null,
                    tint = OnboardingGlassTokens.Primary.copy(alpha = 0.5f),
                    modifier = Modifier.size(32.dp).padding(bottom = 4.dp)
                )
            }

            HorizontalDivider(
                color = OnboardingGlassTokens.GlassBorder.copy(alpha = 0.5f),
                thickness = 0.5.dp
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround // Распределяем равномерно
            ) {
                CircularNutritionProgress(
                    label = stringResource(R.string.protein_str),
                    value = summary.totalProteins,
                    targetValue = summary.proteinTarget ?: 100.0,
                    color = OnboardingGlassTokens.ProteinColor
                )

                CircularNutritionProgress(
                    label = stringResource(R.string.fat_str),
                    value = summary.totalFats,
                    targetValue = summary.fatTarget ?: 70.0,
                    color = OnboardingGlassTokens.FatsColor
                )

                CircularNutritionProgress(
                    label = stringResource(R.string.carbs_str),
                    value = summary.totalCarbs,
                    targetValue = summary.carbTarget ?: 250.0,
                    color = OnboardingGlassTokens.CarbsColor
                )
            }
        }
    }
}

@Composable
private fun CircularNutritionProgress(
    label: String,
    value: Double,
    targetValue: Double,
    color: Color,
    modifier: Modifier = Modifier
) {

    val percentage = if (targetValue > 0) (value / targetValue).coerceIn(0.0, 1.5).toFloat() else 0f

    val animatedProgress by animateFloatAsState(
        targetValue = percentage,
        animationSpec = tween(durationMillis = 1000, easing = FastOutSlowInEasing),
        label = "MacroProgress"
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.size(70.dp)
        ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                val strokeWidth = 6.dp.toPx()
                val innerRadius = (size.minDimension / 2) - (strokeWidth / 2)

                drawArc(
                    color = color.copy(alpha = 0.1f),
                    startAngle = -90f,
                    sweepAngle = 360f,
                    useCenter = false,
                    style = Stroke(strokeWidth, cap = StrokeCap.Round)
                )

                drawArc(
                    color = color,
                    startAngle = -90f,
                    sweepAngle = 360f * animatedProgress.coerceAtMost(1f),
                    useCenter = false,
                    style = Stroke(strokeWidth, cap = StrokeCap.Round)
                )

                // Overdo progress
                if (animatedProgress > 1f) {
                    drawArc(
                        color = color.copy(alpha = 0.4f),
                        startAngle = -90f,
                        sweepAngle = 360f * (animatedProgress - 1f),
                        useCenter = false,
                        style = Stroke(strokeWidth + 4f, cap = StrokeCap.Round)
                    )
                }
            }

            // Circle inner content
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = value.toInt().toString(),
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Black,
                        fontSize = 16.sp
                    ),
                    color = OnboardingGlassTokens.TextPrimary
                )
                Text(
                    text = stringResource(R.string.grams_unit_short),
                    style = MaterialTheme.typography.labelSmall,
                    color = OnboardingGlassTokens.TextTertiary
                )
            }
        }

        // Progress label
        Text(
            text = label.uppercase(),
            style = MaterialTheme.typography.labelSmall.copy(
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp
            ),
            color = color.copy(alpha = 0.8f)
        )
    }
}

@Preview(
    wallpaper = Wallpapers.GREEN_DOMINATED_EXAMPLE,
    locale = "ru"
)
@Composable
private fun MealEntrySummaryHeroCardPreview() {

    NutriLogTheme(
        darkTheme = true
    ) {
        MealEntrySummaryHeroCard(
            summary = MealEntrySummaryUiModel(
                totalCalories = 498.0,
                totalProteins = 23.4,
                totalFats = 12.2,
                totalCarbs = 8.2,
                caloriesTarget = 2300.0,
                proteinTarget = 123.9,
                fatTarget = 32.0,
                carbTarget = 21.3
            ),
            hazeState = rememberHazeState()
        )
    }
}