package com.saliery.nutrilog.app.data.local.room.entity.entries

import com.saliery.nutrilog.app.domain.model.entries.LocalMealType
import java.time.LocalDateTime

data class MealEntryPreviewProjection(

    val id: Long,
    val mealType: LocalMealType,
    val dateTime: LocalDateTime,
    val caloriesTotal: Double,
    val proteinTotal: Double,
    val fatTotal: Double,
    val carbsTotal: Double
)
