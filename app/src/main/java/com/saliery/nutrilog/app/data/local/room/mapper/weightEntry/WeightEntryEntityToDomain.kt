package com.saliery.nutrilog.app.data.local.room.mapper.weightEntry

import com.saliery.nutrilog.app.data.local.room.entity.user.WeightEntryEntity
import com.saliery.nutrilog.app.domain.model.user.WeightEntryModel

fun WeightEntryEntity.toDomain(): WeightEntryModel {
    return WeightEntryModel(
        id = id,
        weightKg = weightKg,
        dateTime = dateTime
    )
}