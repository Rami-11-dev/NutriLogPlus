package com.saliery.nutrilog.app.presentation.common

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.ScrollableDefaults
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.hapticfeedback.HapticFeedback
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.Wallpapers
import androidx.compose.ui.unit.dp
import com.saliery.nutrilog.R
import com.saliery.nutrilog.ui.theme.NutriLogTheme
import com.saliery.nutrilog.ui.theme.OnboardingGlassTokens
import kotlin.math.abs
import kotlin.math.roundToInt

@Composable
fun GlassNumberWheelCard(
    isDouble: Boolean = true,
    shape: Shape = RoundedCornerShape(24.dp),
    trailingUnitIcon: Painter = painterResource(R.drawable.weight_24px),
    trailingUnitText: String = "Kg",
    minValue: Int = 0,
    maxValue: Int = 500,
    defaultValue: Double,
    onValueChanged: (Double) -> Unit
) {

    val haptic = LocalHapticFeedback.current
    val coercedValue = defaultValue.coerceIn(minValue.toDouble(), maxValue.toDouble())
    val mainPart = coercedValue.toInt()
    val decimalPart = ((coercedValue - mainPart) * 10).roundToInt().coerceIn(0, 9)

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation = 44.dp,
                shape = shape,
                ambientColor = OnboardingGlassTokens.Glow,
                spotColor = OnboardingGlassTokens.Glow
            )
            .clip(shape)
            .background(OnboardingGlassTokens.GlassSurfaceStrong)
            .border(1.dp, OnboardingGlassTokens.GlassBorder, shape)
            .height(140.dp)
    ) {

        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {

            NumberWheel(
                minValue = minValue,
                maxValue = maxValue,
                selectedValue = mainPart,
                onValueChanged = { newMainPart ->
                    onValueChanged(newMainPart + decimalPart / 10.0)
                },
                modifier = Modifier.weight(1f),
                haptic = haptic
            )

            if (isDouble) {
                VerticalDivider(
                    color = OnboardingGlassTokens.TextSecondary,
                    modifier = Modifier
                        .padding(horizontal = 20.dp, vertical = 20.dp)
                )

                NumberWheel(
                    minValue = 0,
                    maxValue = 9,
                    selectedValue = decimalPart,
                    onValueChanged = { newDecimal ->
                        onValueChanged(mainPart + newDecimal / 10.0)
                    },
                    modifier = Modifier.weight(1f),
                    haptic = haptic
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(end = 8.dp)
            ) {
                Icon(
                    painter = trailingUnitIcon,
                    contentDescription = null,
                    tint = OnboardingGlassTokens.TextSecondary,
                    modifier = Modifier
                        .size(34.dp)

                )

                Text(
                    text = trailingUnitText,
                    style = MaterialTheme.typography.displaySmall,
                    color = OnboardingGlassTokens.TextSecondary
                )
            }
        }
    }
}

@Composable
private fun NumberWheel(
    minValue: Int,
    maxValue: Int,
    selectedValue: Int,
    haptic: HapticFeedback? = null,
    modifier: Modifier = Modifier,
    onValueChanged: (Int) -> Unit
) {

    val safeSelectedValue = selectedValue.coerceIn(minValue, maxValue)
    val initialIndex = safeSelectedValue - minValue
    val scrollState = rememberLazyListState(
        initialFirstVisibleItemIndex = initialIndex
    )

    var centerVisible by rememberSaveable { mutableIntStateOf(initialIndex) }
    var lastHapticValue by rememberSaveable { mutableIntStateOf(safeSelectedValue) }

    LaunchedEffect(selectedValue) {
        // Scroll to default
        scrollState.scrollToItem((selectedValue - minValue).coerceAtLeast(0))
    }

    LaunchedEffect(scrollState) {
        snapshotFlow { scrollState.layoutInfo.visibleItemsInfo }
            .collect { visibleItems ->
                if (visibleItems.isNotEmpty()) {
                    val viewPortStart = scrollState.layoutInfo.viewportStartOffset
                    val viewPortEnd = scrollState.layoutInfo.viewportEndOffset
                    val viewPortCenter = (viewPortStart + viewPortEnd) / 2f

                    val centered = visibleItems.minByOrNull { itemInfo ->
                        val itemCenter = itemInfo.offset + itemInfo.size / 2f
                        abs(itemCenter - viewPortCenter)
                    }

                    centered?.let { item ->
                        val newValue = minValue + item.index
                        centerVisible = item.index

                        // Haptic response on value change
                        if (newValue != lastHapticValue) {
                            haptic?.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                            lastHapticValue = newValue
                            onValueChanged(newValue)
                        }
                    }
                }
            }
    }

    LazyColumn(
        state = scrollState,
        contentPadding = PaddingValues(vertical = 56.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .height(140.dp)
            .clip(RoundedCornerShape(22.dp)),
        flingBehavior = ScrollableDefaults.flingBehavior()
    ) {
        items(maxValue - minValue + 1) { index ->
            val value = minValue + index
            val isCentered = index == centerVisible

            val textColor = if (isCentered) {
                OnboardingGlassTokens.TextPrimary
            } else {
                OnboardingGlassTokens.TextSecondary.copy(alpha = 0.20f)
            }

            val fontWeight = if (isCentered) FontWeight.ExtraBold else FontWeight.Normal

            Text(
                text = value.toString(),
                style = MaterialTheme.typography.displaySmall,
                fontWeight = fontWeight,
                color = textColor,
                modifier = Modifier
                    .shadow(
                        elevation = if (isCentered) 18.dp else 0.dp,
                        shape = RoundedCornerShape(22.dp),
                        ambientColor = OnboardingGlassTokens.Glow,
                        spotColor = OnboardingGlassTokens.Glow
                    )
                    .animateContentSize()
            )
        }
    }
}

@Preview(
    wallpaper = Wallpapers.GREEN_DOMINATED_EXAMPLE
)
@Composable
private fun GlassNumberWheelCardPreview() {
    NutriLogTheme(
        darkTheme = true
    ) {
        GlassNumberWheelCard(
            defaultValue = 53.1,
            minValue = 15,
            maxValue = 150,
            onValueChanged = { decimal ->

            }
        )
    }
}