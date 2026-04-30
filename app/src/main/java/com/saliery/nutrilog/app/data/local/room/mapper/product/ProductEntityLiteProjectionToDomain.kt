package com.saliery.nutrilog.app.data.local.room.mapper.product

import com.saliery.nutrilog.app.data.local.room.entity.product.ProductEntityLiteProjection
import com.saliery.nutrilog.app.domain.model.product.FoodProductModelLite

fun ProductEntityLiteProjection.toDomain(): FoodProductModelLite {

    return FoodProductModelLite(
        id = id,
        productName = productName,
        productBrand = productBrand,
        nutritionGrade = nutritionGrade,
        foodGroup = foodCategory,
        caloriesPer100g = kcal,
        proteinsPer100g = protein,
        fatsPer100g = totalFat,
        carbsPer100g = carbohydrates,
        displayImage = displayImageUrl,
        source = source
    )
}