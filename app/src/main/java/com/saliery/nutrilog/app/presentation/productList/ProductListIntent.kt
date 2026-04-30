package com.saliery.nutrilog.app.presentation.productList

sealed interface ProductListIntent {
    data class QueryChanged(val value: String) : ProductListIntent
    data class RunSearch(val query: String) : ProductListIntent
    data object SearchSubmitted : ProductListIntent
    data object RetryClicked : ProductListIntent
    data class ProductClicked(val productId: String) : ProductListIntent
    data object ClearQueryClicked : ProductListIntent
}