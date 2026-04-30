package com.saliery.nutrilog.app.presentation.mealEntryJournal

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saliery.nutrilog.app.domain.model.entries.MealEntryPreviewModel
import com.saliery.nutrilog.app.domain.model.user.User
import com.saliery.nutrilog.app.domain.repository.MealEntryRepository
import com.saliery.nutrilog.app.domain.repository.UserRepository
import com.saliery.nutrilog.app.domain.usecase.CalculateDailyCaloriesUseCase
import com.saliery.nutrilog.app.domain.usecase.CalculateMacroTargets
import com.saliery.nutrilog.app.presentation.mealEntryJournal.JournalEffect.*
import com.saliery.nutrilog.app.presentation.mealEntryJournal.JournalIntent.*
import com.saliery.nutrilog.app.presentation.mealEntryScreen.MealEntrySummaryUiModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate

class JournalViewModel(
    private val repository: MealEntryRepository,
    private val userRepository: UserRepository,
    private val calculateMacroTargets: CalculateMacroTargets,
    private val calculateDailyCaloriesUseCase: CalculateDailyCaloriesUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(JournalState())
    val state: StateFlow<JournalState> = _state.asStateFlow()

    private val _effect = MutableSharedFlow<JournalEffect>()
    val effect: SharedFlow<JournalEffect> = _effect.asSharedFlow()

    private var dataObservationJob: Job? = null

    init {
        onIntent(ChangeDate(LocalDate.now()))
    }

    fun onIntent(intent: JournalIntent) {
        when (intent) {
            is ChangeDate -> {
                _state.update { it.copy(selectedDate = intent.date, isLoading = true) }
                observeDataForDate(intent.date)
            }

            SelectToday -> {
                onIntent(ChangeDate(LocalDate.now()))
            }

            is EditMeal -> {
                viewModelScope.launch {
                    _effect.emit(NavigateToEntryDetail(intent.entryId))
                }
            }

            AddNewMeal -> {
                viewModelScope.launch {
                    _effect.emit(NavigateToEntryDetail(null))
                }
            }

            is DeleteMeal -> {
                handleDeleteMeal(intent.entryId)
            }

            Refresh -> {
                observeDataForDate(_state.value.selectedDate)
            }

            LoadUser -> {
                viewModelScope.launch {
                    val user = userRepository.getUser()

                    if (user != null) {
                        _state.update { it.copy(user = user) }
                    }
                }
            }
        }
    }

    private fun handleDeleteMeal(mealEntryId: Long) {
        viewModelScope.launch {
            try {
                repository.deleteEntry(mealEntryId)
            } catch (e: Exception) {
                _effect.emit(JournalEffect.ShowError("Delete operation failed with error: ${e.localizedMessage}"))
            }
        }
    }

    private fun observeDataForDate(date: LocalDate) {
        dataObservationJob?.cancel()

        dataObservationJob = viewModelScope.launch {
            combine(
                repository.observeEntriesForDate(date),
                userRepository.observeUser()
            ) { meals, user ->
                val summary = calculateSummary(meals, user)

                Triple(meals, user, summary)
            }.collect { (meals, user, summary) ->
                _state.update { it.copy(
                    meals = meals,
                    user = user,
                    dailySummary = summary,
                    isLoading = false
                ) }
            }
        }
    }

    private fun calculateSummary(
        meals: List<MealEntryPreviewModel>,
        user: User?
    ): MealEntrySummaryUiModel {

        val dailyCalories = user?.let { calculateDailyCaloriesUseCase(it) }
        val macroTargets = if (user != null && dailyCalories != null) {
            calculateMacroTargets(dailyCalories, user.goal)
        } else null

        return MealEntrySummaryUiModel(
            totalCalories = meals.sumOf { it.caloriesTotal },
            totalProteins = meals.sumOf { it.proteinsTotal },
            totalFats = meals.sumOf { it.fatsTotal },
            totalCarbs = meals.sumOf { it.carbsTotal },
            // Targets
            caloriesTarget = dailyCalories,
            proteinTarget = macroTargets?.proteinGrams,
            fatTarget = macroTargets?.fatGrams,
            carbTarget = macroTargets?.carbGrams
        )
    }
}