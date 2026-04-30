package com.saliery.nutrilog.app.presentation.common

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.Wallpapers
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.saliery.nutrilog.app.domain.model.business.BmiCategory
import com.saliery.nutrilog.app.domain.model.business.BmiCats
import com.saliery.nutrilog.ui.theme.NutriLogTheme
import com.saliery.nutrilog.ui.theme.OnboardingGlassTokens

@Composable
fun BmiScale(
    bmi: Float,
    modifier: Modifier = Modifier,
    minBmi: Float = 0f,
    maxBmi: Float = 50f
) {
    val categories = listOf(
        BmiCategory(BmiCats.SEVERE_THINNESS, 0f, 16f, Color(0xFF3F51B5)),
        BmiCategory(BmiCats.MODERATE_THINNESS, 16f, 17f, Color(0xFF2196F3)),
        BmiCategory(BmiCats.MILD_THINNESS, 17f, 18.5f, Color(0xFF99FF99)),
        BmiCategory(BmiCats.NORMAL, 18.5f, 25f, Color(0xFF4CAF50)),
        BmiCategory(BmiCats.OVERWEIGHT, 25f, 30f, Color(0xFFFFAA00)),
        BmiCategory(BmiCats.OBESE_CLASS_I, 30f, 35f, Color(0xFFFF6600)),
        BmiCategory(BmiCats.OBESE_CLASS_II, 35f, 40f, Color(0xFFFF0000)),
        BmiCategory(BmiCats.OBESE_CLASS_III, 40f, 50f, Color(0xFFCC0000))
    )
    val target = bmi.coerceIn(minBmi, maxBmi)
    val animatedBmi by animateFloatAsState(
        targetValue = target,
        animationSpec = tween(durationMillis = 800),
        label = "bmi"
    )

    val currentBand = remember(animatedBmi) {
        categories.firstOrNull { animatedBmi >= it.start && animatedBmi < it.end }
            ?: categories.last()
    }

    val percent = ((animatedBmi - minBmi) / (maxBmi - minBmi)).coerceIn(0f, 1f)

    val shape = RoundedCornerShape(22.dp)

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(
                    elevation = 12.dp,
                    shape = RoundedCornerShape(28.dp),
                )
                .clip(shape)
                .background(OnboardingGlassTokens.GlassSurface)
                .border(
                    width = 1.dp,
                    color = OnboardingGlassTokens.GlassBorder,
                    shape = shape
                ),

            colors = CardDefaults.cardColors(containerColor = OnboardingGlassTokens.GlassSurfaceStrong)
        ) {
            Column(
                modifier = Modifier.padding(18.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Bottom
                ) {
                    Column {
                        Text(
                            text = "BMI",
                            color = Color.White.copy(alpha = 0.7f),
                            fontSize = 13.sp
                        )
                        Text(
                            text = "%.1f".format(animatedBmi),
                            color = Color.White,
                            fontSize = 30.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Surface(
                        shape = RoundedCornerShape(999.dp),
                        color = currentBand.color.copy(alpha = 0.18f),
                        contentColor = currentBand.color
                    ) {
                        Text(
                            text = currentBand.category.toString(),
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(88.dp)
                ) {
                    Canvas(modifier = Modifier.fillMaxSize()) {
                        val barY = size.height * 0.42f
                        val barHeight = size.height * 0.20f
                        val radius = barHeight / 2f
                        val barWidth = size.width

                        drawRoundRect(
                            brush = Brush.horizontalGradient(
                                colors = categories.map { it.color.copy(alpha = 0.95f) }
                            ),
                            topLeft = Offset(0f, barY),
                            size = Size(barWidth, barHeight),
                            cornerRadius = CornerRadius(radius, radius)
                        )

                        val bandOverlayAlpha = 0.14f
                        categories.forEach { band ->
                            val left = ((band.start - minBmi) / (maxBmi - minBmi)) * barWidth
                            val right = ((band.end - minBmi) / (maxBmi - minBmi)) * barWidth
                            if (currentBand == band) {
                                drawRoundRect(
                                    color = Color.White.copy(alpha = bandOverlayAlpha),
                                    topLeft = Offset(left, barY),
                                    size = Size(right - left, barHeight),
                                    cornerRadius = CornerRadius(radius, radius)
                                )
                            }
                            drawLine(
                                color = Color.White.copy(alpha = 0.28f),
                                start = Offset(right, barY),
                                end = Offset(right, barY + barHeight),
                                strokeWidth = 1.5.dp.toPx()
                            )
                        }

                        drawRoundRect(
                            color = Color.White.copy(alpha = 0.14f),
                            topLeft = Offset(0f, barY),
                            size = Size(barWidth, barHeight),
                            cornerRadius = CornerRadius(radius, radius),
                            style = Stroke(width = 1.dp.toPx())
                        )

                        val x = percent * barWidth
                        val centerY = barY + barHeight / 2f

                        drawLine(
                            color = Color.White.copy(alpha = 0.9f),
                            start = Offset(x, barY - 16.dp.toPx()),
                            end = Offset(x, barY + barHeight + 16.dp.toPx()),
                            strokeWidth = 3.dp.toPx()
                        )

                        drawCircle(
                            color = Color.Black.copy(alpha = 0.35f),
                            radius = 12.dp.toPx(),
                            center = Offset(x + 2.dp.toPx(), centerY + 3.dp.toPx())
                        )
                        drawCircle(
                            color = Color.White,
                            radius = 9.dp.toPx(),
                            center = Offset(x, centerY)
                        )
                        drawCircle(
                            brush = Brush.radialGradient(
                                listOf(Color.White, currentBand.color.copy(alpha = 0.9f))
                            ),
                            radius = 5.dp.toPx(),
                            center = Offset(x, centerY)
                        )
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.BottomCenter),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        listOf("0", "16", "18.5", "25", "30", "35", "40", "50").forEach {
                            Text(
                                text = it,
                                color = Color.White.copy(alpha = 0.55f),
                                fontSize = 10.sp
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(
    wallpaper = Wallpapers.RED_DOMINATED_EXAMPLE,
    showBackground = true
)
@Composable
private fun BmiScalePreview() {
    var bmi by remember { mutableFloatStateOf(22.4f) }

    NutriLogTheme(
        darkTheme = true
    ) {
        Column(Modifier.padding(16.dp)) {
            Slider(
                value = bmi,
                onValueChange = { bmi = it },
                valueRange = 0f..50f
            )

            Spacer(Modifier.height(16.dp))

            BmiScale(bmi = bmi)
        }
    }
}