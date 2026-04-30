package com.saliery.nutrilog.app.presentation.common

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.saliery.nutrilog.app.domain.model.business.DonutChartSegment
import com.saliery.nutrilog.ui.theme.OnboardingGlassTokens

@Composable
fun GapDonutChart(
    segments: List<DonutChartSegment>,
    chartSize: Dp = 220.dp,
    strokeWidth: Dp = 22.dp,
    gapAngle: Float = 16f,
    modifier: Modifier = Modifier,
    centerContent: @Composable BoxScope.() -> Unit = {}
) {

    val total = segments.sumOf { it.value.toDouble() }
        .toFloat()
        .coerceAtLeast(0.0001f)
    val totalGap = gapAngle * segments.size
    val availableAngle = 360f - totalGap

    Box(
        modifier = modifier.size(chartSize),
        contentAlignment = Alignment.Center,
    ) {
        Canvas(
            modifier = Modifier.fillMaxSize()
        ) {
            val strokePx = strokeWidth.toPx()
            val diameter = size.minDimension
            val arcSize = Size(
                width = diameter - strokePx,
                height = diameter - strokePx
            )
            val topLeft = Offset(
                x = (size.width - arcSize.width) / 2f,
                y = (size.height - arcSize.height) / 2f
            )

            drawArc(
                color = Color.White.copy(alpha = 0.08f),
                startAngle = 0f,
                sweepAngle = 360f,
                useCenter = false,
                topLeft = topLeft,
                size = arcSize,
                style = Stroke(
                    width = strokePx,
                    cap = StrokeCap.Round
                )
            )

            var startAngle = -90f

            segments.forEach { segment ->
                val sweepAngle = (segment.value / total) * availableAngle

                drawArc(
                    color = segment.color,
                    startAngle = startAngle,
                    sweepAngle = sweepAngle,
                    useCenter = false,
                    topLeft = topLeft,
                    size = arcSize,
                    style = Stroke(
                        width = strokePx,
                        cap = StrokeCap.Round
                    )
                )

                startAngle += sweepAngle + gapAngle
            }
        }

        Box(
            contentAlignment = Alignment.Center,
            content = centerContent,
            modifier = Modifier
                .size(chartSize - strokeWidth * 2.4f)
                .clip(CircleShape)
                .background(OnboardingGlassTokens.GlassSurfaceStrong)
        )
    }
}
