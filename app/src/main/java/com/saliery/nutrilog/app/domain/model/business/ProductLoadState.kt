package com.saliery.nutrilog.app.domain.model.business

import com.saliery.nutrilog.app.domain.model.product.FoodProductModel

sealed class ProductLoadState {
    data class Found(val product: FoodProductModel) : ProductLoadState()
    object NotFound : ProductLoadState()
    data class Error(val exception: Throwable) : ProductLoadState()
}