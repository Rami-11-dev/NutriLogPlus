package com.saliery.nutrilog.app.data.datasource

import com.saliery.nutrilog.app.data.local.room.entity.product.ProductEntity
import com.saliery.nutrilog.app.data.local.room.entity.product.ProductEntityLiteProjection
import com.saliery.nutrilog.app.data.local.room.entity.product.ProductImageEntity
import com.saliery.nutrilog.app.data.local.room.entity.product.ProductNutriscoreDataEntity
import com.saliery.nutrilog.app.data.local.room.entity.product.ProductSearchPreviewEntity
import kotlinx.coroutines.flow.Flow

interface ProductDataSource {

    // Local
    fun observeProduct(productId: String): Flow<ProductLocalBundle>
    suspend fun insertOrReplaceProductWithData(bundle: ProductRemoteBundle)
    suspend fun deleteProduct(productId: String)
    fun getProductCount(): Flow<Int>
    fun searchProductsPreviewFlow(query: String): Flow<List<ProductEntityLiteProjection>>

    // Remote
    suspend fun searchProductsRemote(query: String): List<ProductEntityLiteProjection>
    suspend fun getProductByIdRemote(productId: String): ProductRemoteBundle
}