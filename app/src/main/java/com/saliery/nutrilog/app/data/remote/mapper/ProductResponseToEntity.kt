package com.saliery.nutrilog.app.data.remote.mapper

import com.google.gson.Gson
import com.saliery.nutrilog.app.data.local.room.entity.product.ProductEntity
import com.saliery.nutrilog.app.data.remote.dto.BarcodeResponseDto
import com.saliery.nutrilog.app.domain.model.product.NutritionBasis
import com.saliery.nutrilog.app.domain.model.product.ProductDataSourceEnum

fun BarcodeResponseDto.toEntity(): ProductEntity? {
    val dto = product ?: return null
    val id = dto.code?.takeIf { it.isNotBlank() } ?: code.takeIf { it.isNotBlank() } ?: return null

    return ProductEntity(
        id = id,
        source = ProductDataSourceEnum.OPEN_FOOD_FACTS,

        productName = dto.product_name,
        productBrand = dto.brands,
        foodCategory = dto.food_groups,

        nutritionGrade = dto.nutritionGrades,
        novaGroup = dto.novaGroup,

        ingredientsText = dto.ingredients
            ?.mapNotNull { ingredient ->
                ingredient.text?.trim()?.takeIf { it.isNotBlank() } ?: return@mapNotNull null
                mapOf(
                    "name" to ingredient.text.trim(),
                    "percent" to ingredient.percentEstimate
                )
            }
            ?.let { Gson().toJson(it) },

        allergensRaw = parseOffTags(dto.allergens),
        additivesRaw = dto.additivesTags
            ?.mapNotNull { tag ->
                tag.substringAfter(":", missingDelimiterValue = tag)
                    .trim()
                    .takeIf { it.isNotBlank() }
            }
            ?: emptyList(),
        tracesRaw = parseOffTags(dto.traces),

        nutritionBasis = NutritionBasis.PER_100G,

        kcal = dto.nutriments?.energyKcal100g,
        kJ = dto.nutriments?.energyKj100g,

        protein = dto.nutriments?.proteins100g,
        totalFat = dto.nutriments?.fat100g,
        carbohydrates = dto.nutriments?.carbohydrates100g,

        sugars = dto.nutriments?.sugars100g,
        addedSugars = null,
        fiber = dto.nutriments?.fiber100g,
        starch = null,

        saturatedFat = dto.nutriments?.saturatedFat100g,
        monounsaturatedFat = null,
        polyunsaturatedFat = null,
        transFat = null,
        cholesterolMg = null,

        sodiumMg = dto.nutriments?.sodium100g,
        saltG = dto.nutriments?.salt100g,

        sourceDataType = ProductDataSourceEnum.OPEN_FOOD_FACTS.name.replace('_', ' '),
        sourcePublicationDate = dto.createdAt,
        sourceExternalNumericId = null,
        sourceLastEditor = dto.lastEditor,
        sourceLastModified = dto.lastUpdatedAt,
        packageQuantity = product.productQuantity,
        packageQuantityUnit = product.productQuantityUnit,
        packageQuantityText = product.quantity
    )
}

private fun parseOffTags(raw: String?): List<String> {
    return raw
        ?.split(",")
        ?.mapNotNull { tag ->
            tag.substringAfter(":", missingDelimiterValue = tag)
                .trim()
                .takeIf { it.isNotBlank() }
        }
        ?: emptyList()
}