package com.saliery.nutrilog.app.presentation.recipe.helper

import com.saliery.nutrilog.app.domain.model.recipe.RecipeModel
import com.saliery.nutrilog.app.presentation.recipe.RecipeState
import java.time.LocalDateTime

fun RecipeState.toDomainOrNull(): RecipeModel? {
    val trimmedName = nameInput.trim()
    if (trimmedName.isBlank()) return null

    val servingsYield = servingsYieldInput
        .replace(',', '.')
        .toDoubleOrNull()
        ?.takeIf { it > 0.0 }
        ?: return null

    val ingredientsDomain = ingredients.mapNotNull { it.toDomainOrNull() }
    if (ingredientsDomain.isEmpty()) return null

    val now = LocalDateTime.now()

    return RecipeModel(
        id = recipeId ?: 0L,
        name = trimmedName,
        ingredients = ingredientsDomain,
        servingsYield = servingsYield,
        createdAt = createdAt ?: LocalDateTime.now(),
        updatedAt = now
    )
}