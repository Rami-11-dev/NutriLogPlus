package com.saliery.nutrilog.app.data.local.room.entity.product

data class ProductSearchPreviewEntity(
    val id: String,
    val productName: String?,
    val productBrand: String?,
    val foodCategory: String?,
    val nutritionGrade: String?
)