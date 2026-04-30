package com.saliery.nutrilog.app.data.local.room.mapper.product

import com.saliery.nutrilog.app.domain.model.product.FoodImageModel
import com.saliery.nutrilog.app.domain.model.product.FoodProductModel
import com.saliery.nutrilog.app.domain.model.product.FoodProductModelLite
import com.saliery.nutrilog.app.domain.model.product.ImageType

fun FoodProductModel.toModelLite(): FoodProductModelLite {

    return FoodProductModelLite(
        id = id,
        source = source,
        productName = name,
        productBrand = brand,
        nutritionGrade = nutritionGrade,
        foodGroup = foodCategory,
        caloriesPer100g = nutritionFacts.kcal,
        proteinsPer100g = nutritionFacts.protein,
        fatsPer100g = nutritionFacts.totalFat,
        carbsPer100g = nutritionFacts.carbohydrates,
        displayImage = images.getFrontImageUrl()
    )
}

fun List<FoodImageModel>.getFrontImageUrl(): String? {
    val frontImage = this.find { it.imageType == ImageType.FRONT } ?: return null

    return frontImage.displayUrl
        ?: frontImage.smallUrl
        ?: frontImage.thumbUrl
}