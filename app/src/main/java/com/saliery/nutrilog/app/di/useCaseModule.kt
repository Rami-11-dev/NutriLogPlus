package com.saliery.nutrilog.app.di

import com.saliery.nutrilog.app.domain.repository.UserRepository
import com.saliery.nutrilog.app.domain.repository.WeightRepository
import com.saliery.nutrilog.app.domain.usecase.CalculateBmiUseCase
import com.saliery.nutrilog.app.domain.usecase.CalculateBmrUseCase
import com.saliery.nutrilog.app.domain.usecase.CalculateDailyCaloriesUseCase
import com.saliery.nutrilog.app.domain.usecase.CalculateDailySummaryUseCase
import com.saliery.nutrilog.app.domain.usecase.CalculateDailyWaterUseCase
import com.saliery.nutrilog.app.domain.usecase.CalculateMacroTargets
import com.saliery.nutrilog.app.domain.usecase.CalculateMealItemNutritionUseCase
import com.saliery.nutrilog.app.domain.usecase.CalculateRecipeDraftSummaryUseCase
import com.saliery.nutrilog.app.domain.usecase.EvaluateCalorieBalanceUseCase
import com.saliery.nutrilog.app.domain.usecase.SaveUserUseCase
import com.saliery.nutrilog.app.domain.usecase.SaveWeightUseCase
import org.koin.dsl.module

val useCaseModule = module {

    single {
        SaveWeightUseCase(
            userRepository = get<UserRepository>(),
            weightRepository = get<WeightRepository>()
        )
    }

    single {
        CalculateDailySummaryUseCase(
            calculateMealItemNutrition = get<CalculateMealItemNutritionUseCase>()
        )
    }

    single {
        CalculateBmiUseCase()
    }

    single {
        CalculateBmrUseCase()
    }

    single {
        CalculateDailyCaloriesUseCase(
            calculateBmr = get()
        )
    }

    single {
        CalculateDailyWaterUseCase()
    }

    single {
        CalculateMacroTargets()
    }

    single {
        CalculateMealItemNutritionUseCase()
    }

    single {
        CalculateRecipeDraftSummaryUseCase()
    }

    single {
        EvaluateCalorieBalanceUseCase()
    }

    single {
        SaveUserUseCase(
            userRepository = get()
        )
    }
}