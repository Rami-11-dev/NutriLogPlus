package com.saliery.nutrilog.app.presentation.helper

import com.saliery.nutrilog.app.presentation.home.HomeState
import com.saliery.nutrilog.app.presentation.home.HomeSummaryCardUiModel
import com.saliery.nutrilog.app.presentation.home.HomeWaterCardUiModel
import com.saliery.nutrilog.app.presentation.home.HomeWeightCardUiModel
import java.time.LocalDate
import java.time.LocalDateTime

fun previewHomeState(): HomeState = HomeState(
    mainLoading = HomeState.LoadingState.Idle,
    waterLoading = HomeState.LoadingState.Idle,
    weightLoading = HomeState.LoadingState.Idle,

    selectedDate = LocalDate.now(),
    user = previewUser(),

    summaryCard = HomeSummaryCardUiModel(
        caloriesConsumed = 1850.0,
        caloriesGoal = 2500.0,
        proteinConsumed = 125.0,
        proteinGoal = 150.0,
        fatsConsumed = 65.0,
        fatsGoal = 80.0,
        carbsConsumed = 220.0,
        carbsGoal = 300.0,
        updatedAt = LocalDateTime.now()
    ),

    waterCard = HomeWaterCardUiModel(
        currentMl = 1800.0,
        goalMl = 2500.0,
        remainingMl = 700.0,
        progress = 0.72f,
        updatedAt = LocalDateTime.now()
    ),

    weightCard = HomeWeightCardUiModel(
        latestWeightKg = 78.5,
        weightChangeKg = -2.3,
        enteredWeightKg = 78.5,
        bmiValue = 24.8,
        chartPoints = listOf(82f, 81.8f, 81.2f, 80.1f, 79.4f, 78.9f, 78.5f),
        updatedAt = LocalDateTime.now()
    ),

    error = null,
    searchQuery = ""
)