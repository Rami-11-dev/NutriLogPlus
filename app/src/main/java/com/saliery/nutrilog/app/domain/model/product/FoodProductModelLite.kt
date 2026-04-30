package com.saliery.nutrilog.app.domain.model.product

data class FoodProductModelLite(
    val id: String,
    val source: ProductDataSourceEnum,
    val productName: String?,
    val productBrand: String?,
    val nutritionGrade: String?,
    val foodGroup: String?,
    val caloriesPer100g: Double?,
    val proteinsPer100g: Double?,
    val fatsPer100g: Double?,
    val carbsPer100g: Double?,
    val displayImage: String?
)
