package com.saliery.nutrilog.app.presentation.helper

import androidx.compose.ui.res.painterResource
import com.saliery.nutrilog.R
import com.saliery.nutrilog.app.domain.model.product.FoodImageModel
import com.saliery.nutrilog.app.domain.model.product.FoodProductModel
import com.saliery.nutrilog.app.domain.model.product.ImageType
import com.saliery.nutrilog.app.domain.model.product.MicronutrientModel
import com.saliery.nutrilog.app.domain.model.product.NutriscoreComponent
import com.saliery.nutrilog.app.domain.model.product.NutriscoreSummary
import com.saliery.nutrilog.app.domain.model.product.NutritionFacts
import com.saliery.nutrilog.app.domain.model.product.Portion
import com.saliery.nutrilog.app.domain.model.product.ProductDataSourceEnum
import com.saliery.nutrilog.app.domain.model.product.ProductIngredientItem
import com.saliery.nutrilog.app.domain.model.product.ProductPackageInfo
import com.saliery.nutrilog.app.domain.model.product.SourceMetadata

fun previewFoodProduct(): FoodProductModel {
    return FoodProductModel(
        id = "1234567890",
        source = ProductDataSourceEnum.OPEN_FOOD_FACTS,
        name = "Греческий йогурт",
        brand = "Demo Brand",
        foodCategory = "Молочные продукты",
        nutritionGrade = "A",
        novaGroup = 2,
        ingredients = listOf(
            ProductIngredientItem(
                ingredientName = "молоко",
                percentEstimate = 95.0
            ),
            ProductIngredientItem(
                ingredientName = "закваска",
                percentEstimate = 5.0
            )
        ),
        allergens = listOf("milk"),
        additives = listOf("E12", "E123"),
        traces = listOf("peanuts", "nuts"),
        nutritionFacts = NutritionFacts(
            kcal = 59.0,
            kJ = 247.0,
            protein = 10.0,
            totalFat = 0.4,
            carbohydrates = 3.6,
            sugars = 3.2,
            addedSugars = 2.1,
            fiber = 0.0,
            starch = 0.3,
            saturatedFat = 0.1,
            monounsaturatedFat = 1.2,
            polyunsaturatedFat = 1.2,
            transFat = 0.4,
            cholesterolMg = 0.78,
            sodiumMg = 36.0,
            saltG = 0.09
        ),
        micronutrients = MicronutrientModel(
            potassiumMg = 141.0,
            calciumMg = 110.0,
            magnesiumMg = 11.0,
            phosphorusMg = 135.0,
            waterG = 123.1,
            alcoholG = 2.0,
            ashG = 2.0,
            ironMg = 2.4,
            zincMg = null,
            seleniumMcg = null,
            copperMg = null,
            manganeseMg = null,
            vitaminB1Mg = 0.2,
            vitaminB2Mg = 0.1,
            vitaminB3Mg = null,
            vitaminB5Mg = null,
            vitaminB6Mg = null,
            vitaminB12Mcg = null,
            vitaminAMcg = null,
            vitaminDMcg = null,
            vitaminEMg = null,
            vitaminKMcg = null,
            vitaminCMg = null,
            folateTotalMcg = null,
            folateFoodMcg = null,
            folicAcidMcg = 0.2,
            caffeineMg = 0.01,
            cholineMg = 0.012,
            luteinZeaxanthinMcg = null,
            epaMg = null,
            dhaMg = null
        ),
        images = emptyList(),
        portions = listOf(
            Portion(
                measureUnit = "ml",
                gramWeight = 200.0,
                description = "1 cup",
                amount = 2.0
            )
        ),
        nutriscoreSummary = NutriscoreSummary(
            grade = "A",
            score = -2
        ),
        nutriscoreComponents = listOf(
            NutriscoreComponent(
                componentId = "milk",
                isNegative = false,
                points = 25,
                maxPoints = 40,
                unit = "g",
                value = 120.0
            ),
            NutriscoreComponent(
                componentId = "saturated_fat",
                isNegative = true,
                points = 10,
                maxPoints = 10,
                unit = "g",
                value = 10.6
            )
        ),
        sourceMetadata = SourceMetadata(
            dataType = "Open Food Facts",
            publicationDate = 1602617601,
            externalNumericId = null,
            lastModified = 1769783618,
            lastEditor = "axeonsoft-off"
        ),
        isFavorite = false,
        isCached = true,
        packageInfo = ProductPackageInfo(
            quantity = 400.0,
            unit = "ml",
            label = "400.0 ml"
        )
    )
}