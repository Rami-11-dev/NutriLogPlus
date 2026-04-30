package com.saliery.nutrilog.app.presentation.helper

import com.saliery.nutrilog.app.domain.model.entries.LocalMealType
import com.saliery.nutrilog.app.domain.model.entries.MealEntryPreviewModel
import java.time.LocalDateTime

fun previewMealEntryPreviewModel(): List<MealEntryPreviewModel> {
    return listOf(
        MealEntryPreviewModel(
            id = 1L,
            mealType = LocalMealType.BREAKFAST,
            dateTime = LocalDateTime.of(2026, 4, 24, 8, 30),
            caloriesTotal = 450.0,
            proteinsTotal = 20.0,
            fatsTotal = 15.0,
            carbsTotal = 55.0
        ),
        MealEntryPreviewModel(
            id = 2L,
            mealType = LocalMealType.LUNCH,
            dateTime = LocalDateTime.of(2026, 4, 24, 12, 45),
            caloriesTotal = 650.0,
            proteinsTotal = 35.0,
            fatsTotal = 22.0,
            carbsTotal = 72.0
        ),
        MealEntryPreviewModel(
            id = 3L,
            mealType = LocalMealType.SNACK,
            dateTime = LocalDateTime.of(2026, 4, 24, 15, 15),
            caloriesTotal = 200.0,
            proteinsTotal = 8.0,
            fatsTotal = 6.0,
            carbsTotal = 28.0
        ),
        MealEntryPreviewModel(
            id = 4L,
            mealType = LocalMealType.DINNER,
            dateTime = LocalDateTime.of(2026, 4, 24, 19, 0),
            caloriesTotal = 750.0,
            proteinsTotal = 40.0,
            fatsTotal = 28.0,
            carbsTotal = 85.0
        ),
        MealEntryPreviewModel(
            id = 5L,
            mealType = LocalMealType.SNACK,
            dateTime = LocalDateTime.of(2026, 4, 24, 21, 30),
            caloriesTotal = 150.0,
            proteinsTotal = 5.0,
            fatsTotal = 4.0,
            carbsTotal = 22.0
        )
    )
}