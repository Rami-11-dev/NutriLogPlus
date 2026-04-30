package com.saliery.nutrilog.app.data.local.room.entity.product

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.saliery.nutrilog.app.domain.model.product.NutritionBasis
import com.saliery.nutrilog.app.domain.model.product.ProductDataSourceEnum

@Entity(
    tableName = "product",
    indices = [
        Index(value = ["source"]),
        Index(value = ["productName"]),
        Index(value = ["productBrand"]),
        Index(value = ["foodCategory"])
    ]
)
data class ProductEntity(
    @PrimaryKey(autoGenerate = false)
    val id: String,                     // OFF: barcode/code, USDA: fdcId.toString()

    val source: ProductDataSourceEnum,                 // DataSource.name

    // ---- Basic identification ----
    val productName: String?,
    val productBrand: String?,
    val foodCategory: String?,

    // ---- Quality / classification ----
    val nutritionGrade: String?,        // OFF
    val novaGroup: Int?,                // OFF

    // ---- Product info ----
    val ingredientsText: String?,
    val allergensRaw: List<String> = emptyList(),
    val additivesRaw: List<String> = emptyList(),
    val tracesRaw: List<String> = emptyList(),

    // ---- Common nutrition facts ----
    val nutritionBasis: NutritionBasis,         // NutritionBasis.name

    val kcal: Double?,
    val kJ: Double?,

    val protein: Double?,
    val totalFat: Double?,
    val carbohydrates: Double?,

    val sugars: Double?,
    val addedSugars: Double?,
    val fiber: Double?,
    val starch: Double?,

    val saturatedFat: Double?,
    val monounsaturatedFat: Double?,
    val polyunsaturatedFat: Double?,
    val transFat: Double?,
    val cholesterolMg: Double?,

    val sodiumMg: Double?,
    val saltG: Double?,

    // ---- Source metadata ----
    val sourceDataType: String?,
    val sourcePublicationDate: Long?,
    val sourceExternalNumericId: Int?,
    val sourceLastModified: Long?,
    val sourceLastEditor: String?,

    val packageQuantity: Double?,
    val packageQuantityUnit: String?,
    val packageQuantityText: String?,

    // ---- App flags ----
    val isCached: Boolean = true,
    val isFavorite: Boolean = false
)