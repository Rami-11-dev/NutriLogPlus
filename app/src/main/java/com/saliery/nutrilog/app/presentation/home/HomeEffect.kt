package com.saliery.nutrilog.app.presentation.home

sealed interface HomeEffect {

    // navigation
    data class NavigateToProductSearch(val query: String) : HomeEffect
    data object NavigateToMealEntry : HomeEffect
    data object NavigateToRecipes : HomeEffect
    data object NavigateToCamera : HomeEffect

    // feedback
    data class ShowMessage(val message: String, val type: MessageType = MessageType.INFO) : HomeEffect
}

enum class MessageType {
    SUCCESS, ERROR, INFO, WARNING
}