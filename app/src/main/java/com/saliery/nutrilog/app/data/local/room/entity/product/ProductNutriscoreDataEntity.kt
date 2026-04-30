package com.saliery.nutrilog.app.data.local.room.entity.product

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "product_nutriscore",
    foreignKeys = [
        ForeignKey(
            entity = ProductEntity::class,
            parentColumns = ["id"],
            childColumns = ["productId"],
            onDelete = ForeignKey.Companion.CASCADE
        )
    ],
    indices = [Index("productId")]
)
data class ProductNutriscoreDataEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val productId: String,
    val componentId: String,
    val points: Int,
    val pointsMax: Int,
    val unit: String,
    val value: Double?,
    val isNegative: Boolean
)