package com.saliery.nutrilog.app.data.local.room.entity.user

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(tableName = "weight_entry")
data class WeightEntryEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val weightKg: Double,
    val dateTime: LocalDateTime
)
