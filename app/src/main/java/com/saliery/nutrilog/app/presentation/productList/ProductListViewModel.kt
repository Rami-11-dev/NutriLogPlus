package com.saliery.nutrilog.app.presentation.productList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saliery.nutrilog.app.domain.repository.ProductRepository
import com.saliery.nutrilog.app.presentation.productList.ProductListEffect.*
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

class ProductListViewModel(
    private val reducer: ProductListReducer,
    private val productRepository: ProductRepository
) : ViewModel() {

    private val _state = MutableStateFlow(ProductListState())
    val state: StateFlow<ProductListState> = _state.asStateFlow()

    private val _effects = MutableSharedFlow<ProductListEffect>()
    val effects: SharedFlow<ProductListEffect> = _effects.asSharedFlow()

    private var searchJob: Job? = null

    fun onIntent(intent: ProductListIntent) {

        when (intent) {
            ProductListIntent.ClearQueryClicked -> {
                searchJob?.cancel()
                _state.update { reducer.reduce(it, intent) }
            }
            is ProductListIntent.ProductClicked -> {
                viewModelScope.launch {
                    _effects.emit(OpenProduct(intent.productId))
                }
            }
            is ProductListIntent.QueryChanged -> {
                _state.update { reducer.reduce(it, intent) }
            }
            ProductListIntent.RetryClicked -> {
                val submittedQuery = _state.value.submittedQuery ?: return
                _state.update { reducer.reduce(it, intent) }
                performSearch(submittedQuery)
            }
            ProductListIntent.SearchSubmitted -> {
                val currentState = _state.value
                _state.update { reducer.reduce(it, intent) }

                val submittedQuery = _state.value.submittedQuery ?: return
                if (submittedQuery.isBlank()) return

                performSearch(submittedQuery)
            }

            is ProductListIntent.RunSearch -> {
                _state.update { reducer.reduce(it, intent) }
                performSearch(intent.query)
            }
        }
    }

    private fun performSearch(query: String) {

        searchJob?.cancel()

        searchJob = viewModelScope.launch {
            productRepository.searchProducts(query)
                .catch { error ->
                    _state.update {
                        reducer.onSearchError(
                            it,
                            error.message ?: "Unknown error"
                        )
                    }
                    _effects.emit(
                        ProductListEffect.ShowMessage("Search failed!")
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