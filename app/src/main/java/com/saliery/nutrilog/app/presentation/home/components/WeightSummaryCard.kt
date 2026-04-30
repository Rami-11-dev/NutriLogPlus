package com.saliery.nutrilog.app.presentation.home.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
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
import com.saliery.nutrilog.app.presentation.home.HomeWeightCardUiModel
import com.saliery.nutrilog.app.presentation.home.WeightTimeRange
import com.saliery.nutrilog.app.presentation.mealEntryScreen.components.AmountStepper
import com.saliery.nutrilog.app.presentation.mealEntryScreen.components.GlassActionButton
import com.saliery.nutrilog.ui.theme.NutriLogTheme
import com.saliery.nutrilog.ui.theme.OnboardingGlassTokens
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.rememberHazeState
import java.util.Locale

@Composable
fun WeightSummaryCard(
    state: HomeState.LoadingState,
    model: HomeWeightCardUiModel?,
    hazeState: HazeState,
    modifier: Modifier = Modifier,
    onWeightKgUpdate: (Double) -> Unit,
    onRangeChange: (WeightTimeRange) -> Unit
) {
    GlassCard(
        modifier = modifier.fillMaxWidth(),
        hazeState = hazeState
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = stringResource(R.string.weight_summary_title_str),
                style = MaterialTheme.typography.titleLarge,
                color = OnboardingGlassTokens.TextPrimary
            )

            Spacer(modifier = Modifier.height(16.dp))

            if (model != null) {
                // Header with chart and filters
                TimeRangeSelector(
                    selectedRange = model.weightTimeRange,
                    onRangeSelected = onRangeChange
                )

                Spacer(modifier = Modifier.height(16.dp))

                WeightLineChart(
                    points = model.chartPoints,
                    minHealthyWeight = model.minHealthWeight ?: 0f,
                    maxHealthyWeight = model.maxHealthWeight ?: 0f,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(140.dp)
                )

                HorizontalDivider(
                    modifier = Modifier.padding(vertical = 20.dp),
                    color = OnboardingGlassTokens.GlassBorder.copy(alpha = 0.5f)
                )

                // Statistics
                WeightBody(
                    model = model,
                    onWeightSave = onWeightKgUpdate
                )
            } else {
                // Loading state
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            }
        }
    }
}

@Composable
fun TimeRangeSelector(
    selectedRange: WeightTimeRange,
    onRangeSelected: (WeightTimeRange) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        WeightTimeRange.entries.forEach { range ->
            val isSelected = range == selectedRange

            // Color animation
            val backgroundColor by animateColorAsState(
                if (isSelected) OnboardingGlassTokens.Primary
                else OnboardingGlassTokens.GlassSurfaceStrong.copy(alpha = 0.3f),
                label = "bgColor"
            )
            val contentColor by animateColorAsState(
                if (isSelected) Color.White
                else OnboardingGlassTokens.TextSecondary,
                label = "textColor"
            )

            Surface(
                onClick = { onRangeSelected(range) },
                modifier = Modifier.weight(1f),
                shape = CircleShape,
                color = backgroundColor,
                border = BorderStroke(
                    width = 1.dp,
                    color = if (isSelected) Color.Transparent else OnboardingGlassTokens.GlassBorder
                )
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text(
                        text = when (range) {
                            WeightTimeRange.WEEK -> stringResource(R.string.week_str)
                            WeightTimeRange.MONTH -> stringResource(R.string.month_str)
                            WeightTimeRange.QUARTER -> stringResource(R.string.quarter)
                            WeightTimeRange.YEAR -> stringResource(R.string.year_str)
                        },
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                        color = contentColor
                    )
                }
            }
        }
    }
}

@Composable
fun WeightLineChart(
    points: List<Float>,
    minHealthyWeight: Float,
    maxHealthyWeight: Float,
    modifier: Modifier = Modifier
) {
    val primaryColor = OnboardingGlassTokens.Primary
    val errorColor = Color(0xFFEF5350)
    val pathColor = OnboardingGlassTokens.TextPrimary.copy(alpha = 0.6f)

    Canvas(modifier = modifier) {
        if (points.isEmpty()) return@Canvas

        // Y-axis
        // Health range with 10% padding both top and bottom
        val allValues = points + listOf(minHealthyWeight, maxHealthyWeight)
        val rawMax = allValues.maxOrNull() ?: 100f
        val rawMin = allValues.minOrNull() ?: 50f
        val padding = (rawMax - rawMin) * 0.15f

        val maxY = rawMax + padding
        val minY = rawMin - padding

        // Coordinate mapping
        val getX = { index: Int ->
            if (points.size > 1) {
                index * (size.width / (points.size - 1))
            } else size.width / 2
        }

        val getY = { value: Float ->
            size.height - ((value - minY) / (maxY - minY) * size.height)
        }

        // Background
        val healthyTop = getY(maxHealthyWeight)
        val healthyBottom = getY(minHealthyWeight)

        drawRect(
            color = primaryColor.copy(alpha = 0.08f),
            topLeft = Offset(0f, healthyTop),
            size = Size(size.width, (healthyBottom - healthyTop).coerceAtLeast(1f))
        )

        // Пунктирные линии границ здоровья
        val pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
        drawLine(
            color = primaryColor.copy(alpha = 0.2f),
            start = Offset(0f, healthyTop),
            end = Offset(size.width, healthyTop),
            pathEffect = pathEffect,
            strokeWidth = 1.dp.toPx()
        )
        drawLine(
            color = primaryColor.copy(alpha = 0.2f),
            start = Offset(0f, healthyBottom),
            end = Offset(size.width, healthyBottom),
            pathEffect = pathEffect,
            strokeWidth = 1.dp.toPx()
        )

        // Path
        val strokePath = Path().apply {
            points.forEachIndexed { i, weight ->
                val x = getX(i)
                val y = getY(weight)
                if (i == 0) moveTo(x, y) else lineTo(x, y)
            }
        }

        drawPath(
            path = strokePath,
            color = pathColor,
            style = Stroke(width = 3.dp.toPx(), cap = StrokeCap.Round, join = StrokeJoin.Round)
        )

        // Path dots
        points.forEachIndexed { i, weight ->
            val isHealthy = weight in minHealthyWeight..maxHealthyWeight
            val pointColor = if (isHealthy) primaryColor else errorColor

            val center = Offset(getX(i), getY(weight))

            // Dot glow
            drawCircle(
                color = pointColor.copy(alpha = 0.2f),
                radius = 7.dp.toPx(),
                center = center
            )
            // Dot
            drawCircle(
                color = pointColor,
                radius = 4.dp.toPx(),
                center = center
            )
        }
    }
}

@Composable
private fun WeightBody(
    model: HomeWeightCardUiModel,
    onWeightSave: (Double) -> Unit
) {
    var enteredWeight by remember(model.latestWeightKg) {
        mutableStateOf(model.latestWeightKg.toString())
    }

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = stringResource(R.string.kg_formatted_unit_short_str, model.latestWeightKg),
                    style = MaterialTheme.typography.displaySmall,
                    fontWeight = FontWeight.Bold,
                    color = OnboardingGlassTokens.TextPrimary
                )

                // Weight dynamics
                model.weightChangeKg?.let { change ->
                    val isLoss = change < 0
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            painter = painterResource(
                                if (isLoss) R.drawable.trending_down_svgrepo_com
                                else R.drawable.trending_up_svgrepo_com
                            ),
                            contentDescription = null,
                            tint = if (isLoss) Color.Green else Color.Red,
                            modifier = Modifier.size(16.dp)
                        )
                        Text(
                            text = "${if (change > 0) "+" else ""}$change " + stringResource(R.string.kg_unit_short_str),
                            style = MaterialTheme.typography.bodyMedium,
                            color = if (isLoss) Color.Green else Color.Red
                        )
                    }
                }
            }

            // BMI
            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = stringResource(R.string.bmi_float_str, model.bmiValue ?: 0.0),
                    style = MaterialTheme.typography.titleMedium,
                    color = OnboardingGlassTokens.TextPrimary
                )
                Text(
                    text = getBmiCategory(model.bmiValue ?: 0.0),
                    style = MaterialTheme.typography.labelSmall,
                    color = OnboardingGlassTokens.TextTertiary
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Controls
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            AmountStepper(
                value = enteredWeight,
                unit = stringResource(R.string.kg_unit_short_str),
                onValueChange = { enteredWeight = it },
                onIncrease = {
                    enteredWeight = ((enteredWeight.toDoubleOrNull() ?: 0.0) + 0.1).format(1)
                },
                onDecrease = {
                    enteredWeight = ((enteredWeight.toDoubleOrNull() ?: 0.0) - 0.1).format(1)
                }
            )

            GlassActionButton(
                icon = painterResource(R.drawable.save_floppy_svgrepo_com), // Иконка сохранения
                buttonSize = 48.dp,
                containerColor = OnboardingGlassTokens.Primary,
                onClick = { onWeightSave(enteredWeight.toDoubleOrNull() ?: 0.0) }
            )
        }
    }
}

fun Double.format(digits: Int): String {
    return String.format(Locale.US, "%.${digits}f", this)
}

@Composable
private fun getBmiCategory(bmi: Double): String = when {
    bmi < 16.0 -> "severe thinness"
    bmi in 16.0..17.0 -> "moderate thinness"
    bmi in 17.0..18.5 -> "mild thinness"
    bmi in 18.5..25.0 -> "normal"
    bmi in 25.0..30.0 -> "overweight"
    bmi in 30.0..35.0 -> "obese I"
    bmi in 35.0..40.0 -> "obese II"
    else -> "obese III"
}

@Preview(
    wallpaper = Wallpapers.GREEN_DOMINATED_EXAMPLE,
    locale = "en"
)
@Composable
private fun WeightSummaryCardPreview() {

    val model = remember { previewHomeState().weightCard }

    NutriLogTheme(
        darkTheme = true
    ) {
        WeightSummaryCard(
            state = HomeState.LoadingState.Idle,
            model = model,
            hazeState = rememberHazeState(),
            onWeightKgUpdate = {},
            onRangeChange = {}
        )
    }
}