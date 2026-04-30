package com.saliery.nutrilog.app.domain.model.entries

import java.time.LocalDateTime

data class MealEntryPreviewModel(

    val id: Long,
    val mealType: LocalMealType,
    val dateTime: LocalDateTime,
    val caloriesTotal: Double,
    val proteinsTotal: Double,
    val fatsTotal: Double,
    val carbsTotal: Double
)
