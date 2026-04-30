package com.saliery.nutrilog.app.presentation.mealEntryScreen

import com.saliery.nutrilog.app.domain.model.business.CookingMethod
import com.saliery.nutrilog.app.domain.model.product.FoodProductModelLite
import com.saliery.nutrilog.app.domain.model.recipe.RecipeIngredientModel
import com.saliery.nutrilog.app.domain.model.recipe.RecipeModel
import java.time.LocalDateTime

data class RecipeDraft(
    val name: String,
    val ingredients: List<RecipeDraftIngredient>,
    val servingsYield: Double
)

data class RecipeDraftIngredient(
    val product: FoodProductModelLite,
    val amountGrams: Double
)

fun List<MealEntryItemUiModel>.toRecipeDraft(
    defaultName: String = "My recipe"
): RecipeDraft {

    val ingredients = buildList {
        this@toRecipeDraft.forEach { item ->
            when (item) {
                is MealEntryItemUiModel.ProductUiItem -> {
                    val grams = item.amountInput
                        .replace(',', '.')
                        .toDoubleOrNull() ?: return@forEach

                    add(
                        RecipeDraftIngredient(
                            product = item.productPreview,
                            amountGrams = grams
                        )
                    )
                }

                is MealEntryItemUiModel.RecipeUiItem -> {
                    // TODO: Not implemented yet. No logic created yet.
                }
            }
        }
    }

    return RecipeDraft(
        name = defaultName,
        ingredients = ingredients,
        servingsYield = 1.0
    )
}

fun RecipeDraft.toRecipeModel(): RecipeModel {

    val newIngredients = ingredients.map { ingredient ->
        RecipeIngredientModel(
            productPreview = ingredient.product,
            cookingMethod = CookingMethod.RAW,
            amountGrams = ingredient.amountGrams
        )
    }

    return RecipeModel(
        id = 0L,
        name = name,
        ingredients = newIngredients,
        servingsYield = servingsYield,
        createdAt = LocalDateTime.now(),
        updatedAt = LocalDateTime.now()
    )
}