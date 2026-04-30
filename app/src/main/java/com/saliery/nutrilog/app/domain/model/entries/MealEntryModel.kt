package com.saliery.nutrilog.app.domain.model.entries

import java.time.LocalDateTime

data class MealEntryModel(
    val id: Long,
    val mealType: LocalMealType,
    val dateTime: LocalDateTime,
    val items: List<MealItemModel>
)
