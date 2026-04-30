package com.saliery.nutrilog.app.data.local.room.mapper.weightEntry

import com.saliery.nutrilog.app.data.local.room.entity.user.WeightEntryEntity
import com.saliery.nutrilog.app.domain.model.user.WeightEntryModel

fun WeightEntryModel.toEntity(): WeightEntryEntity {
    return WeightEntryEntity(
        id = id,
        weightKg = weightKg,
        dateTime = dateTime
    )
}