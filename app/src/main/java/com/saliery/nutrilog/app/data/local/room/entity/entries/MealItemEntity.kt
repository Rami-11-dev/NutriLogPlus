package com.saliery.nutrilog.app.data.local.room.entity.entries

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.saliery.nutrilog.app.domain.model.business.CookingMethod
import com.saliery.nutrilog.app.domain.model.entries.LocalMealItemType

@Entity(
    tableName = "meal_item",
    foreignKeys = [
        ForeignKey(
            entity = MealEntryEntity::class,
            parentColumns = ["id"],
            childColumns = ["mealEntryId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["mealEntryId"]),
        Index(value = ["recipeId"])
    ]
)
data class MealItemEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val mealEntryId: Long,
    val recipeId: Long?,
    val itemType: LocalMealItemType,
    val cookingMethod: CookingMethod?,
    val consumedGrams: Double?,
    val consumedServings: Double?
)
