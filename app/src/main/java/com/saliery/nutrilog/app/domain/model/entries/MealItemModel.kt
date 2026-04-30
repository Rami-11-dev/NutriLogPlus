package com.saliery.nutrilog.app.domain.model.entries

import com.saliery.nutrilog.app.domain.model.business.CookingMethod
import com.saliery.nutrilog.app.domain.model.product.FoodProductModelLite
import com.saliery.nutrilog.app.domain.model.recipe.RecipeModel

sealed class MealItemModel {
    abstract val id: Long
    abstract val cookingMethod: CookingMethod?

    data class ProductItemModel(
        override val id: Long,
        val product: FoodProductModelLite,
        val consumedGrams: Double,
        override val cookingMethod: CookingMethod? = null,
    ) : MealItemModel()

    data class RecipeItemModel(
        override val id: Long,
        val recipeModel: RecipeModel,
        val consumedServings: Double,
        override val cookingMethod: CookingMethod? = null
    ) : MealItemModel()
}
