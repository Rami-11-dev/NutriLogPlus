package com.saliery.nutrilog.app.domain.model.business

import androidx.compose.ui.graphics.Color

data class BmiCategory(
    val category: BmiCats,
    val start: Float,
    val end: Float,
    val color: Color
)
