package com.saliery.nutrilog.app.domain.model.business

import androidx.compose.ui.graphics.Color

data class ScaleBand(
    val start: Float,
    val end: Float,
    val color: Color,
    val label: String
)
