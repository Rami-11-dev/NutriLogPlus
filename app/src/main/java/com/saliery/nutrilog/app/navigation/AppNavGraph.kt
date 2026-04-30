package com.saliery.nutrilog.app.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.saliery.nutrilog.app.domain.model.product.FoodProductModelLite
import com.saliery.nutrilog.app.presentation.cameraScreen.CameraRoute
import com.saliery.nutrilog.app.presentation.home.HomeScreenRoute
import com.saliery.nutrilog.app.presentation.mealEntryJournal.JournalRoute
import com.saliery.nutrilog.app.presentation.mealEntryScreen.MealEntryRoute
import com.saliery.nutrilog.app.presentation.mealEntryScreen.RecipeDraft
import com.saliery.nutrilog.app.presentation.onboarding.OnboardingRoute
import com.saliery.nutrilog.app.presentation.product.ProductRoute
import com.saliery.nutrilog.app.presentation.productList.ProductListRoute
import com.saliery.nutrilog.app.presentation.recipe.RecipeRoute
import com.saliery.nutrilog.app.presentation.recipeList.RecipeListRoute
import com.saliery.nutrilog.app.presentation.splash.SplashRoute

@Composable
fun AppNavGraph(
    navController: NavHostController,
    startDestination: String = Screen.Splash.route
) {

    var pickedProduct: FoodProductModelLite? by rememberSaveable {
        mutableStateOf(null)
    }
    var recipeDraft: RecipeDraft? by rememberSaveable {
        mutableStateOf(null)
    }

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {

        composable(Screen.Splash.route) {
            SplashRoute(
                onNavigateToOnboarding = {
                    navController.navigate(Screen.Onboarding.route) {
                        popUpTo(Screen.Splash.route) { inclusive = true }
                    }
                },
                onNavigateToHome = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Splash.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.Onboarding.route) {
            OnboardingRoute(
                onNavigateToHome = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Onboarding.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.Home.route) {
            HomeScreenRoute(
                onNavigateToHome = {},
                onNavigateToRecipeList = {
                    navController.navigate(Screen.RecipeList.route)
                },
                onNavigateToMealEntry = {
                    navController.navigate(Screen.MealEntry.route)
                },
                onNavigateToCamera = {
                    navController.navigate(Screen.Camera.route)
                },
                onNavigateToProductList = { query ->
                    navController.navigate(Screen.ProductList.createRoute(query = query))
                }
            )
        }

        composable(
            route = Screen.ProductList.route,
            arguments = listOf(navArgument("query") { defaultValue = "" })
        ) { backStackEntry ->
            val query = backStackEntry.arguments?.getString("query") ?: ""

            ProductListRoute(
                onBackClick = { navController.popBackStack() },
                query = query,
                onProductClick = { productId ->
                    navController.navigate(Screen.Product.createRoute(productId = productId))
                }
            )
        }

        composable(Screen.Product.route) { backStackEntry ->
            val productId = backStackEntry.arguments?.getString("productId") ?: ""

            ProductRoute(
                productId = productId,
                onBackClick = { navController.popBackStack() },
                onOpenAddToMeal = { productPreview ->
                    pickedProduct = productPreview
                    navController.popBackStack(Screen.MealEntry.route, inclusive = false)
                }
            )
        }

        composable(Screen.MealEntry.route) { backStackEntry ->
            val entryId = backStackEntry.arguments?.getString("entryId")?.toLongOrNull()

            MealEntryRoute(
                entryId = entryId,
                pickedProduct = pickedProduct,
                pickedRecipe = null,
                onPickedProductConsumed = { pickedProduct = null },
                onPickedRecipeConsumed = {},
                onBackClick = {
                    navController.popBackStack()
                },
                onOpenProductPicker = {
                    navController.navigate(Screen.ProductList.createRoute())
                },
                onOpenRecipePicker = {
                    navController.navigate(Screen.RecipeList.route)
                },
                onOpenSaveAsRecipe = {

                },
                onOpenRecipeDraft = { draft ->
                    recipeDraft = draft
                    navController.navigate(Screen.Recipe.route)
                }
            )
        }

        composable(Screen.Journal.route) {
            JournalRoute(
                onNavigateToEntry ={ entryId ->
                    navController.navigate(Screen.MealEntry.createRoute(entryId))
                }
            )
        }

        composable(
            Screen.Recipe.route,
            arguments = listOf(navArgument("recipeId") { nullable = false } )
        ) { backStackEntry ->
            val recipeId = backStackEntry.arguments?.getString("recipeId")?.toLongOrNull()

            RecipeRoute(
                recipeId = recipeId,
                recipeDraft = recipeDraft,
                onBackClick = {
                    navController.popBackStack()
                },
                onOpenIngredientPicker = {
                    navController.navigate(Screen.ProductList.route)
                },
                pickedIngredient = pickedProduct,
                onIngredientConsumed = { pickedProduct = null }
            )
        }

        composable(Screen.RecipeList.route) {

            RecipeListRoute(
                onBackClick = {
                    navController.popBackStack()
                },
                onRecipeClick = { recipeId ->
                    navController.navigate(Screen.Recipe.createRoute(recipeId))
                },
            )
        }

        composable(Screen.Camera.route) {

            CameraRoute(
                onNavigateToProduct = { productId ->
                    navController.navigate(Screen.Product.createRoute(productId))
                },
                onBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}