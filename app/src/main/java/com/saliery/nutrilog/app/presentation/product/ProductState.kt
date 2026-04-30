package com.saliery.nutrilog.app.presentation.product

import com.saliery.nutrilog.app.domain.model.product.FoodProductModel

data class ProductState(
    val productId: String? = null,
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val isProductNotFound: Boolean = false,

    val product: FoodProductModel? = null,

    val expandedSections: Set<ProductSection> = setOf(
        ProductSection.NUTRITION,
        ProductSection.MICRONUTRIENTS,
        ProductSection.METADATA,
        ProductSection.INGREDIENTS,
        ProductSection.NUTRISCORE,
        ProductSection.PORTIONS
    ),

    val errorMessage: String? = null
)
