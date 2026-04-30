package com.saliery.nutrilog.app.presentation.product

import com.saliery.nutrilog.app.domain.model.product.FoodProductModelLite

sealed interface ProductEffect {
    data class ShowMessage(val message: String) : ProductEffect
    data class OpenAddToMeal(val productPreview: FoodProductModelLite) : ProductEffect
}