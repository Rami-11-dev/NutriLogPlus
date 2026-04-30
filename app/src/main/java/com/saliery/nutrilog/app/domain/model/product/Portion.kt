package com.saliery.nutrilog.app.domain.model.product

data class Portion(
    val measureUnit: String,     // "g", "ml", "cup", "tbsp", etc.
    val gramWeight: Double,
    val description: String,     // "serving", "1 waffle", "1 cup"
    val amount: Double           // 1.0, 0.5, etc.
)
