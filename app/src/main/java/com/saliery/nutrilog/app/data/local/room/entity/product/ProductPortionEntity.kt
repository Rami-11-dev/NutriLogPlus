package com.saliery.nutrilog.app.data.local.room.entity.product

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "product_portion",
    foreignKeys = [
        ForeignKey(
            entity = ProductEntity::class,
            parentColumns = ["id"],
            childColumns = ["productId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["productId"])
    ]
)
data class ProductPortionEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,

    val productId: String,
    val measureUnit: String,
    val gramWeight: Double,
    val description: String,
    val amount: Double
)
