package com.saliery.nutrilog.app.data.local.room.converters

import androidx.room.TypeConverter
import com.saliery.nutrilog.app.domain.model.product.NutritionBasis

class NutritionBasisConverter {

    @TypeConverter
    fun toString(value: NutritionBasis): String = value.name

    @TypeConverter
    fun fromString(value: String): NutritionBasis =
        value.let { NutritionBasis.valueOf(it) }
}