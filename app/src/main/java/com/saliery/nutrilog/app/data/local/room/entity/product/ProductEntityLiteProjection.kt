package com.saliery.nutrilog.app.data.local.room.entity.product

import com.saliery.nutrilog.app.domain.model.product.ProductDataSourceEnum

data class ProductEntityLiteProjection(
    val id: String,
    val source: ProductDataSourceEnum,
    val productName: String?,
    val productBrand: String?,
    val nutritionGrade: String?,
    val foodCategory: String?,
    val kcal: Double?,
    val carbohydrates: Double?,
    val totalFat: Double?,
    val protein: Double?,
    val displayImageUrl: String?
)
