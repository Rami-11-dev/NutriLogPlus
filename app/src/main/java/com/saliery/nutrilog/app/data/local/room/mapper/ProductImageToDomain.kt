package com.saliery.nutrilog.app.data.local.room.mapper

import com.saliery.nutrilog.app.data.local.room.entity.product.ProductImageEntity
import com.saliery.nutrilog.app.domain.model.product.FoodImageModel
import com.saliery.nutrilog.app.domain.model.product.ImageType

fun ProductImageEntity.toDomain(): FoodImageModel {

    return FoodImageModel(
        imageType = imageType,
        displayUrl = displayUrl,
        smallUrl = smallUrl,
        thumbUrl = thumbUrl,
        language = language,
        isThumbCached = thumbCached,
        thumbCacheKey = thumbCacheKey
    )
}