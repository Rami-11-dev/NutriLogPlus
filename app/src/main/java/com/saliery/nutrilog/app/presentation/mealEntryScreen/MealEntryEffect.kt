package com.saliery.nutrilog.app.presentation.mealEntryScreen

sealed interface MealEntryEffect {
    data object OpenProductPicker : MealEntryEffect
    data object OpenRecipePicker : MealEntryEffect
    data object OpenSaveAsRecipe : MealEntryEffect
    data object NavigateBack : MealEntryEffect

    data class ShowMessage(val message: String) : MealEntryEffect
    data class OpenRecipeWithDraft(val draft: RecipeDraft) : MealEntryEffect
}