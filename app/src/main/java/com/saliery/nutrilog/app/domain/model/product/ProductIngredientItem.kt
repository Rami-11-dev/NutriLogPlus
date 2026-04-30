package com.saliery.nutrilog.app.domain.model.product

data class ProductIngredientItem(
    val ingredientName: String,
    val percentEstimate: Double? = null
)
