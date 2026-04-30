package com.saliery.nutrilog.app.data.datasource

import com.saliery.nutrilog.app.data.local.room.dao.ProductDao
import com.saliery.nutrilog.app.data.local.room.entity.product.ProductEntityLiteProjection
import com.saliery.nutrilog.app.data.remote.OpenFoodFactsApi
import com.saliery.nutrilog.app.data.remote.mapper.toEntities
import com.saliery.nutrilog.app.data.remote.mapper.toEntity
import com.saliery.nutrilog.app.data.remote.mapper.toNutriscoreDataEntities
import com.saliery.nutrilog.app.data.remote.mapper.toProductImageEntities
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import timber.log.Timber

class ProductDataSourceImpl(
    private val productDao: ProductDao,
    private val api: OpenFoodFactsApi
) : ProductDataSource {

    override fun observeProduct(productId: String): Flow<ProductLocalBundle> {
        return combine(
            productDao.getProductByIdFlow(productId),
            productDao.getProductImagesByIdFlow(productId),
            productDao.getProductNutriscoreComponentsByIdFlow(productId),
            productDao.getProductPortionsByIdFlow(productId),
            productDao.getProductMicronutrientsByIdFlow(productId)
        ) { product, images, nutriscore, portions, micronutrients ->
            ProductLocalBundle(
                product = product,
                images = images,
                nutriscore = nutriscore,
                portions = portions,
                micronutrients = micronutrients
            )
        }
    }

    override suspend fun insertOrReplaceProductWithData(bundle: ProductRemoteBundle) {
        val product = bundle.product ?: return

        productDao.insertProductWithData(
            product = product,
            images = bundle.images,
            nutriscore = bundle.nutriscore,
            portions = bundle.portions,
            micronutrients = bundle.micronutrients
        )
    }

    override suspend fun deleteProduct(productId: String) {
        productDao.deleteProduct(productId)
    }

    override fun getProductCount(): Flow<Int> {
        return productDao.getProductCountFlow()
    }

    override fun searchProductsPreviewFlow(query: String): Flow<List<ProductEntityLiteProjection>> {
        return productDao.searchProductsPreviewFlow(query)
    }

    override suspend fun searchProductsRemote(query: String): List<ProductEntityLiteProjection> {
        return try {
            val remoteResult = api.searchProducts(query = query)
            Timber.d("Remote search result by query '$query' is: ${remoteResult.toString()}")
            remoteResult.toEntities()
        } catch (e: CancellationException) {
            Timber.e("Coroutine has been cancelled: ${e.localizedMessage}")
            emptyList()
        } catch (e: Exception) {
            Timber.e("Remote search run into error: ${e.localizedMessage}")
            emptyList()
        }
    }

    override suspend fun getProductByIdRemote(productId: String): ProductRemoteBundle {
        return try {
            val response = api.getProductByBarcode(productId)

            Timber.d("API Response: ${response.product?.product_name.toString()}")

            ProductRemoteBundle(
                product = response.toEntity(),
                images = response.toProductImageEntities(),
                nutriscore = response.toNutriscoreDataEntities(),
                portions = emptyList(),        // OFF usually empty or computed later
                micronutrients = null          // OFF usually no detailed micronutrient entity
            )
        } catch (e: Exception) {
            Timber.e("Remote get by id error: ${e.localizedMessage}")
            ProductRemoteBundle(
                product = null,
                images = emptyList(),
                nutriscore = emptyList(),
                portions = emptyList(),
                micronutrients = null
            )
        }
    }
}