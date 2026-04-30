package com.saliery.nutrilog.app.presentation.product

sealed interface ProductIntent {
    data class LoadProduct(val productId: String) : ProductIntent
    data object RefreshClicked : ProductIntent
    data object RetryClicked : ProductIntent

    data object ToggleFavoriteClicked : ProductIntent
    data object AddToMealClicked : ProductIntent

    data class SectionToggled(val section: ProductSection) : ProductIntent
}