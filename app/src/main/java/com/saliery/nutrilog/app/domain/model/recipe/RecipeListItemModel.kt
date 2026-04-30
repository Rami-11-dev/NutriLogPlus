package com.saliery.nutrilog.app.domain.model.recipe

import java.time.LocalDateTime

data class RecipeListItemModel(
    val id: Long,
    val name: String,
    val servingsYield: Double,
    val ingredientCount: Int,
    val totalCalories: Double,
    val totalProteins: Double,
    val totalFats: Double,
    val totalCarbs: Double,
    val updatedAt: LocalDateTime
)
