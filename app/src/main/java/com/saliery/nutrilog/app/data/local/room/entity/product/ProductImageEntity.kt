package com.saliery.nutrilog.app.data.local.room.entity.product

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.saliery.nutrilog.app.domain.model.product.ImageType

@Entity(
    tableName = "product_image",
    foreignKeys = [
        ForeignKey(
            entity = ProductEntity::class,
            parentColumns = ["id"],
            childColumns = ["productId"],
            onDelete = ForeignKey.Companion.CASCADE
        )
    ],
    indices = [
        Index("productId"),
        Index(value = ["productId", "imageType"])
    ]
)
data class ProductImageEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val productId: String,
    val imageType: ImageType,
    val displayUrl: String?,
    val smallUrl: String?,
    val thumbUrl: String?,
    val language: String?,
    val thumbCacheKey: String? = null,
    val thumbCached: Boolean = false
)