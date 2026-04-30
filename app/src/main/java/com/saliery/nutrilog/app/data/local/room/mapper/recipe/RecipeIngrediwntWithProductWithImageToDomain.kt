package com.saliery.nutrilog.app.data.local.room.mapper.recipe

import com.saliery.nutrilog.app.data.local.room.entity.recipe.RecipeIngredientWithProductWithImageProjection
import com.saliery.nutrilog.app.domain.model.product.FoodProductModelLite
import com.saliery.nutrilog.app.domain.model.recipe.RecipeIngredientModel

fun RecipeIngredientWithProductWithImageProjection.toDomain(): RecipeIngredientModel {

    return RecipeIngredientModel(
        productPreview = FoodProductModelLite(
            id = productId,
            productName = productName,
            productBrand = productBrand,
            nutritionGrade = nutritionGrade,
            foodGroup = foodGroup,
            caloriesPer100g = energyKCaloriesPer100g,
            displayImage = displayImage,
            proteinsPer100g = proteinsPer100g,
            fatsPer100g = fatsPer100g,
            carbsPer100g = carbohydratesPer100g,
            source = source
        ),
        cookingMethod = cookingMethod,
        amountGrams = amountGrams
    )
}