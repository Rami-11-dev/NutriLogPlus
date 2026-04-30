package com.saliery.nutrilog.app.presentation.recipe

sealed interface RecipeEffect {
    data object OpenIngredientPicker : RecipeEffect
    data object NavigateBack : RecipeEffect
    data class ShowMessage(val message: String) : RecipeEffect
}