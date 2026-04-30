package com.saliery.nutrilog.app.presentation.recipe

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saliery.nutrilog.app.domain.model.business.CookingMethod
import com.saliery.nutrilog.app.domain.model.recipe.RecipeIngredientModel
import com.saliery.nutrilog.app.domain.model.recipe.RecipeModel
import com.saliery.nutrilog.app.domain.repository.RecipeRepository
import com.saliery.nutrilog.app.domain.usecase.CalculateRecipeDraftSummaryUseCase
import com.saliery.nutrilog.app.presentation.mealEntryScreen.RecipeDraft
import com.saliery.nutrilog.app.presentation.mealEntryScreen.toRecipeModel
import com.saliery.nutrilog.app.presentation.recipe.helper.toDomainOrNull
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDateTime

class RecipeViewModel(
    private val reducer: RecipeReducer,
    private val recipeRepository: RecipeRepository,
    private val calculateRecipeDraftSummary: CalculateRecipeDraftSummaryUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(RecipeState())
    val state: StateFlow<RecipeState> = _state.asStateFlow()

    private val _effects = MutableSharedFlow<RecipeEffect>()
    val effects: SharedFlow<RecipeEffect> = _effects.asSharedFlow()

    private var observeJob: Job? = null

    fun onIntent(intent: RecipeIntent) {
        when (intent) {
            is RecipeIntent.LoadRecipe -> loadRecipe(intent.recipeId)
            is RecipeIntent.LoadRecipeWithDraft -> loadRecipeWithDraft(intent.draft)

            is RecipeIntent.NameChanged -> {
                _state.update { reducer.reduce(it, intent) }
            }

            is RecipeIntent.ServingsYieldChanged -> {
                _state.update {
                    val newState = reducer.reduce(it, intent)
                    reducer.withSummary(
                        newState,
                        calculateSummary(newState)
                    )
                }
            }

            RecipeIntent.AddIngredientClicked -> {
                viewModelScope.launch {
                    _effects.emit(RecipeEffect.OpenIngredientPicker)
                }
            }

            is RecipeIntent.IngredientAdded -> {
                _state.update {
                    val newState = reducer.reduce(it, intent)
                    reducer.withSummary(
                        newState,
                        calculateSummary(newState)
                    )
                }
            }

            is RecipeIntent.IngredientAmountChanged -> {
                _state.update {
                    val newState = reducer.reduce(it, intent)
                    reducer.withSummary(
                        newState,
                        calculateSummary(newState)
                    )
                }
            }

            is RecipeIntent.IngredientCookingMethodChanged -> {
                _state.update {
                    val newState = reducer.reduce(it, intent)
                    reducer.withSummary(
                        newState,
                        calculateSummary(newState)
                    )
                }
            }

            is RecipeIntent.RemoveIngredientClicked -> {
                _state.update {
                    val newState = reducer.reduce(it, intent)
                    reducer.withSummary(
                        newState,
                        calculateSummary(newState)
                    )
                }
            }

            RecipeIntent.SaveClicked -> saveRecipe()
            RecipeIntent.DeleteClicked -> deleteRecipe()
            RecipeIntent.RetryClicked -> retryLoad()
        }
    }

    private fun loadRecipe(recipeId: Long) {
        if (_state.value.recipeId == recipeId && !_state.value.isNewRecipe) return

        _state.update { reducer.reduce(it, RecipeIntent.LoadRecipe(recipeId)) }

        observeJob?.cancel()
        observeJob = viewModelScope.launch {
            recipeRepository.observeRecipe(recipeId)
                .catch { error ->
                    _state.update {
                        reducer.onError(
                            it,
                            error.message ?: "Failed to load recipe"
                        )
                    }
                }
                .collect { recipe ->
                    if (recipe != null) {
                        val loadedState = reducer.onLoadSuccess(_state.value, recipe)
                        _state.value = reducer.withSummary(
                            loadedState,
                            calculateSummary(loadedState)
                        )
                    } else {
                        _state.update {
                            reducer.onError(it, "Recipe not found")
                        }
                    }
                }
        }
    }

    private fun loadRecipeWithDraft(draft: RecipeDraft) {
        if (draft.ingredients.isEmpty()) {
            _state.update { reducer.onError(it, message = "Empty draft") }
            return
        }
        observeJob?.cancel()
        observeJob = null

        val loadedStateWithDraft = reducer.onDraftLoaded(_state.value, draft)
        _state.value = reducer.withSummary(
            loadedStateWithDraft,
            calculateSummary(loadedStateWithDraft)
        )
    }
    private fun retryLoad() {
        val recipeId = _state.value.recipeId ?: return
        loadRecipe(recipeId)
    }

    private fun saveRecipe() {
        val currentState = _state.value
        val recipe = currentState.toDomainOrNull()

        if (recipe == null) {
            viewModelScope.launch {
                _effects.emit(RecipeEffect.ShowMessage("Проверьте название, порции и ингредиенты"))
            }
            _state.update {
                reducer.onError(it, "Invalid recipe data")
            }
            return
        }

        _state.update { reducer.reduce(it, RecipeIntent.SaveClicked) }

        viewModelScope.launch {
            runCatching {
                if (currentState.isNewRecipe || currentState.recipeId == null) {
                    recipeRepository.addRecipe(recipe)
                } else {
                    recipeRepository.updateRecipe(recipe)
                    recipe.id
                }
            }.onSuccess { savedId ->
                _state.update {
                    reducer.onSaveSuccess(it, savedId)
                }
                _effects.emit(RecipeEffect.ShowMessage("Рецепт сохранён"))
                _effects.emit(RecipeEffect.NavigateBack)
            }.onFailure { error ->
                _state.update {
                    reducer.onError(
                        it,
                        error.message ?: "Failed to save recipe"
                    )
                }
                _effects.emit(RecipeEffect.ShowMessage("Не удалось сохранить рецепт"))
            }
        }
    }

    private fun deleteRecipe() {
        val recipeId = _state.value.recipeId ?: return

        _state.update { reducer.reduce(it, RecipeIntent.DeleteClicked) }

        viewModelScope.launch {
            runCatching {
                recipeRepository.deleteRecipe(recipeId)
            }.onSuccess {
                _state.update {
                    reducer.onDeleteSuccess(it)
                }
                _effects.emit(RecipeEffect.ShowMessage("Рецепт удалён"))
                _effects.emit(RecipeEffect.NavigateBack)
            }.onFailure { error ->
                _state.update {
                    reducer.onError(
                        it,
                        error.message ?: "Failed to delete recipe"
                    )
                }
                _effects.emit(RecipeEffect.ShowMessage("Не удалось удалить рецепт"))
            }
        }
    }

    private fun calculateSummary(state: RecipeState): RecipeNutritionSummaryUiModel {
        val servingsYield = state.servingsYieldInput
            .replace(',', '.')
            .toDoubleOrNull()
            ?: 1.0

        return calculateRecipeDraftSummary(
            ingredients = state.ingredients,
            servingsYield = servingsYield
        )
    }
}
