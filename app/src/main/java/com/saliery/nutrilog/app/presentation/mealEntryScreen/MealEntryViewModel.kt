package com.saliery.nutrilog.app.presentation.mealEntryScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saliery.nutrilog.app.domain.model.entries.MealEntryModel
import com.saliery.nutrilog.app.domain.model.entries.MealItemModel
import com.saliery.nutrilog.app.domain.model.user.User
import com.saliery.nutrilog.app.domain.repository.MealEntryRepository
import com.saliery.nutrilog.app.domain.repository.RecipeRepository
import com.saliery.nutrilog.app.domain.repository.UserRepository
import com.saliery.nutrilog.app.domain.usecase.CalculateDailyCaloriesUseCase
import com.saliery.nutrilog.app.domain.usecase.CalculateMacroTargets
import com.saliery.nutrilog.app.presentation.helper.toSafeDouble
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class MealEntryViewModel(
    private val reducer: MealEntryReducer,
    private val mealEntryRepository: MealEntryRepository,
    private val recipeRepository: RecipeRepository,
    private val userRepository: UserRepository,
    private val calculateMacroTargets: CalculateMacroTargets,
    private val calculateDailyCaloriesUseCase: CalculateDailyCaloriesUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(
        MealEntryState(
            selectedDate = LocalDate.now(),
            selectedTime = LocalTime.now().withSecond(0).withNano(0)
        )
    )

    val state: StateFlow<MealEntryState> = _state.asStateFlow()

    private val _effects = MutableSharedFlow<MealEntryEffect>()
    val effects: SharedFlow<MealEntryEffect> = _effects.asSharedFlow()

    private var observeJob: Job? = null

    private var currentUser: User? = null

    init {
        viewModelScope.launch {
            userRepository.observeUser().collect { user ->
                currentUser = user

                _state.update {
                    reducer.withSummary(it, calculateSummary(it, currentUser))
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        observeJob?.cancel()
    }

    fun onIntent(intent: MealEntryIntent) {
        when (intent) {
            is MealEntryIntent.LoadEntry -> loadEntry(intent.entryId)

            is MealEntryIntent.MealTypeChanged -> {
                _state.update { reducer.reduce(it, intent) }
            }

            is MealEntryIntent.TimeChanged -> {
                _state.update { reducer.reduce(it, intent) }
            }

            MealEntryIntent.AddProductClicked -> {
                viewModelScope.launch {
                    _effects.emit(MealEntryEffect.OpenProductPicker)
                }
            }

            MealEntryIntent.AddRecipeClicked -> {
                viewModelScope.launch {
                    _effects.emit(MealEntryEffect.OpenRecipePicker)
                }
            }

            is MealEntryIntent.ProductAdded -> {
                _state.update {
                    val newState = reducer.reduce(it, intent)
                    reducer.withSummary(newState, calculateSummary(newState, currentUser))
                }
            }

            is MealEntryIntent.RecipeAdded -> {
                _state.update {
                    val newState = reducer.reduce(it, intent)
                    reducer.withSummary(newState, calculateSummary(newState, currentUser))
                }
            }

            is MealEntryIntent.ItemAmountChanged -> {
                _state.update {
                    val newState = reducer.reduce(it, intent)
                    reducer.withSummary(newState, calculateSummary(newState, currentUser))
                }
            }

            is MealEntryIntent.IncreaseItemAmountClicked -> {
                _state.update {
                    val newState = reducer.reduce(it, intent)
                    reducer.withSummary(newState, calculateSummary(newState, currentUser))
                }
            }

            is MealEntryIntent.DecreaseItemAmountClicked -> {
                _state.update {
                    val newState = reducer.reduce(it, intent)
                    reducer.withSummary(newState, calculateSummary(newState, currentUser))
                }
            }

            is MealEntryIntent.RemoveItemClicked -> {
                _state.update {
                    val newState = reducer.reduce(it, intent)
                    reducer.withSummary(newState, calculateSummary(newState, currentUser))
                }
            }

            MealEntryIntent.SaveAsRecipeClicked -> {
                viewModelScope.launch {
                    val draft = _state.value.items.toRecipeDraft()

                    viewModelScope.launch {
                        _effects.emit(
                            MealEntryEffect.OpenRecipeWithDraft(draft)
                        )
                    }
                }
            }

            MealEntryIntent.SaveClicked -> saveEntry()
            MealEntryIntent.DeleteClicked -> deleteEntry()
            MealEntryIntent.RetryClicked -> retryLoad()
        }
    }

    private fun loadEntry(entryId: Long) {
        if (_state.value.entryId == entryId && !_state.value.isNewEntry) return

        _state.update { reducer.reduce(it, MealEntryIntent.LoadEntry(entryId)) }

        observeJob?.cancel()
        observeJob = viewModelScope.launch {
            runCatching {
                mealEntryRepository.observeEntry(entryId).first()
            }.onSuccess { entry ->
                if (entry != null) {
                    val loadedState = reducer.onLoadSuccess(_state.value, entry)
                    _state.value = reducer.withSummary(
                        loadedState,
                        calculateSummary(loadedState, currentUser)
                    )
                } else {
                    _state.update {
                        reducer.onError(it, "Meal entry not found")
                    }
                }
            }.onFailure { error ->
                _state.update {
                    reducer.onError(
                        it,
                        error.message ?: "Failed to load meal entry"
                    )
                }
            }
        }
    }

    private fun retryLoad() {
        val entryId = _state.value.entryId ?: return
        loadEntry(entryId)
    }

    private fun saveEntry() {
        val currentState = _state.value

        _state.update { reducer.reduce(it, MealEntryIntent.SaveClicked) }

        viewModelScope.launch {
            val entry = buildDomainEntry(currentState)

            if (entry == null) {
                _state.update {
                    reducer.onError(it, "Invalid meal entry data")
                }
                _effects.emit(MealEntryEffect.ShowMessage("Проверьте время и состав приёма пищи"))
                return@launch
            }

            runCatching {
                if (currentState.isNewEntry || currentState.entryId == null) {
                    mealEntryRepository.addEntry(entry)
                } else {
                    mealEntryRepository.updateEntry(entry)
                    entry.id
                }
            }.onSuccess { savedId ->
                _state.update {
                    reducer.onSaveSuccess(it, savedId)
                }
                _effects.emit(MealEntryEffect.ShowMessage("Приём пищи сохранён"))
                _effects.emit(MealEntryEffect.NavigateBack)
            }.onFailure { error ->
                _state.update {
                    reducer.onError(
                        it,
                        error.message ?: "Failed to save meal entry"
                    )
                }
                _effects.emit(MealEntryEffect.ShowMessage("Не удалось сохранить приём пищи"))
            }
        }
    }

    private fun deleteEntry() {
        val entryId = _state.value.entryId ?: return

        _state.update { reducer.reduce(it, MealEntryIntent.DeleteClicked) }

        viewModelScope.launch {
            runCatching {
                mealEntryRepository.deleteEntry(entryId)
            }.onSuccess {
                _state.update {
                    reducer.onDeleteSuccess(it)
                }
                _effects.emit(MealEntryEffect.ShowMessage("Приём пищи удалён"))
                _effects.emit(MealEntryEffect.NavigateBack)
            }.onFailure { error ->
                _state.update {
                    reducer.onError(
                        it,
                        error.message ?: "Failed to delete meal entry"
                    )
                }
                _effects.emit(MealEntryEffect.ShowMessage("Не удалось удалить приём пищи"))
            }
        }
    }

    private fun calculateSummary(state: MealEntryState, user: User?): MealEntrySummaryUiModel {
        val totalCalories = state.items.sumOf { it.calories }
        val totalProteins = state.items.sumOf { it.proteins }
        val totalFats = state.items.sumOf { it.fats }
        val totalCarbs = state.items.sumOf { it.carbs }

        if (user == null) {
            return MealEntrySummaryUiModel(
                totalCalories = totalCalories,
                totalProteins = totalProteins,
                totalFats = totalFats,
                totalCarbs = totalCarbs
            )
        }

        val dailyCalories = calculateDailyCaloriesUseCase(user)
        val macroTargets = calculateMacroTargets(dailyCalories, user.goal)

        return MealEntrySummaryUiModel(
            totalCalories = totalCalories,
            totalProteins = totalProteins,
            totalFats = totalFats,
            totalCarbs = totalCarbs,

            caloriesTarget = dailyCalories,
            proteinTarget = macroTargets.proteinGrams,
            fatTarget = macroTargets.fatGrams,
            carbTarget = macroTargets.carbGrams
        )
    }

    private suspend fun buildDomainEntry(state: MealEntryState): MealEntryModel? {
        if (state.items.isEmpty()) return null

        val dateTime = LocalDateTime.of(state.selectedDate, state.selectedTime)
        val domainItems = mutableListOf<MealItemModel>()

        for (item in state.items) {
            when (item) {
                is MealEntryItemUiModel.ProductUiItem -> {
                    val grams = item.amountInput.toSafeDouble().takeIf { it > 0.0 } ?: return null

                    domainItems.add(
                        MealItemModel.ProductItemModel(
                            id = item.id,
                            product = item.productPreview,
                            consumedGrams = grams,
                            cookingMethod = null
                        )
                    )
                }
                is MealEntryItemUiModel.RecipeUiItem -> {
                    val servings = item.amountInput.toSafeDouble().takeIf { it > 0.0 } ?: return null
                    val recipeModel = recipeRepository.getRecipeById(item.recipePreview.id) ?: return null

                    domainItems.add(
                        MealItemModel.RecipeItemModel(
                            id = item.id,
                            recipeModel = recipeModel,
                            consumedServings = servings,
                            cookingMethod = null
                        )
                    )
                }
            }
        }

        return MealEntryModel(
            id = state.entryId ?: 0L,
            mealType = state.mealType,
            dateTime = dateTime,
            items = domainItems
        )
    }
}