package com.saliery.nutrilog.app.data.local.room.converters

import androidx.room.TypeConverter
import com.saliery.nutrilog.app.domain.model.business.CookingMethod

class CookingMethodConverter {

    @TypeConverter
    fun fromCookingMethodToString(value: CookingMethod?): String? =
        value?.name

    @TypeConverter
    fun toCookingMethodFromString(value: String?): CookingMethod? =
        value?.let { CookingMethod.valueOf(it) }
}