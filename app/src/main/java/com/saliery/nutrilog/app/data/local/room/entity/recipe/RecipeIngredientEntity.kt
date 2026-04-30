package com.saliery.nutrilog.app.data.local.room.entity.recipe

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.saliery.nutrilog.app.data.local.room.converters.CookingMethodConverter
import com.saliery.nutrilog.app.data.local.room.entity.product.ProductEntity
import com.saliery.nutrilog.app.domain.model.business.CookingMethod

@Entity(
    tableName = "recipe_ingredient",
    foreignKeys = [
        ForeignKey(
            entity = RecipeEntity::class,
            parentColumns = ["id"],
            childColumns = ["recipeId"],
            onDelete = ForeignKey.Companion.CASCADE
        ),
        ForeignKey(
            entity = ProductEntity::class,
            parentColumns = ["id"],
            childColumns = ["productId"]
        )
    ],
    indices = [
        Index(value = ["recipeId"]),
        Index(value = ["productId"])
    ]
)
@TypeConverters(CookingMethodConverter::class)
data class RecipeIngredientEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val recipeId: Long,
    val productId: String,
    val cookingMethod: CookingMethod? = CookingMethod.RAW,
    val amountGrams: Double
)