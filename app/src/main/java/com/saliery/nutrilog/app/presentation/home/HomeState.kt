package com.saliery.nutrilog.app.presentation.home

import com.saliery.nutrilog.app.domain.model.user.User
import java.time.LocalDate
import java.time.LocalDateTime

data class HomeState(
    val mainLoading: LoadingState = LoadingState.Idle,
    val waterLoading: LoadingState = LoadingState.Idle,
    val weightLoading: LoadingState = LoadingState.Idle,

    val selectedDate: LocalDate = LocalDate.now(),
    val user: User? = null,
    val summaryCard: HomeSummaryCardUiModel? = null,
    val waterCard: HomeWaterCardUiModel? = null,
    val weightCard: HomeWeightCardUiModel? = null,

    val error: HomeError? = null,

    val searchQuery: String = ""
) {
    sealed interface LoadingState {
        data object Idle : LoadingState
        data object Loading : LoadingState
        data class Error(val message: String) : LoadingState
    }
}

sealed interface HomeError {
    data class LoadingFailed(val cause: Exception) : HomeError
    data class AddingWaterFailed(val cause: Exception) : HomeError
    data class SavingWeightFailed(val cause: Exception) : HomeError

    val message: String
        get() = when (this) {
            is AddingWaterFailed -> "Adding water failed: ${cause.localizedMessage}"
            is LoadingFailed -> "Loading failed: ${cause.localizedMessage}"
            is SavingWeightFailed -> "Weight saving failed: ${cause.localizedMessage}"
        }
}

data class HomeSummaryCardUiModel(
    val caloriesConsumed: Double = 0.0,
    val caloriesGoal: Double? = null,
    val proteinConsumed: Double = 0.0,
    val proteinGoal: Double? = null,
    val fatsConsumed: Double = 0.0,
    val fatsGoal: Double? = null,
    val carbsConsumed: Double = 0.0,
    val carbsGoal: Double? = null,
    val updatedAt: LocalDateTime? = null
)

data class HomeWaterCardUiModel(
    val currentMl: Double = 0.0,
    val goalMl: Double = 0.0,
    val remainingMl: Double = 0.0,
    val progress: Float = 0f,
    val updatedAt: LocalDateTime? = null
)

data class HomeWeightCardUiModel(
    val latestWeightKg: Double = 0.0,
    val weightChangeKg: Double? = null,
    val enteredWeightKg: Double? = null,
    val bmiValue: Double? = null,
    val chartPoints: List<Float> = emptyList(),
    val weightTimeRange: WeightTimeRange = WeightTimeRange.WEEK,
    val minHealthWeight: Float? = null,
    val maxHealthWeight: Float? = null,
    val updatedAt: LocalDateTime? = null
)

enum class WeightTimeRange(val days: Int) {
    WEEK(7), MONTH(30), QUARTER(90), YEAR(365)
}