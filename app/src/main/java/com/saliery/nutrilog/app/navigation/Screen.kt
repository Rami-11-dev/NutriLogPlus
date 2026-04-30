package com.saliery.nutrilog.app.navigation

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Onboarding : Screen("onboarding")
    object Home : Screen("home")
    object Camera : Screen("camera")
    object RecipeList : Screen("recipe_list")
    object Journal : Screen("journal")

    object ProductList : Screen("product_list?query={query}") {
        fun createRoute(query: String = "") = "product_list?query=$query"
    }

    object Product : Screen("product/{productId}") {
        fun createRoute(productId: String) = "product/$productId"
    }

    object MealEntry : Screen("meal_entry?entryId={entryId}") {
        fun createRoute(entryId: Long? = null) =
            if (entryId != null) "meal_entry?entryId=$entryId" else "meal_entry"
    }

    object Recipe : Screen("recipe?recipeId={recipeId}") {
        fun createRoute(recipeId: Long? = null) =
            if (recipeId != null) "recipe?recipeId=$recipeId" else "recipe"
    }
}