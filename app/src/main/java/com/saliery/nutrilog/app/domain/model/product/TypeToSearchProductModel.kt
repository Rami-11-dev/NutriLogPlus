package com.saliery.nutrilog.app.domain.model.product

data class TypeToSearchProductModel(
    val barcode: String,
    val productName: String?,
    val productBrand: String?,
    val nutritionGrade: String?,
    val foodGroup: String?
)