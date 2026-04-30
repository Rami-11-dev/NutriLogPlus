package com.saliery.nutrilog.app.presentation.helper

import com.saliery.nutrilog.app.domain.model.product.FoodProductModelLite
import com.saliery.nutrilog.app.domain.model.product.ProductDataSourceEnum

fun previewFoodProductModelLiteList(): List<FoodProductModelLite> {

    return listOf(
        FoodProductModelLite(
            id = "1",
            productName = "Куриное филе",
            productBrand = "Фермерское",
            nutritionGrade = "A",
            foodGroup = "Мясо и птица",
            caloriesPer100g = 165.0,
            proteinsPer100g = 31.0,
            fatsPer100g = 3.6,
            carbsPer100g = 0.0,
            displayImage = null,
            source = ProductDataSourceEnum.OPEN_FOOD_FACTS
        ),
        FoodProductModelLite(
            id = "2",
            productName = "Гречневая крупа",
            productBrand = "Природа",
            nutritionGrade = "B",
            foodGroup = "Зерновые",
            caloriesPer100g = 343.0,
            proteinsPer100g = 12.6,
            fatsPer100g = 3.4,
            carbsPer100g = 62.1,
            displayImage = null,
            source = ProductDataSourceEnum.OPEN_FOOD_FACTS
        ),
        FoodProductModelLite(
            id = "3",
            productName = "Бананы",
            productBrand = "Импорт",
            nutritionGrade = "A",
            foodGroup = "Фрукты",
            caloriesPer100g = 89.0,
            proteinsPer100g = 1.1,
            fatsPer100g = 0.3,
            carbsPer100g = 23.0,
            displayImage = null,
            source = ProductDataSourceEnum.USDA_SR_LEGACY
        ),
        FoodProductModelLite(
            id = "4",
            productName = "Йогурт натуральный",
            productBrand = "Молочный завод",
            nutritionGrade = "A",
            foodGroup = "Молочные продукты",
            caloriesPer100g = 59.0,
            proteinsPer100g = 3.5,
            fatsPer100g = 0.4,
            carbsPer100g = 4.7,
            displayImage = null,
            source = ProductDataSourceEnum.OPEN_FOOD_FACTS
        ),
        FoodProductModelLite(
            id = "5",
            productName = "Оливковое масло",
            productBrand = "Греческое",
            nutritionGrade = "B",
            foodGroup = "Масла и жиры",
            caloriesPer100g = 884.0,
            proteinsPer100g = 0.0,
            fatsPer100g = 100.0,
            carbsPer100g = 0.0,
            displayImage = null,
            source = ProductDataSourceEnum.USDA_BRANDED
        ),
        FoodProductModelLite(
            id = "6",
            productName = "Куриное филе",
            productBrand = "Фермерское",
            nutritionGrade = "A",
            foodGroup = "Мясо и птица",
            caloriesPer100g = 165.0,
            proteinsPer100g = 31.0,
            fatsPer100g = 3.6,
            carbsPer100g = 0.0,
            displayImage = null,
            source = ProductDataSourceEnum.OPEN_FOOD_FACTS
        ),
        FoodProductModelLite(
            id = "7",
            productName = "Гречневая крупа",
            productBrand = "Природа",
            nutritionGrade = "B",
            foodGroup = "Зерновые",
            caloriesPer100g = 343.0,
            proteinsPer100g = 12.6,
            fatsPer100g = 3.4,
            carbsPer100g = 62.1,
            displayImage = null,
            source = ProductDataSourceEnum.OPEN_FOOD_FACTS
        ),
        FoodProductModelLite(
            id = "8",
            productName = "Бананы",
            productBrand = "Импорт",
            nutritionGrade = "A",
            foodGroup = "Фрукты",
            caloriesPer100g = 89.0,
            proteinsPer100g = 1.1,
            fatsPer100g = 0.3,
            carbsPer100g = 23.0,
            displayImage = null,
            source = ProductDataSourceEnum.USDA_SR_LEGACY
        ),
        FoodProductModelLite(
            id = "9",
            productName = "Йогурт натуральный",
            productBrand = "Молочный завод",
            nutritionGrade = "A",
            foodGroup = "Молочные продукты",
            caloriesPer100g = 59.0,
            proteinsPer100g = 3.5,
            fatsPer100g = 0.4,
            carbsPer100g = 4.7,
            displayImage = null,
            source = ProductDataSourceEnum.OPEN_FOOD_FACTS
        ),
        FoodProductModelLite(
            id = "10",
            productName = "Оливковое масло",
            productBrand = "Греческое",
            nutritionGrade = "B",
            foodGroup = "Масла и жиры",
            caloriesPer100g = 884.0,
            proteinsPer100g = 0.0,
            fatsPer100g = 100.0,
            carbsPer100g = 0.0,
            displayImage = null,
            source = ProductDataSourceEnum.USDA_BRANDED
        )
    )
}