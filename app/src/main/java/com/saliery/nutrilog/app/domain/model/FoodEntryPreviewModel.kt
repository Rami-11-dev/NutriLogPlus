package com.saliery.nutrilog.app.domain.model

import java.time.LocalDateTime

data class FoodEntryPreviewModel(
    val id: Long,
    val productId: String,
    val productName: String?,
    val productBrand: String?,
    val amountGrams: Double,
    val kCaloriesTotal: Double,
    val dateTime: LocalDateTime
)
