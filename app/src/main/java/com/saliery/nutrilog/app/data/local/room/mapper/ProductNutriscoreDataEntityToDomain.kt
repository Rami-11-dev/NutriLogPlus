package com.saliery.nutrilog.app.data.local.room.mapper

import com.saliery.nutrilog.app.data.local.room.entity.product.ProductNutriscoreDataEntity
import com.saliery.nutrilog.app.domain.model.product.NutriscoreComponent

fun ProductNutriscoreDataEntity.toDomain(): NutriscoreComponent? {

    val id = componentId.takeIf { it.isNotEmpty() } ?: return null

    return NutriscoreComponent(
        componentId = id,
        isNegative = isNegative,
        points = points,
        maxPoints = pointsMax,
        unit = unit,
        value = value
    )
}