package com.saliery.nutrilog.app.presentation.home.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.Wallpapers
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.saliery.nutrilog.R
import com.saliery.nutrilog.app.presentation.common.GlassCard
import com.saliery.nutrilog.app.presentation.helper.previewHomeState
import com.saliery.nutrilog.app.presentation.home.HomeState
import com.saliery.nutrilog.app.presentation.home.HomeSummaryCardUiModel
import com.saliery.nutrilog.ui.theme.NutriLogTheme
import com.saliery.nutrilog.ui.theme.OnboardingGlassTokens
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.rememberHazeState

@Composable
fun HomeSummaryHeroCard(
    loadingState: HomeState.LoadingState,
    model: HomeSummaryCardUiModel?,
    hazeState: HazeState,
    modifier: Modifier = Modifier
) {
    GlassCard(
        hazeState = hazeState,
        modifier = modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (model == null) {
                CircularProgressIndicator(color = OnboardingGlassTokens.Primary)
            } else {

                Spacer(modifier = Modifier.height(16.dp))

                CalorieArcGauge(
                    consumed = model.caloriesConsumed,
                    goal = model.caloriesGoal ?: 2000.0
                )

                Spacer(modifier = Modifier.height(24.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    MacroProgressItem(
                        modifier = Modifier.weight(1f),
                        label = stringResource(R.string.protein_str),
                        current = model.proteinConsumed,
                        goal = model.proteinGoal ?: 0.0,
                        color = OnboardingGlassTokens.BmiBlue
                    )
                    MacroProgressItem(
                        modifier = Modifier.weight(1f),
                        label = stringResource(R.string.fat_str),
                        current = model.fatsConsumed,
                        goal = model.fatsGoal ?: 0.0,
                        color = OnboardingGlassTokens.BmiYellow
                    )
                    MacroProgressItem(
                        modifier = Modifier.weight(1f),
                        label = stringResource(R.string.carbs_str),
                        current = model.carbsConsumed,
                        goal = model.carbsGoal ?: 0.0,
                        color = OnboardingGlassTokens.BmiGreen
                    )
                }
            }
        }
    }
}

@Composable
private fun IdleState() {

}

@Composable
private fun CalorieArcGauge(
    consumed: Double,
    goal: Double
) {
    val remaining = (goal - consumed).coerceAtLeast(0.0)
    val progress = (consumed / goal).toFloat().coerceIn(0f, 1f)
    val animatedProgress by animateFloatAsState(targetValue = progress, label = "progress")

    val gradientColor = OnboardingGlassTokens.Primary.copy(alpha = 0.5f)
    val gradientColor2 = OnboardingGlassTokens.Primary

    Box(contentAlignment = Alignment.Center) {
        Canvas(modifier = Modifier.size(200.dp)) {
            val strokeWidth = 14.dp.toPx()
            val size = size.copy(width = size.width - strokeWidth, height = size.height - strokeWidth)
            val topLeft = Offset(strokeWidth / 2, strokeWidth / 2)

            // track
            drawArc(
                color = Color.White.copy(alpha = 0.1f),
                startAngle = 140f,
                sweepAngle = 260f,
                useCenter = false,
                topLeft = topLeft,
                size = size,
                style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
            )

            // track progress
            drawArc(
                brush = Brush.sweepGradient(
                    0f to gradientColor,
                    animatedProgress to gradientColor2,
                    center = center
                ),
                startAngle = 140f,
                sweepAngle = 260f * animatedProgress,
                useCenter = false,
                topLeft = topLeft,
                size = size,
                style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
            )
        }

        // inner text
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = remaining.toInt().toString(),
                style = MaterialTheme.typography.displayMedium,
                fontWeight = FontWeight.Black,
                color = OnboardingGlassTokens.TextPrimary
            )
            Text(
                text = stringResource(R.string.kcal_left_str),
                style = MaterialTheme.typography.labelMedium,
                color = OnboardingGlassTokens.TextSecondary
            )
        }
    }
}

@Composable
private fun MacroProgressItem(
    label: String,
    current: Double,
    goal: Double,
    color: Color,
    modifier: Modifier = Modifier
) {
    val progress = if (goal > 0) (current / goal).toFloat().coerceIn(0f, 1.2f) else 0f

    Column(modifier = modifier) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = OnboardingGlassTokens.TextTertiary
        )

        Spacer(modifier = Modifier.height(4.dp))

        // progress line
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(6.dp)
                .clip(CircleShape)
                .background(Color.White.copy(alpha = 0.05f))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(fraction = if (progress > 1f) 1f else progress)
                    .fillMaxHeight()
                    .background(color.copy(alpha = 0.8f))
            )
        }

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = stringResource(R.string.current_of_total_str, current.toInt(), goal.toInt()),
            style = MaterialTheme.typography.bodySmall,
            color = OnboardingGlassTokens.TextSecondary,
            fontSize = 10.sp
        )
    }
}

@Preview(
    wallpaper = Wallpapers.GREEN_DOMINATED_EXAMPLE,
    locale = "en"
)
@Composable
private fun HomeSummaryHeroCardPreview() {

    NutriLogTheme(
        darkTheme = true
    ) {
        HomeSummaryHeroCard(
            loadingState = HomeState.LoadingState.Idle,
            model = previewHomeState().summaryCard,
            hazeState = rememberHazeState()
        )
    }
}