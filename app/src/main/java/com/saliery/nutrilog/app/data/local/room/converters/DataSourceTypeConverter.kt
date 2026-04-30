package com.saliery.nutrilog.app.data.local.room.converters

import androidx.room.TypeConverter
import com.saliery.nutrilog.app.domain.model.product.ProductDataSourceEnum

class DataSourceTypeConverter {

    @TypeConverter
    fun toString(value: ProductDataSourceEnum): String = value.name

    @TypeConverter
    fun fromString(value: String): ProductDataSourceEnum  =
        value.let { ProductDataSourceEnum.valueOf(it) }
}