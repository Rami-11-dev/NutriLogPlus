package com.saliery.nutrilog.app.presentation.common

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.saliery.nutrilog.R
import com.saliery.nutrilog.app.domain.model.business.BmiScaleDefaults

@Composable
fun BmiScaleContent(
    bmi: Float,
    modifier: Modifier = Modifier
) {
    val currentBand = remember(bmi) {
        BmiScaleDefaults.bands.firstOrNull { bmi >= it.start && bmi < it.end }
            ?: BmiScaleDefaults.bands.last()
    }

    MetricScaleContent(
        title = stringResource(R.string.body_mass_index_bmi_str),
        valueText = "%.1f".format(bmi),
        badgeText = currentBand.label,
        value = bmi,
        minValue = 0f,
        maxValue = 50f,
        bands = BmiScaleDefaults.bands,
        ticks = BmiScaleDefaults.ticks,
        modifier = modifier
    )
}