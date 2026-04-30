package com.saliery.nutrilog.app.di

import com.saliery.nutrilog.app.presentation.cameraScreen.CameraViewModel
import com.saliery.nutrilog.app.presentation.home.HomeViewModel
import com.saliery.nutrilog.app.presentation.mealEntryJournal.JournalViewModel
import com.saliery.nutrilog.app.presentation.mealEntryScreen.MealEntryViewModel
import com.saliery.nutrilog.app.presentation.onboarding.OnboardingViewModel
import com.saliery.nutrilog.app.presentation.product.ProductViewModel
import com.saliery.nutrilog.app.presentation.productList.ProductListViewModel
import com.saliery.nutrilog.app.presentation.recipe.RecipeViewModel
import com.saliery.nutrilog.app.presentation.recipeList.RecipeListViewModel
import com.saliery.nutrilog.app.presentation.splash.SplashViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel {
        ProductViewModel(
            reducer = get(),
            productRepository = get()
        )
    }

    viewModel {
        ProductListViewModel(
            reducer = get(),
            productRepository = get(),
        )
    }

    viewModel {
        RecipeViewModel(
            reducer = get(),
            recipeRepository = get(),
            calculateRecipeDraftSummary = get()
        )
    }

    viewModel {
        RecipeListViewModel(
            reducer = get(),
            recipeRepository = get(),
        )
    }

    viewModel {
        MealEntryViewModel(
            reducer = get(),
            mealEntryRepository = get(),
            recipeRepository = get(),
            userRepository = get(),
            calculateMacroTargets = get(),
            calculateDailyCaloriesUseCase = get()
        )
    }

    viewModel {
        OnboardingViewModel(
            reducer = get(),
            saveUserUseCase = get(),
            calculateBmrUseCase = get(),
            calculateDailyCaloriesUseCase = get(),
            calculateMacroTargets = get(),
            calculateDailyWaterUseCase = get(),
            calculateBmiUseCase = get()
        )
    }

    viewModel {
        JournalViewModel(
            repository = get(),
            userRepository = get(),
            calculateMacroTargets = get(),
            calculateDailyCaloriesUseCase = get()
        )
    }

    viewModel {
        HomeViewModel(
            reducer = get(),
            userRepository = get(),
            waterRepository = get(),
            weightRepository = get(),
            mealRepository = get(),
            calculateBmiUseCase = get(),
            calculateMacroTargets = get(),
            calculateDailyWaterUseCase = get(),
            calculateDailyCaloriesUseCase = get(),
            calculateDailySummaryUseCase = get(),
            saveWeightUseCase = get()
        )
    }

    viewModel {
        CameraViewModel()
    }

    viewModel {
        SplashViewModel(
            userRepository = get()
        )
    }
}