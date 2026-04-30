package com.saliery.nutrilog.app.domain.model.recipe

import java.time.LocalDateTime

data class RecipeModel(
    val id: Long,
    val name: String,
    val ingredients: List<RecipeIngredientModel>,
    val servingsYield: Double,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
)
