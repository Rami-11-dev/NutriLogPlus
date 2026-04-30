package com.saliery.nutrilog.app.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saliery.nutrilog.app.domain.model.entries.MealEntryPreviewModel
import com.saliery.nutrilog.app.domain.model.user.DailyWaterProgress
import com.saliery.nutrilog.app.domain.model.user.User
import com.saliery.nutrilog.app.domain.model.user.WeightEntryModel
import com.saliery.nutrilog.app.domain.repository.MealEntryRepository
import com.saliery.nutrilog.app.domain.repository.UserRepository
import com.saliery.nutrilog.app.domain.repository.WaterRepository
import com.saliery.nutrilog.app.domain.repository.WeightRepository
import com.saliery.nutrilog.app.domain.usecase.CalculateBmiUseCase
import com.saliery.nutrilog.app.domain.usecase.CalculateBmrUseCase
import com.saliery.nutrilog.app.domain.usecase.CalculateDailyCaloriesUseCase
import com.saliery.nutrilog.app.domain.usecase.CalculateDailySummaryUseCase
import com.saliery.nutrilog.app.domain.usecase.CalculateDailyWaterUseCase
import com.saliery.nutrilog.app.domain.usecase.CalculateMacroTargets
import com.saliery.nutrilog.app.domain.usecase.SaveWeightUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import kotlin.math.pow

class HomeViewModel(
    private val reducer: HomeReducer,
    private val userRepository: UserRepository,
    private val waterRepository: WaterRepository,
    private val weightRepository: WeightRepository,
    private val mealRepository: MealEntryRepository,
    private val calculateBmiUseCase: CalculateBmiUseCase,
    private val calculateMacroTargets: CalculateMacroTargets,
    private val calculateDailyWaterUseCase: CalculateDailyWaterUseCase,
    private val calculateDailyCaloriesUseCase: CalculateDailyCaloriesUseCase,
    private val calculateDailySummaryUseCase: CalculateDailySummaryUseCase,
    private val saveWeightUseCase: SaveWeightUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(HomeState())
    val state: StateFlow<HomeState> = _state.asStateFlow()

    private val _effects = MutableSharedFlow<HomeEffect>()
    val effects: SharedFlow<HomeEffect> = _effects.asSharedFlow()

    private var homeDataJob: Job? = null

    private val selectedRange = MutableStateFlow(WeightTimeRange.WEEK)

    private companion object {
        const val GLASS_VOLUME_ML = 240.0
        const val BMI_MIN_HEALTHY = 18.5
        const val BMI_MAX_HEALTHY = 24.9
    }

    init {
        onIntent(HomeIntent.InitializeHome)
    }

    fun onIntent(intent: HomeIntent) {

        when (intent) {
            // initializing
            HomeIntent.InitializeHome -> {
                _state.update { reducer.reduce(it, intent) }
                loadHomeData()
            }

            HomeIntent.RefreshHome -> {
                _state.update { reducer.reduce(it, intent) }
                loadHomeData()
            }

            is HomeIntent.SelectedDate -> {
                _state.update { reducer.reduce(it, intent) }
                loadHomeData(intent.date)
            }

            // water
            HomeIntent.AddGlassOfWaterClicked -> {
                _state.update { reducer.reduce(it, intent) }
                addWater(GLASS_VOLUME_ML)
            }

            is HomeIntent.AddCustomWaterClicked -> {
                _state.update { reducer.reduce(it, intent) }
                addWater(intent.amountMl)
            }

            // weight
            is HomeIntent.SaveWeightClicked -> {
                _state.update { reducer.reduce(it, intent) }
                saveWeight(intent.weightKg)
            }

            is HomeIntent.WeightTimeRangeChanged -> {
                _state.update { reducer.reduce(it, intent) }
                selectedRange.value = intent.weightTimeRange
            }

            // search
            is HomeIntent.SearchQueryChanged -> {
                _state.update { reducer.reduce(it, intent) }
            }

            is HomeIntent.SearchSubmitted -> {
                val trimmedQuery = _state.value.searchQuery.trim()

                if (trimmedQuery.isNotBlank()) {
                    _state.update { it.copy(searchQuery = "") }

                    viewModelScope.launch {
                        _effects.emit(HomeEffect.NavigateToProductSearch(trimmedQuery))
                    }
                }
            }

            HomeIntent.ClearSearchClicked -> {
                _state.update { reducer.reduce(it, intent) }
            }

            // navigation
            HomeIntent.OpenMealEntryClicked -> {
                viewModelScope.launch {
                    _effects.emit(HomeEffect.NavigateToMealEntry)
                }
            }

            HomeIntent.OpenRecipesClicked -> {
                viewModelScope.launch {
                    _effects.emit(HomeEffect.NavigateToRecipes)
                }
            }

            HomeIntent.OpenCameraClicked -> {
                viewModelScope.launch {
                    _effects.emit(HomeEffect.NavigateToCamera)
                }
            }

            // other
            is HomeIntent.OnHomeDataLoaded -> {
                _state.update { reducer.reduce(it, intent) }
            }

            is HomeIntent.OnHomeDataError -> {
                _state.update { reducer.reduce(it, intent) }

                viewModelScope.launch {
                    _effects.emit(
                        HomeEffect.ShowMessage(
                            intent.error.message,
                            MessageType.ERROR
                        )
                    )
                }
            }

            is HomeIntent.OnWaterAddError -> {
                _state.update { reducer.reduce(it, intent) }

                viewModelScope.launch {
                    _effects.emit(
                        HomeEffect.ShowMessage(
                            "Failed to add water",
                            MessageType.ERROR
                        )
                    )
                }
            }

            is HomeIntent.OnWeightSaveError -> {
                _state.update { reducer.reduce(it, intent) }

                viewModelScope.launch {
                    _effects.emit(
                        HomeEffect.ShowMessage(
                            "Failed to update weight data",
                            MessageType.ERROR
                        )
                    )
                }
            }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun loadHomeData(date: LocalDate = LocalDate.now()) {
        homeDataJob?.cancel()

        homeDataJob = viewModelScope.launch {
            try {

                val weightFlow = selectedRange.flatMapLatest { range ->
                    val endDate = LocalDate.now()
                    val startDate = endDate.minusDays(range.days.toLong())
                    weightRepository.observeWeightEntriesByRange(startDate, endDate)
                }

                combine(
                    userRepository.observeUser(),
                    waterRepository.observeTodayProgress(),
                    weightFlow,
                    mealRepository.observeEntriesForDate(date)
                ) { user, waterProgress, weightEntries, mealEntries ->
                    buildHomeData(user, waterProgress, weightEntries, mealEntries)
                }.collect { dataPackage ->
                    if (dataPackage != null) {
                        onIntent(
                            HomeIntent.OnHomeDataLoaded(
                                user = dataPackage.user,
                                summary = dataPackage.summary,
                                water = dataPackage.water,
                                weight = dataPackage.weight
                            )
                        )
                    }
                }
            } catch (e: Exception) {
                onIntent(
                    HomeIntent.OnHomeDataError(HomeError.LoadingFailed(e))
                )
            }
        }
    }

    private fun buildHomeData(
        user: User?,
        waterProgress: DailyWaterProgress,
        weightEntries: List<WeightEntryModel>,
        mealEntries: List<MealEntryPreviewModel>
    ): DataPackage? {
        if (user == null) return null

        // === Summary Card ===
        val dailyCaloriesTarget = calculateDailyCaloriesUseCase(user)
        val macros = calculateMacroTargets(dailyCaloriesTarget, user.goal)
        val consumedSummary = calculateDailySummaryUseCase(mealEntries)

        val summaryCard = HomeSummaryCardUiModel(
            caloriesConsumed = consumedSummary.totalCalories,
            caloriesGoal = dailyCaloriesTarget,
            proteinConsumed = consumedSummary.totalProteins,
            proteinGoal = macros.proteinGrams,
            fatsConsumed = consumedSummary.totalFats,
            fatsGoal = macros.fatGrams,
            carbsConsumed = consumedSummary.totalCarbs,
            carbsGoal = macros.carbGrams,
            updatedAt = LocalDateTime.now()
        )

        // === Water Card ===
        val waterGoalMl = calculateDailyWaterUseCase(user)
        val waterCard = HomeWaterCardUiModel(
            currentMl = waterProgress.currentProgressMl,
            goalMl = waterGoalMl.waterMl,
            remainingMl = (waterGoalMl.waterMl - waterProgress.currentProgressMl).coerceAtLeast(0.0),
            progress = (waterProgress.currentProgressMl / waterGoalMl.waterMl)
                .toFloat()
                .coerceIn(0f, 1f),
            updatedAt = LocalDateTime.now()
        )

        // === Weight Card ===
        val heightMeters = user.heightCm / 100.0
        val minWeight = (BMI_MIN_HEALTHY * heightMeters.pow(2)).toFloat()
        val maxWeight = (BMI_MAX_HEALTHY * heightMeters.pow(2)).toFloat()
        val latestWeight = weightEntries.firstOrNull()
        val previousWeight = weightEntries.getOrNull(1)
        val weightCard = HomeWeightCardUiModel(
            latestWeightKg = latestWeight?.weightKg ?: user.weightKg,
            weightChangeKg = if (previousWeight != null && latestWeight != null) {
                latestWeight.weightKg - previousWeight.weightKg
            } else {
                null
            },
            bmiValue = calculateBmiUseCase(user),
            chartPoints = weightEntries.map { it.weightKg.toFloat() },
            weightTimeRange = selectedRange.value,
            minHealthWeight = minWeight,
            maxHealthWeight = maxWeight,
            updatedAt = LocalDateTime.now()
        )

        return DataPackage(user, summaryCard, waterCard, weightCard)
    }

    private fun addWater(amountMl: Double) {
        viewModelScope.launch {
            try {
                waterRepository.addWater(amountMl)
            } catch (e: Exception) {
                onIntent(HomeIntent.OnWaterAddError(e))
            }
        }
    }

    private fun saveWeight(weightKg: Double) {
        viewModelScope.launch {
            try {
                saveWeightUseCase(weightKg, LocalDateTime.now())
            } catch (e: Exception) {
                onIntent(HomeIntent.OnWeightSaveError(e))
            }
        }
    }

    private data class DataPackage(
        val user: User,
        val summary: HomeSummaryCardUiModel,
        val water: HomeWaterCardUiModel,
        val weight: HomeWeightCardUiModel
    )
}