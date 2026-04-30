package com.saliery.nutrilog.app.data.local.room.entity.entries

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.saliery.nutrilog.app.data.local.room.converters.MealTypeConverter
import com.saliery.nutrilog.app.domain.model.entries.LocalMealType
import java.time.LocalDateTime

@Entity(tableName = "meal_entry")
@TypeConverters(MealTypeConverter::class)
data class MealEntryEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val mealType: LocalMealType,
    val dateTime: LocalDateTime
)
