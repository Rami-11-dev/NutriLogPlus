package com.saliery.nutrilog.app.presentation.productList

sealed interface ProductListEffect {
    data class OpenProduct(val productId: String) : ProductListEffect
    data class ShowMessage(val message: String) : ProductListEffect
}