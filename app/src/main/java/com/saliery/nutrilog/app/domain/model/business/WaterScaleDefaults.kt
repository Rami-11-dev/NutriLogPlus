package com.saliery.nutrilog.app.domain.model.business

import android.content.Context
import androidx.compose.ui.graphics.Color
import com.saliery.nutrilog.R

object WaterScaleDefaults {

    fun getBands(context: Context): List<ScaleBand> {
        val bands = listOf(
            ScaleBand(1.5f, 2.0f, Color(0xFF58A9FF), context.getString(R.string.low_str)),
            ScaleBand(2.0f, 3.2f, Color(0xFF2CA56B), context.getString(R.string.normal_str)),
            ScaleBand(3.2f, 4.0f, Color(0xFFF0B34F), context.getString(R.string.high_str))
        )
        return bands
    }
    val bands = listOf(
        ScaleBand(1.5f, 2.0f, Color(0xFF58A9FF), "Low"),
        ScaleBand(2.0f, 3.2f, Color(0xFF2CA56B), "Good"),
        ScaleBand(3.2f, 4.0f, Color(0xFFF0B34F), "High")
    )

    val ticks = listOf("1.5", "2.0", "2.5", "3.0", "4.0")
}