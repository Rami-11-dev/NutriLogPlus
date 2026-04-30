package com.saliery.nutrilog.app.domain.helper

import com.saliery.nutrilog.app.domain.model.product.FoodProductModel
import com.saliery.nutrilog.app.domain.model.product.ImageType

fun FoodProductModel.heroImageUrl(): String? {
    val frontImage = images.firstOrNull { it.imageType == ImageType.FRONT }
    val anyImage = images.firstOrNull()

    return frontImage?.displayUrl
        ?: frontImage?.smallUrl
        ?: frontImage?.thumbUrl
        ?: anyImage?.displayUrl
        ?: anyImage?.smallUrl
        ?: anyImage?.thumbUrl
}