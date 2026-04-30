package com.saliery.nutrilog.app.data.local.room.mapper.recipe

import com.saliery.nutrilog.app.domain.model.recipe.RecipeListItemModel
import com.saliery.nutrilog.app.domain.model.recipe.RecipeModel

fun RecipeModel.toListItemModel(): RecipeListItemModel {
    val totalCalories = ingredients.sumOf { ingredient ->
        ((ingredient.productPreview.caloriesPer100g ?: 0.0) * ingredient.amountGrams) / 100.0
    }

    val totalProteins = ingredients.sumOf { ingredient ->
        ((ingredient.productPreview.proteinsPer100g ?: 0.0) * ingredient.amountGrams) / 100.0
    }

    val totalFats = ingredients.sumOf { ingredient ->
        ((ingredient.productPreview.fatsPer100g ?: 0.0) * ingredient.amountGrams) / 100.0
    }

    val totalCarbs = ingredients.sumOf { ingredient ->
        ((ingredient.productPreview.carbsPer100g ?: 0.0) * ingredient.amountGrams) / 100.0
    }

    return RecipeListItemModel(
        id = id,
        name = name,
        servingsYield = servingsYield,
        ingredientCount = ingredients.size,
        totalCalories = totalCalories,
        totalProteins = totalProteins,
        totalFats = totalFats,
        totalCarbs = totalCarbs,
        updatedAt = updatedAt
    )
}