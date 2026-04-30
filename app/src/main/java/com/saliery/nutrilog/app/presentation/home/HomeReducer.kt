package com.saliery.nutrilog.app.presentation.home

import com.saliery.nutrilog.app.presentation.home.HomeError.AddingWaterFailed
import com.saliery.nutrilog.app.presentation.home.HomeError.SavingWeightFailed
import com.saliery.nutrilog.app.presentation.home.HomeState.LoadingState.Error

class HomeReducer {

    fun reduce(state: HomeState, intent: HomeIntent): HomeState {
        return when (intent) {
            // initial
            HomeIntent.InitializeHome -> state.copy(
                mainLoading = HomeState.LoadingState.Loading,
                error = null
            )

            HomeIntent.RefreshHome -> state.copy(
                mainLoading = HomeState.LoadingState.Loading,
                error = null
            )

            // water
            HomeIntent.AddGlassOfWaterClicked -> state.copy(
                waterLoading = HomeState.LoadingState.Loading
            )

            is HomeIntent.AddCustomWaterClicked -> state.copy(
                waterLoading = HomeState.LoadingState.Loading
            )

            is HomeIntent.OnWaterAddError -> state.copy(
                waterLoading = Error(intent.cause.message ?: "Unknown error"),
                error = AddingWaterFailed(intent.cause)
            )

            // weight
            is HomeIntent.SaveWeightClicked -> state.copy(
                weightLoading = HomeState.LoadingState.Loading
            )

            is HomeIntent.WeightTimeRangeChanged -> state.copy(
                weightLoading = HomeState.LoadingState.Loading
            )

            is HomeIntent.OnWeightSaveError -> state.copy(
                weightLoading = Error(intent.cause.message ?: "Unknown error"),
                error = SavingWeightFailed(intent.cause)
            )

            // search
            is HomeIntent.SearchQueryChanged -> state.copy(
                searchQuery = intent.query
            )

            HomeIntent.ClearSearchClicked -> state.copy(
                searchQuery = ""
            )

            // home screen data
            is HomeIntent.OnHomeDataLoaded -> state.copy(
                mainLoading = HomeState.LoadingState.Idle,
                user = intent.user,
                summaryCard = intent.summary,
                waterCard = intent.water,
                weightCard = intent.weight,
                error = null
            )

            is HomeIntent.OnHomeDataError -> state.copy(
                mainLoading = Error(intent.error.message),
                error = intent.error
            )

            is HomeIntent.SelectedDate -> {
                state.copy(selectedDate = intent.date)
            }

            else -> state
        }
    }
}