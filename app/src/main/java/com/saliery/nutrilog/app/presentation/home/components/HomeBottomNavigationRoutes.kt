package com.saliery.nutrilog.app.presentation.home.components

sealed interface HomeBottomNavigationRoutes {
    val route: String
}

data object HomeDestination : HomeBottomNavigationRoutes {
    override val route: String = "home"
}

data object RecipeListDestination : HomeBottomNavigationRoutes {
    override val route: String = "recipe_list"
}

data object MealJournalDestination : HomeBottomNavigationRoutes {
    override val route: String = "meal_journal"
}