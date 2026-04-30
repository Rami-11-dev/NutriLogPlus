package com.saliery.nutrilog.app.data.remote.mapper

import com.saliery.nutrilog.app.data.local.room.entity.product.ProductEntityLiteProjection
import com.saliery.nutrilog.app.data.remote.dto.SearchResponseDto
import com.saliery.nutrilog.app.domain.model.product.ProductDataSourceEnum
import timber.log.Timber


fun SearchResponseDto.toEntities(): List<ProductEntityLiteProjection> {
    Timber.d("Search response DTO mapper. List size: ${products?.size ?: 0}")

    if (count == 0) return emptyList()

    return products
        ?.mapNotNull { product ->
            val code = product.code?.takeIf { it.isNotBlank() } ?: return@mapNotNull null
            val hasNameOrBrand =
                !product.productName.isNullOrBlank() || !product.brands.isNullOrBlank()

            if (!hasNameOrBrand) return@mapNotNull null

            ProductEntityLiteProjection(
                id = code,
                source = ProductDataSourceEnum.OPEN_FOOD_FACTS,
                productName = product.productName,
                productBrand = product.brands,
                nutritionGrade = product.nutritionGrades,
                foodCategory = product.foodGroup,
                kcal = product.nutriments?.energyKcal,
                carbohydrates = product.nutriments?.carbohydrates100g,
                totalFat = product.nutriments?.fat100g,
                protein = product.nutriments?.proteins100g,
                displayImageUrl = product.selectedImages?.front?.thumb?.values?.firstOrNull() ?:
                    product.selectedImages?.front?.small?.values?.firstOrNull() ?:
                        product.selectedImages?.front?.display?.values?.firstOrNull()
            )
        }
        ?: emptyList()
}