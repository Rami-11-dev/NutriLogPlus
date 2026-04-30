package com.saliery.nutrilog.app.data.local.room.entity.recipe

import com.saliery.nutrilog.app.domain.model.business.CookingMethod
import com.saliery.nutrilog.app.domain.model.product.ProductDataSourceEnum

data class RecipeIngredientWithProductWithImageProjection(
    val id: Long,
    val recipeId: Long,
    val source: ProductDataSourceEnum,
    val cookingMethod: CookingMethod?,
    val amountGrams: Double,
    val productId: String,
    val productName: String?,
    val productBrand: String?,
    val nutritionGrade: String?,
    val foodGroup: String?,
    val energyKCaloriesPer100g: Double?,
    val carbohydratesPer100g: Double?,
    val fatsPer100g: Double?,
    val proteinsPer100g: Double?,
    val displayImage: String?
)
