package com.saliery.nutrilog.app.data.local.room.mapper.mealentry

import com.saliery.nutrilog.app.data.local.room.entity.entries.MealEntryPreviewProjection
import com.saliery.nutrilog.app.domain.model.entries.MealEntryPreviewModel

fun MealEntryPreviewProjection.toDomain(): MealEntryPreviewModel {

    return MealEntryPreviewModel(
        id = id,
        mealType = mealType,
        dateTime = dateTime,
        caloriesTotal = caloriesTotal,
        proteinsTotal = proteinTotal,
        fatsTotal = fatTotal,
        carbsTotal = carbsTotal
    )
}