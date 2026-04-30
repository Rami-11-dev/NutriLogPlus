package com.saliery.nutrilog.app.domain.model

import com.saliery.nutrilog.app.domain.model.product.FoodProductModel
import java.time.LocalDateTime


data class FoodEntryDetailsModel(
    val id: Long,
    val product: FoodProductModel?,
    val amountGrams: Double,
    val kCaloriesTotal: Double,
    val dateTime: LocalDateTime
)
