package com.saliery.nutrilog.app.data.local.room.entity.recipe

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(
    tableName = "recipe",
    indices = [Index(value = ["name"])]
)
data class RecipeEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val name: String,
    val servingsYield: Double,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
)