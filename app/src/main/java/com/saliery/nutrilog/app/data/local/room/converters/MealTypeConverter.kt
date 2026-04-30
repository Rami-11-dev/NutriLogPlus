package com.saliery.nutrilog.app.data.local.room.converters

import androidx.room.TypeConverter
import com.saliery.nutrilog.app.domain.model.entries.LocalMealType

class MealTypeConverter {

    @TypeConverter
    fun fromLocalMealTypeToString(value: LocalMealType?): String? =
        value?.name

    @TypeConverter
    fun toLocalMealTypeFromString(value: String?): LocalMealType? =
        value?.let { LocalMealType.valueOf(it) }
}