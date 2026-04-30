package com.saliery.nutrilog.app.data.remote.mapper

import com.saliery.nutrilog.app.data.local.room.entity.product.ProductNutriscoreDataEntity
import com.saliery.nutrilog.app.data.remote.dto.BarcodeResponseDto
import com.saliery.nutrilog.app.data.remote.dto.GetNutriscoreDataResponse
import com.saliery.nutrilog.app.data.remote.dto.ProductDto
import com.saliery.nutrilog.app.data.remote.dto.SingleComponentDto

fun BarcodeResponseDto.toNutriscoreDataEntities(): List<ProductNutriscoreDataEntity> {

    val barcode = code.takeIf { it.isNotBlank() } ?: return emptyList()

    val entities = mutableListOf<ProductNutriscoreDataEntity>()
    val components = product?.nutriscore_data?.components ?: return emptyList()

    if (components.negative.isNullOrEmpty() && components.positive.isNullOrEmpty()) {
        return emptyList()
    }

    components.negative?.forEach { component ->
        if (!component.id.isNullOrBlank()) {
            entities.add(toEntity(component, isNegative = true, barcode))
        }
    }

    components.positive?.forEach { component ->
        if (!component.id.isNullOrBlank()) {
            entities.add(toEntity(component, isNegative = false, barcode))
        }
    }

    return entities
}

private fun toEntity(
    component: SingleComponentDto,
    isNegative: Boolean,
    barcode: String,
    ): ProductNutriscoreDataEntity {

    return ProductNutriscoreDataEntity(
        productId = barcode,
        componentId = component.id!!,
        points = component.points,
        pointsMax = component.points_max,
        unit = component.unit ?: "Unknown",
        value = component.value.asDouble(),
        isNegative = isNegative
    )
}

private fun Any?.asDouble(): Double = when (this) {
    is Int -> toDouble()
    is Double -> this
    else -> 0.0
}