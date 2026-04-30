package com.saliery.nutrilog.app.domain.usecase

import com.saliery.nutrilog.app.domain.model.business.DailyNutritionSummary
import com.saliery.nutrilog.app.domain.model.entries.MealEntryModel
import com.saliery.nutrilog.app.domain.model.entries.MealEntryPreviewModel


class CalculateDailySummaryUseCase(
    private val calculateMealItemNutrition: CalculateMealItemNutritionUseCase
) {

    operator fun invoke(entries: List<*>): DailyNutritionSummary {
        var calories = 0.0
        var proteins = 0.0
        var fats = 0.0
        var carbs = 0.0

        entries.forEach { entry ->
            when (entry) {
                is MealEntryModel -> {
                    entry.items.forEach { item ->
                        val totals = calculateMealItemNutrition(item)
                        calories += totals.calories
                        proteins += totals.proteins
                        fats += totals.fats
                        carbs += totals.carbs
                    }
                }

                is MealEntryPreviewModel -> {
                    calories += entry.caloriesTotal
                    proteins += entry.proteinsTotal
                    fats += entry.fatsTotal
                    carbs += entry.carbsTotal
                }
            }
        }

        return DailyNutritionSummary(calories, proteins, fats, carbs)
    }
}