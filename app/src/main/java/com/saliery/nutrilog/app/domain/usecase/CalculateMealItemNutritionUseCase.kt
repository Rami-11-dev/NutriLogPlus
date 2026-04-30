package com.saliery.nutrilog.app.domain.usecase

import com.saliery.nutrilog.app.domain.model.business.NutritionTotal
import com.saliery.nutrilog.app.domain.model.entries.MealItemModel

class CalculateMealItemNutritionUseCase {

    operator fun invoke(item: MealItemModel): NutritionTotal {
        return when (item) {
            is MealItemModel.ProductItemModel -> calculateProduct(item)
            is MealItemModel.RecipeItemModel -> calculateRecipe(item)
        }
    }

    private fun calculateProduct(item: MealItemModel.ProductItemModel): NutritionTotal {
        require(item.consumedGrams >= 0.0)
        val factor = item.consumedGrams / 100.0

        return NutritionTotal(
            calories = (item.product.caloriesPer100g ?: 0.0) * factor,
            proteins = (item.product.proteinsPer100g ?: 0.0) * factor,
            fats = (item.product.fatsPer100g ?: 0.0) * factor,
            carbs = (item.product.carbsPer100g ?: 0.0) * factor
        )
    }

    private fun calculateRecipe(item: MealItemModel.RecipeItemModel): NutritionTotal {
        require(item.recipeModel.servingsYield > 0.0)
        require(item.consumedServings >= 0.0)

        var calories = 0.0
        var proteins = 0.0
        var fats = 0.0
        var carbs = 0.0

        item.recipeModel.ingredients.forEach { ingredient ->
            val factor = (ingredient.amountGrams / item.recipeModel.servingsYield) *
                    item.consumedServings / 100.0

            calories += (ingredient.productPreview.caloriesPer100g ?: 0.0) * factor
            proteins += (ingredient.productPreview.proteinsPer100g ?: 0.0) * factor
            fats += (ingredient.productPreview.fatsPer100g ?: 0.0) * factor
            carbs += (ingredient.productPreview.carbsPer100g ?: 0.0) * factor
        }

        return NutritionTotal(
            calories = calories,
            proteins = proteins,
            fats = fats,
            carbs = carbs
        )
    }
}