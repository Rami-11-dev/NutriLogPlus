package com.saliery.nutrilog.app.domain.model.product

data class NutritionFacts(
    val basis: NutritionBasis = NutritionBasis.PER_100G,

    // --- Energy ---
    val kcal: Double?,
    val kJ: Double?,

    // --- Common macros ---
    val protein: Double?,
    val totalFat: Double?,
    val carbohydrates: Double?,

    // --- Carbs detail ---
    val sugars: Double?,
    val addedSugars: Double?,
    val fiber: Double?,
    val starch: Double?,

    // --- Fats detail ---
    val saturatedFat: Double?,
    val monounsaturatedFat: Double?,
    val polyunsaturatedFat: Double?,
    val transFat: Double?,
    val cholesterolMg: Double?,

    // --- Salt / sodium ---
    val sodiumMg: Double?,
    val saltG: Double?
) {
    fun computedSaltFromSodium(): Double? = sodiumMg?.let { it / 1000.0 * 2.5 }
}