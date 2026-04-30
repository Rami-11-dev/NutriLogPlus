package com.saliery.nutrilog.app.presentation.recipeList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saliery.nutrilog.app.domain.repository.RecipeRepository
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

class RecipeListViewModel(
    private val reducer: RecipeListReducer,
    private val recipeRepository: RecipeRepository
) : ViewModel() {

    private val _state = MutableStateFlow(RecipeListState())
    val state: StateFlow<RecipeListState> = _state.asStateFlow()

    private val _effects = MutableSharedFlow<RecipeListEffect>()
    val effects: SharedFlow<RecipeListEffect> = _effects.asSharedFlow()

    private var searchJob: Job? = null

    init {
        observeAllRecipes()
    }

    fun onIntent(intent: RecipeListIntent) {
        when (intent) {
            RecipeListIntent.ClearQueryClicked -> {
                searchJob?.cancel()
                _state.update { reducer.reduce(it, intent) }
                observeAllRecipes()
            }

            is RecipeListIntent.QueryChanged -> {
                _state.update { reducer.reduce(it, intent) }
            }

            is RecipeListIntent.RecipeClicked -> {
                viewModelScope.launch {
                    _effects.emit(RecipeListEffect.OpenRecipe(intent.recipeId))
                }
            }

            RecipeListIntent.RetryClicked -> {
                val submittedQuery = _state.value.submittedQuery

                _state.update { reducer.reduce(it, intent) }

                if (submittedQuery.isNullOrBlank()) {
                    observeAllRecipes()
                } else {
                    performSearch(submittedQuery)
                }
            }

            RecipeListIntent.SearchSubmitted -> {
                _state.update { reducer.reduce(it, intent) }

                val submittedQuery = _state.value.submittedQuery

                if (submittedQuery.isNullOrBlank()) {
                    observeAllRecipes()
                } else {
                    performSearch(submittedQuery)
                }
            }
        }
    }

    private fun observeAllRecipes() {
        searchJob?.cancel()

        searchJob = viewModelScope.launch {
            recipeRepository.getRecipes()
                .catch { error ->
                    _state.update {
                        reducer.onSearchError(
                            it,
                            error.message ?: "Unknown error"
                        )
                    }
                    _effects.emit(
                        RecipeListEffect.ShowMessage("Failed to load recipes")
                    )
                }
                .collect { items ->
                    _state.update {
                        reducer.onSearchSuccess(it, items)
                    }
                }
        }
    }

    private fun performSearch(query: String) {
        searchJob?.cancel()

        searchJob = viewModelScope.launch {
            recipeRepository.searchRecipes(query)
                .catch { error ->
                    _state.update {
                        reducer.onSearchError(
                            it,
                            error.message ?: "Unknown error"
                        )
                    }
                    _effects.emit(
                        RecipeListEffect.ShowMessage("Search failed!")
                    )
                }
                .collect { items ->
                    _state.update {
                        reducer.onSearchSuccess(it, items)
                    }
                }
        }
    }
}