package com.saliery.nutrilog.app.data.local.room.converters

import androidx.room.TypeConverter
import com.saliery.nutrilog.app.domain.model.product.ImageType

class ImageTypeConverter {

    @TypeConverter
    fun from(value: ImageType): String =
        value.name

    @TypeConverter
    fun to(value: String): ImageType =
        value.let { ImageType.valueOf(it) }
}