package com.saliery.nutrilog.app.domain.repository

import com.saliery.nutrilog.app.domain.model.business.ProductLoadState
import com.saliery.nutrilog.app.domain.model.product.FoodProductModel
import com.saliery.nutrilog.app.domain.model.product.FoodProductModelLite
import com.saliery.nutrilog.app.domain.model.product.TypeToSearchProductModel
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    fun searchProducts(query: String): Flow<List<FoodProductModelLite>>
    fun observeProductById(productId: String): Flow<ProductLoadState>
    suspend fun refreshProduct(productId: String): Result<FoodProductModel?>
}