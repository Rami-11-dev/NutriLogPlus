package com.saliery.nutrilog.app.data.local.room.converters

import androidx.room.TypeConverter
import kotlinx.serialization.json.Json

class ListStringConverter {

    private val json = Json

    @TypeConverter
    fun fromList(list: List<String>?): String {
        return json.encodeToString(list ?: emptyList())
    }

    @TypeConverter
    fun toList(string: String?): List<String> {
        return if (string.isNullOrEmpty()) {
            emptyList()
        } else {
            runCatching {
                json.decodeFromString<List<String>>(string)
            }.getOrElse { emptyList() }
        }
    }
}