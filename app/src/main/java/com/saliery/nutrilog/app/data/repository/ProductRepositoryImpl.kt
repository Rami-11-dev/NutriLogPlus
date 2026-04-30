package com.saliery.nutrilog.app.data.repository

import com.saliery.nutrilog.app.data.datasource.ProductDataSource
import com.saliery.nutrilog.app.data.local.room.mapper.product.toDomain
import com.saliery.nutrilog.app.domain.model.business.ProductLoadState
import com.saliery.nutrilog.app.domain.model.product.FoodProductModel
import com.saliery.nutrilog.app.domain.model.product.FoodProductModelLite
import com.saliery.nutrilog.app.domain.repository.ProductRepository
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import kotlin.collections.emptyList


class ProductRepositoryImpl(
    private val dataSource: ProductDataSource
) : ProductRepository {

    @OptIn(FlowPreview::class)
    override fun searchProducts(query: String): Flow<List<FoodProductModelLite>> {
        if (query.isBlank()) return flowOf(emptyList())
        return flow {
            val localResults = dataSource.searchProductsPreviewFlow(query)
                .first()
                .map { it.toDomain() }
            emit(localResults)
            if (localResults.isEmpty()) {
                val remoteResults = dataSource.searchProductsRemote(query)
                    .map { it.toDomain() }
                emit(remoteResults)
                Timber.d("Remote result size: ${remoteResults.size}")
            }
        }
    }

    override fun observeProductById(productId: String): Flow<ProductLoadState> = flow {
        var hasCheckedRemote = false

        try {
            dataSource.observeProduct(productId)
                .collect { bundle ->
                    val localProduct = bundle.product?.toDomain(
                        images = bundle.images,
                        nutriscore = bundle.nutriscore,
                        portions = bundle.portions,
                        micronutrients = bundle.micronutrients
                    )

                    if (localProduct != null) {
                        emit(ProductLoadState.Found(localProduct))
                    } else if (!hasCheckedRemote) {
                        // If local is null, we check the network
                        try {
                            val remoteBundle = dataSource.getProductByIdRemote(productId)

                            if (remoteBundle.product != null) {
                                dataSource.insertOrReplaceProductWithData(remoteBundle)
                                hasCheckedRemote = true
                            } else {
                                // NotFound if both of them are null.
                                emit(ProductLoadState.NotFound)
                                hasCheckedRemote = true
                            }
                        } catch (e: Exception) {
                            emit(ProductLoadState.Error(e))
                            hasCheckedRemote = true
                        }
                    }
                }
        } catch (e: Exception) {
            emit(ProductLoadState.Error(e))
        }
    }

    override suspend fun refreshProduct(productId: String): Result<FoodProductModel?> =
        withContext(Dispatchers.IO) {

        try {
            val remoteBundle = dataSource.getProductByIdRemote(productId)

            if (remoteBundle.product != null) {
                dataSource.insertOrReplaceProductWithData(
                    bundle = remoteBundle
                )
                Result.success(
                    remoteBundle.product.toDomain(
                        images = remoteBundle.images,
                        nutriscore = remoteBundle.nutriscore,
                        portions = remoteBundle.portions,
                        micronutrients = remoteBundle.micronutrients
                    )
                )
            } else {
                Result.success(null)
            }
        } catch (e: Exception) {
            Timber.e("Refresh product error: ${e.localizedMessage}")
            Result.failure(e)
        }
    }
}