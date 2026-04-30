package com.saliery.nutrilog.app.data.local.room.entity.entries

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.saliery.nutrilog.app.data.local.room.entity.product.ProductEntity

@Entity(
    tableName = "meal_item_ingredient",
    foreignKeys = [
        ForeignKey(
            entity = MealItemEntity::class,
            parentColumns = ["id"],
            childColumns = ["mealItemId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = ProductEntity::class,
            parentColumns = ["id"],
            childColumns = ["productId"]
        )
    ],
    indices = [
        Index(value = ["mealItemId"]),
        Index(value = ["productId"])
    ]
)
data class MealItemIngredientEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val mealItemId: Long,
    val productId: String,
    val amountGrams: Double
)
