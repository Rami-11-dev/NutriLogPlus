package com.saliery.nutrilog.app.presentation.home

import com.saliery.nutrilog.app.domain.model.user.User
import java.time.LocalDate

sealed interface HomeIntent {

    data object InitializeHome : HomeIntent
    data object RefreshHome : HomeIntent
    data class SelectedDate(val date: LocalDate) : HomeIntent

    // water
    data object AddGlassOfWaterClicked : HomeIntent
    data class AddCustomWaterClicked(val amountMl: Double) : HomeIntent

    // weight
    data class SaveWeightClicked(val weightKg: Double) : HomeIntent
    data class WeightTimeRangeChanged(val weightTimeRange: WeightTimeRange) : HomeIntent

    // product search
    data class SearchQueryChanged(val query: String) : HomeIntent
    data object SearchSubmitted : HomeIntent
    data object ClearSearchClicked : HomeIntent

    // navigation
    data object OpenMealEntryClicked : HomeIntent
    data object OpenRecipesClicked : HomeIntent
    data object OpenCameraClicked : HomeIntent

    data class OnHomeDataLoaded(
        val user: User?,
        val summary: HomeSummaryCardUiModel,
        val water: HomeWaterCardUiModel,
        val weight: HomeWeightCardUiModel
    ) : HomeIntent

    data class OnHomeDataError(val error: HomeError) : HomeIntent
    data class OnWaterAddError(val cause: Exception) : HomeIntent
    data class OnWeightSaveError(val cause: Exception) : HomeIntent
}