package com.saliery.nutrilog.app.data.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.saliery.nutrilog.app.data.local.room.entity.product.ProductEntity
import com.saliery.nutrilog.app.data.local.room.entity.product.ProductEntityLiteProjection
import com.saliery.nutrilog.app.data.local.room.entity.product.ProductImageEntity
import com.saliery.nutrilog.app.data.local.room.entity.product.ProductMicronutrientEntity
import com.saliery.nutrilog.app.data.local.room.entity.product.ProductNutriscoreDataEntity
import com.saliery.nutrilog.app.data.local.room.entity.product.ProductPortionEntity
import com.saliery.nutrilog.app.data.local.room.entity.product.ProductSearchPreviewEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduct(product: ProductEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProductImages(images: List<ProductImageEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNutriscoreComponents(components: List<ProductNutriscoreDataEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProductPortions(portions: List<ProductPortionEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProductMicronutrients(micronutrients: ProductMicronutrientEntity)

    @Transaction
    suspend fun insertProductWithData(
        product: ProductEntity,
        images: List<ProductImageEntity>,
        nutriscore: List<ProductNutriscoreDataEntity>,
        portions: List<ProductPortionEntity>,
        micronutrients: ProductMicronutrientEntity?
    ) {

        insertProduct(product)

        insertProductImages(
            images.map { it.copy(productId = product.id) }
        )

        insertNutriscoreComponents(
            nutriscore.map { it.copy(productId = product.id) }
        )

        insertProductPortions(
            portions.map { it.copy(productId = product.id) }
        )

        micronutrients?.let {
            insertProductMicronutrients(it.copy(productId = product.id))
        }
    }


    @Query("SELECT * FROM product WHERE id = :productId")
    fun getProductByIdFlow(productId: String): Flow<ProductEntity?>

    @Query("SELECT * FROM product WHERE id = :productId")
    suspend fun getProductById(productId: String): ProductEntity?

    @Query("SELECT * FROM product_image WHERE productId = :productId ORDER BY imageType")
    fun getProductImagesByIdFlow(productId: String): Flow<List<ProductImageEntity>>

    @Query("SELECT * FROM product_image WHERE productId = :productId ORDER BY imageType")
    suspend fun getProductImagesById(productId: String): List<ProductImageEntity>

    @Query("SELECT * FROM product_portion WHERE productId = :productId")
    suspend fun getProductPortionsById(productId: String): List<ProductPortionEntity>

    @Query("SELECT * FROM product_portion WHERE productId = :productId")
    fun getProductPortionsByIdFlow(productId: String): Flow<List<ProductPortionEntity>>

    @Query("SELECT * FROM product_micronutrient WHERE productId = :productId LIMIT 1")
    suspend fun getProductMicronutrientsById(productId: String): ProductMicronutrientEntity?

    @Query("SELECT * FROM product_micronutrient WHERE productId = :productId LIMIT 1")
    fun getProductMicronutrientsByIdFlow(productId: String): Flow<ProductMicronutrientEntity?>

    @Query("SELECT * FROM product_nutriscore WHERE productId = :productId")
    fun getProductNutriscoreComponentsByIdFlow(productId: String): Flow<List<ProductNutriscoreDataEntity>>

    @Query("SELECT * FROM product_nutriscore WHERE productId = :productId")
    suspend fun getProductNutriscoreComponentsById(productId: String): List<ProductNutriscoreDataEntity>

    @Query("DELETE FROM product WHERE id = :productId")
    suspend fun deleteProduct(productId: String)

    @Query("SELECT COUNT(*) FROM product")
    fun getProductCountFlow(): Flow<Int>


    @Query("""
        SELECT
            p.id,
            p.source,
            p.productName,
            p.productBrand,
            p.nutritionGrade,
            p.foodCategory,
            p.kcal,
            p.carbohydrates,
            p.totalFat,
            p.protein,
            pi.displayUrl as displayImageUrl
        FROM product p
        LEFT JOIN product_image pi ON p.id = pi.productId
            AND pi.imageType = 'FRONT'
        WHERE p.productName LIKE '%' || :query || '%'
           OR p.productBrand LIKE '%' || :query || '%'
    """)
    fun searchProductsPreviewFlow(query: String): Flow<List<ProductEntityLiteProjection>>

    @Query("""
        SELECT
            p.id,
            p.source,
            p.productName,
            p.productBrand,
            p.nutritionGrade,
            p.foodCategory,
            p.kcal,
            p.carbohydrates,
            p.totalFat,
            p.protein,
            pi.displayUrl as displayImageUrl
        FROM product p
        LEFT JOIN product_image pi ON p.id = pi.productId
            AND pi.imageType = 'FRONT'
        WHERE p.id IN (:productIds)
    """)
    fun getProductEntitiesLiteFlow(
        productIds: List<String>
    ): Flow<List<ProductEntityLiteProjection>>

    @Query("""
        SELECT
            p.id,
            p.source,
            p.productName,
            p.productBrand,
            p.nutritionGrade,
            p.foodCategory,
            p.kcal,
            p.carbohydrates,
            p.totalFat,
            p.protein,
            pi.displayUrl as displayImageUrl
        FROM product p
        LEFT JOIN product_image pi ON p.id = pi.productId
            AND pi.imageType = 'FRONT'
        WHERE p.id IN (:productIds)
    """)
    suspend fun getProductEntitiesLite(
        productIds: List<String>
    ): List<ProductEntityLiteProjection>
}