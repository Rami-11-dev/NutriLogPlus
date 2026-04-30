package com.saliery.nutrilog.app.presentation.common

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.saliery.nutrilog.R
import com.saliery.nutrilog.app.domain.model.business.WaterScaleDefaults

@Composable
fun WaterScaleContent(
    liters: Float,
    modifier: Modifier = Modifier
) {
    val currentBand = remember(liters) {
        WaterScaleDefaults.bands.firstOrNull { liters >= it.start && liters < it.end }
            ?: WaterScaleDefaults.bands.last()
    }

    MetricScaleContent(
        title = stringResource(R.string.daily_water_str),
        valueText = stringResource(R.string.recommended_liters_value_short, liters),
        badgeText = currentBand.label,
        value = liters,
        minValue = 1.5f,
        maxValue = 4.0f,
        bands = WaterScaleDefaults.getBands(LocalContext.current),
        ticks = WaterScaleDefaults.ticks,
        modifier = modifier
    )
}