package com.saliery.nutrilog.app.data.local.room.converters

import androidx.room.TypeConverter
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class LocalDateTimeConverter {

    private val formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME

    @TypeConverter
    fun fromLocalDateTimeToString(value: LocalDateTime?): String? {
        return value?.format(formatter)
    }

    @TypeConverter
    fun toLocalDateTimeFromString(value: String?): LocalDateTime? {
        return value?.let {
            LocalDateTime.parse(it, formatter)
        }
    }
}