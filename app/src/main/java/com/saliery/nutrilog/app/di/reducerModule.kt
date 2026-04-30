package com.saliery.nutrilog.app.di

import com.saliery.nutrilog.app.presentation.home.HomeReducer
import com.saliery.nutrilog.app.presentation.mealEntryScreen.MealEntryReducer
import com.saliery.nutrilog.app.presentation.onboarding.OnboardingReducer
import com.saliery.nutrilog.app.presentation.product.ProductReducer
import com.saliery.nutrilog.app.presentation.productList.ProductListReducer
import com.saliery.nutrilog.app.presentation.recipe.RecipeReducer
import com.saliery.nutrilog.app.presentation.recipeList.RecipeListReducer
import org.koin.dsl.module

val reducerModule = module {
    single { ProductReducer() }
    single { ProductListReducer() }
    single { RecipeReducer() }
    single { RecipeListReducer() }
    single { MealEntryReducer() }
    single { OnboardingReducer() }
    single { HomeReducer() }
}