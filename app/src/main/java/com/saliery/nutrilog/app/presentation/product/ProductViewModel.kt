package com.saliery.nutrilog.app.presentation.product

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saliery.nutrilog.app.data.local.room.mapper.product.toModelLite
import com.saliery.nutrilog.app.domain.model.business.ProductLoadState
import com.saliery.nutrilog.app.domain.repository.ProductRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProductViewModel(
    private val reducer: ProductReducer,
    private val productRepository: ProductRepository
) : ViewModel() {

    private val _state = MutableStateFlow(ProductState())
    val state: StateFlow<ProductState> = _state.asStateFlow()

    private val _effects = MutableSharedFlow<ProductEffect>()
    val effects: SharedFlow<ProductEffect> = _effects.asSharedFlow()

    private var observeJob: Job? = null

    fun onIntent(intent: ProductIntent) {
        when (intent) {
            ProductIntent.AddToMealClicked -> openAddToMeal()
            is ProductIntent.LoadProduct -> loadProduct(intent.productId)
            ProductIntent.RefreshClicked -> refreshProduct()
            ProductIntent.RetryClicked -> retryLoad()
            is ProductIntent.SectionToggled -> {
                _state.update { reducer.reduce(it, intent) }
            }
            ProductIntent.ToggleFavoriteClicked -> toggleFavorite()
        }
    }

    private fun loadProduct(productId: String) {
        _state.update { reducer.reduce(it, ProductIntent.LoadProduct(productId)) }

        observeJob?.cancel()
        observeJob = viewModelScope.launch {
            productRepository.observeProductById(productId)
                .collect { loadState ->
                    when (loadState) {
                        is ProductLoadState.Error -> {
                            _state.update {
                                reducer.onLoadError(
                                    it,
                                    loadState.exception.localizedMessage ?: "Unknown product loading error"
                                )
                            }
                        }
                        is ProductLoadState.Found -> {
                            _state.update { reducer.onLoadSuccess(it, loadState.product) }
                        }
                        ProductLoadState.NotFound -> {
                            _state.update { reducer.onProductNotFound(it) }
                        }
                    }
                }
        }
    }

    private fun retryLoad() {
        val productId = _state.value.productId ?: return
        loadProduct(productId)
    }

    private fun refreshProduct() {
        val productId = _state.value.productId ?: return

        _state.update { reducer.reduce(it, ProductIntent.RefreshClicked) }

        viewModelScope.launch {
            val result = productRepository.refreshProduct(productId)

            result.onSuccess { product ->
                if (product != null) {
                    _state.update { reducer.onLoadSuccess(it, product) }
                    _effects.emit(ProductEffect.ShowMessage("Product updated"))
                } else {
                    _state.update {
                        reducer.onLoadError(it, "Unable to update product")
                    }
                }
            }.onFailure { error ->
                _state.update {
                    reducer.onLoadError(
                        it,
                        error.message ?: "Unknown error"
                    )
                }
                _effects.emit(
                    ProductEffect.ShowMessage("Update failed")
                )
            }
        }
    }

    private fun openAddToMeal() {
        val productId = _state.value.product ?: return

        viewModelScope.launch {
            _effects.emit(ProductEffect.OpenAddToMeal(productId.toModelLite()))
        }
    }

    private fun toggleFavorite() {
        viewModelScope.launch {
            _effects.emit(ProductEffect.ShowMessage("Favorite action is not implemented yet"))
        }
    }
}