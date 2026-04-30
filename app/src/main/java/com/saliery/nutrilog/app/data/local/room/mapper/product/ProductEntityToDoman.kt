package com.saliery.nutrilog.app.data.local.room.mapper.product

import com.saliery.nutrilog.app.data.local.room.entity.product.ProductEntity
import com.saliery.nutrilog.app.data.local.room.entity.product.ProductImageEntity
import com.saliery.nutrilog.app.data.local.room.entity.product.ProductMicronutrientEntity
import com.saliery.nutrilog.app.data.local.room.entity.product.ProductNutriscoreDataEntity
import com.saliery.nutrilog.app.data.local.room.entity.product.ProductPortionEntity
import com.saliery.nutrilog.app.data.local.room.mapper.toDomain
import com.saliery.nutrilog.app.domain.model.product.FoodProductModel
import com.saliery.nutrilog.app.domain.model.product.NutritionFacts
import com.saliery.nutrilog.app.domain.model.product.ProductIngredientItem
import com.saliery.nutrilog.app.domain.model.product.ProductPackageInfo
import com.saliery.nutrilog.app.domain.model.product.SourceMetadata
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

fun ProductEntity.toDomain(
    images: List<ProductImageEntity>,
    nutriscore: List<ProductNutriscoreDataEntity>,
    portions: List<ProductPortionEntity>,
    micronutrients: ProductMicronutrientEntity?
): FoodProductModel {
    return FoodProductModel(
        id = id,
        source = source,

        name = productName,
        brand = productBrand,
        foodCategory = foodCategory,

        nutritionGrade = nutritionGrade,
        novaGroup = novaGroup,

        ingredients = parseIngredientsWithPercent(ingredientsText),
        allergens = allergensRaw,
        additives = additivesRaw,
        traces = tracesRaw,

        nutritionFacts = NutritionFacts(
            basis = nutritionBasis,

            kcal = kcal,
            kJ = kJ,

            protein = protein,
            totalFat = totalFat,
            carbohydrates = carbohydrates,

            sugars = sugars,
            addedSugars = addedSugars,
            fiber = fiber,
            starch = starch,

            saturatedFat = saturatedFat,
            monounsaturatedFat = monounsaturatedFat,
            polyunsaturatedFat = polyunsaturatedFat,
            transFat = transFat,
            cholesterolMg = cholesterolMg,

            sodiumMg = sodiumMg,
            saltG = saltG
        ),

        micronutrients = micronutrients?.toDomain(),

        images = images.map { it.toDomain() },
        portions = portions.map { it.toDomain() },

        nutriscoreSummary = null,
        nutriscoreComponents = nutriscore.mapNotNull { it.toDomain() },

        sourceMetadata = SourceMetadata(
            dataType = sourceDataType,
            publicationDate = sourcePublicationDate,
            externalNumericId = sourceExternalNumericId,
            lastModified = sourceLastModified,
            lastEditor = sourceLastEditor
        ),

        isFavorite = isFavorite,
        isCached = isCached,
        packageInfo = ProductPackageInfo(
            quantity = packageQuantity,
            unit = packageQuantityUnit,
            label = packageQuantityText
        ),
    )
}

private fun parseIngredientsWithPercent(ingredientsText: String?): List<ProductIngredientItem> {
    if (ingredientsText.isNullOrBlank()) return emptyList()

    return try {
        Gson().fromJson<List<Map<String, Any?>>>(ingredientsText, object : TypeToken<List<Map<String, Any?>>>() {}.type)
            ?.map { map ->
                ProductIngredientItem(
                    ingredientName = map["name"] as String,
                    percentEstimate = (map["percent"] as? Double)
                )
            } ?: emptyList()
    } catch (e: Exception) {
        emptyList()
    }
}