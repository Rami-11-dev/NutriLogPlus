package com.saliery.nutrilog.app.presentation.common

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun NutrientProgressBar(
    value: Float,
    minValue: Float,
    maxValue: Float,
    scaleMax: Float,
    modifier: Modifier = Modifier,
    barHeight: Dp = 6.dp,
    gap: Dp = 2.dp,
    neutralColor: Color = Color(0xFF3A3A3A),
    underColor: Color = Color(0xFF9BE7A6),
    healthyColor: Color = Color(0xFF3CCB6A),
    excessColor: Color = Color(0xFFFFA24A),
    healthyTrackColor: Color = Color(0x223CCB6A),
    markerColor: Color = Color(0xFF7A7A7A),
) {
    val safeMin = minValue.coerceAtLeast(0f)
    val safeMax = maxValue.coerceAtLeast(safeMin + 0.0001f)
    val safeScaleMax = scaleMax.coerceAtLeast(safeMax + 0.0001f)
    val clampedValue = value.coerceAtLeast(0f)

    Canvas(modifier = modifier.height(barHeight + 20.dp)) {
        val w = size.width
        val h = barHeight.toPx()
        val gapPx = gap.toPx()
        val radius = h / 2f
        val y = 0f

        fun xFor(v: Float): Float =
            (v / safeScaleMax).coerceIn(0f, 1f) * w

        fun drawMarker(x: Float) {
            drawLine(
                color = markerColor,
                start = Offset(x, h + 4.dp.toPx()),
                end = Offset(x, h + 12.dp.toPx()),
                strokeWidth = 1.5.dp.toPx()
            )
        }

        drawRoundRect(
            color = neutralColor,
            topLeft = Offset(0f, y),
            size = Size(w, h),
            cornerRadius = CornerRadius(radius, radius)
        )

        val healthyStartX = xFor(safeMin)
        val healthyEndX = xFor(safeMax)

        if (safeMin > 0f) {
            drawRoundRect(
                color = healthyTrackColor,
                topLeft = Offset(healthyStartX, y),
                size = Size((healthyEndX - healthyStartX).coerceAtLeast(0f), h),
                cornerRadius = CornerRadius(radius, radius)
            )
        }

        when {
            clampedValue <= 0f -> Unit

            clampedValue < safeMin -> {
                val endX = xFor(clampedValue)
                if (endX > 0f) {
                    drawRoundRect(
                        color = underColor,
                        topLeft = Offset(0f, y),
                        size = Size(endX, h),
                        cornerRadius = CornerRadius(radius, radius)
                    )
                }
            }

            clampedValue <= safeMax -> {
                val endX = xFor(clampedValue)
                drawRoundRect(
                    color = healthyColor,
                    topLeft = Offset(0f, y),
                    size = Size(endX, h),
                    cornerRadius = CornerRadius(radius, radius)
                )
            }

            else -> {
                drawRoundRect(
                    color = healthyColor,
                    topLeft = Offset(0f, y),
                    size = Size(healthyEndX, h),
                    cornerRadius = CornerRadius(radius, radius)
                )

                val excessStart = (healthyEndX + gapPx / 2f).coerceAtMost(w)
                val excessEnd = xFor(clampedValue)

                if (excessEnd > excessStart) {
                    drawRoundRect(
                        color = excessColor,
                        topLeft = Offset(excessStart, y),
                        size = Size(excessEnd - excessStart, h),
                        cornerRadius = CornerRadius(radius, radius)
                    )
                }
            }
        }

        drawMarker(0f)
        drawMarker(healthyStartX)
        drawMarker(healthyEndX)
    }
}

