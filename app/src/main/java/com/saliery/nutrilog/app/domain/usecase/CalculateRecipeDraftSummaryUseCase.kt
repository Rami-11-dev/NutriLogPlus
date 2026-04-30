package com.saliery.nutrilog.app.domain.usecase

import com.saliery.nutrilog.app.presentation.recipe.EditableRecipeIngredientUiModel
import com.saliery.nutrilog.app.presentation.recipe.RecipeNutritionSummaryUiModel

class CalculateRecipeDraftSummaryUseCase {

    operator fun invoke(
        ingredients: List<EditableRecipeIngredientUiModel>,
        servingsYield: Double
    ): RecipeNutritionSummaryUiModel {
        val totalCalories = ingredients.sumOf { ingredient ->
            ((ingredient.productPreview.caloriesPer100g ?: 0.0) *
                    ingredient.amountGramsInput.toSafeDouble()) / 100.0
        }

        val totalProteins = ingredients.sumOf { ingredient ->
            ((ingredient.productPreview.proteinsPer100g ?: 0.0) *
                    ingredient.amountGramsInput.toSafeDouble()) / 100.0
        }

        val totalFats = ingredients.sumOf { ingredient ->
            ((ingredient.productPreview.fatsPer100g ?: 0.0) *
                    ingredient.amountGramsInput.toSafeDouble()) / 100.0
        }

        val totalCarbs = ingredients.sumOf { ingredient ->
            ((ingredient.productPreview.carbsPer100g ?: 0.0) *
                    ingredient.amountGramsInput.toSafeDouble()) / 100.0
        }

        val safeServings = servingsYield.takeIf { it > 0.0 } ?: 1.0

        return RecipeNutritionSummaryUiModel(
            totalCalories = totalCalories,
            totalProteins = totalProteins,
            totalFats = totalFats,
            totalCarbs = totalCarbs,
            perServingCalories = totalCalories / safeServings,
            perServingProteins = totalProteins / safeServings,
            perServingFats = totalFats / safeServings,
            perServingCarbs = totalCarbs / safeServings
        )
    }
}

private fun String.toSafeDouble(): Double {
    return replace(',', '.').toDoubleOrNull() ?: 0.0
}