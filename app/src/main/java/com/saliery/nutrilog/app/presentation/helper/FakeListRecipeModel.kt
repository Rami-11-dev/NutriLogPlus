package com.saliery.nutrilog.app.presentation.helper

import com.saliery.nutrilog.app.domain.model.recipe.RecipeListItemModel
import java.time.LocalDateTime

fun previewRecipeList(): List<RecipeListItemModel> {

    return listOf(
        RecipeListItemModel(
            id = 1L,
            name = "Классическое дрожжевое тесто",
            servingsYield = 1.0,
            ingredientCount = 5,
            totalCalories = 2650.0,
            totalProteins = 75.0,
            totalFats = 25.0,
            totalCarbs = 520.0,
            updatedAt = LocalDateTime.now()
        ),
        RecipeListItemModel(
            id = 2L,
            name = "Слоёное тесто",
            servingsYield = 8.0,
            ingredientCount = 4,
            totalCalories = 3.0,
            totalProteins = 32.0,
            totalFats = 240.0,
            totalCarbs = 256.0,
            updatedAt = LocalDateTime.now()
        ),
        RecipeListItemModel(
            id = 3L,
            name = "Пресное тесто для пельменей",
            servingsYield = 6.0,
            ingredientCount = 4,
            totalCalories = 1800.0,
            totalProteins = 54.0,
            totalFats = 18.0,
            totalCarbs = 324.0,
            updatedAt = LocalDateTime.now()
        ),
        RecipeListItemModel(
            id = 4L,
            name = "Бисквитное тесто",
            servingsYield = 5.0,
            ingredientCount = 6,
            totalCalories = 1750.0,
            totalProteins = 50.0,
            totalFats = 70.0,
            totalCarbs = 210.0,
            updatedAt = LocalDateTime.now()
        ),
        RecipeListItemModel(
            id = 5L,
            name = "Тесто для пиццы",
            servingsYield = 1.0,
            ingredientCount = 7,
            totalCalories = 3000.0,
            totalProteins = 90.0,
            totalFats = 36.0,
            totalCarbs = 540.0,
            updatedAt = LocalDateTime.now()
        )
    )
}