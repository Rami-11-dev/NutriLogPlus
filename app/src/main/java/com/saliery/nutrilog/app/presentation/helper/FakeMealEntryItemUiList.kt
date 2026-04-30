package com.saliery.nutrilog.app.presentation.helper

import com.saliery.nutrilog.app.domain.model.product.FoodProductModelLite
import com.saliery.nutrilog.app.domain.model.product.ProductDataSourceEnum
import com.saliery.nutrilog.app.domain.model.recipe.RecipeListItemModel
import com.saliery.nutrilog.app.presentation.mealEntryScreen.AmountUnit
import com.saliery.nutrilog.app.presentation.mealEntryScreen.MealEntryItemUiModel
import java.time.LocalDateTime

fun previewMealEntryList(): List<MealEntryItemUiModel> {
    return listOf(
        MealEntryItemUiModel.ProductUiItem(
            id = 1L,
            amountInput = "150",
            amountUnit = AmountUnit.GRAMS,
            calories = 255.0,
            proteins = 26.0,
            fats = 15.0,
            carbs = 0.0,
            productPreview = FoodProductModelLite(
                id = "101",
                source = ProductDataSourceEnum.USDA_SR_LEGACY,
                productName = "Куриное филе",
                productBrand = null,
                nutritionGrade = "A",
                foodGroup = "Мясо и мясные продукты",
                caloriesPer100g = 165.0,
                proteinsPer100g = 31.0,
                fatsPer100g = 3.7,
                carbsPer100g = 0.0,
                displayImage = null
            )
        ),
        MealEntryItemUiModel.ProductUiItem(
            id = 2L,
            amountInput = "200",
            amountUnit = AmountUnit.GRAMS,
            calories = 260.0,
            proteins = 10.0,
            fats = 6.0,
            carbs = 42.0,
            productPreview = FoodProductModelLite(
                id = "102",
                source = ProductDataSourceEnum.OPEN_FOOD_FACTS,
                productName = "Белый рис",
                productBrand = "Golden Rice",
                nutritionGrade = "B",
                foodGroup = "Зерновые продукты",
                caloriesPer100g = 130.0,
                proteinsPer100g = 2.7,
                fatsPer100g = 0.3,
                carbsPer100g = 28.0,
                displayImage = null
            )
        ),
        MealEntryItemUiModel.RecipeUiItem(
            id = 3L,
            amountInput = "1",
            amountUnit = AmountUnit.PORTIONS,
            calories = 850.0,
            proteins = 45.0,
            fats = 35.0,
            carbs = 85.0,
            recipePreview = RecipeListItemModel(
                id = 201L,
                name = "Паста Карбонара",
                servingsYield = 1.0,
                ingredientCount = 5,
                totalCalories = 850.0,
                totalProteins = 45.0,
                totalFats = 35.0,
                totalCarbs = 85.0,
                updatedAt = LocalDateTime.now()
            )
        )
    )
}
