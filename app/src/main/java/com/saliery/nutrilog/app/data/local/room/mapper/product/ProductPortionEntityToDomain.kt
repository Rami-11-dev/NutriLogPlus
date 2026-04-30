package com.saliery.nutrilog.app.data.local.room.mapper.product

import com.saliery.nutrilog.app.data.local.room.entity.product.ProductPortionEntity
import com.saliery.nutrilog.app.domain.model.product.Portion

fun ProductPortionEntity.toDomain(): Portion {
    return Portion(
        measureUnit = measureUnit,
        gramWeight = gramWeight,
        description = description,
        amount = amount
    )
}