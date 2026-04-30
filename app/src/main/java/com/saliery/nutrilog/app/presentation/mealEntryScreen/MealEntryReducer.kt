package com.saliery.nutrilog.app.presentation.mealEntryScreen

import com.saliery.nutrilog.app.domain.model.entries.MealEntryModel
import com.saliery.nutrilog.app.domain.model.entries.MealItemModel
import com.saliery.nutrilog.app.domain.model.product.FoodProductModelLite
import com.saliery.nutrilog.app.domain.model.recipe.RecipeListItemModel
import com.saliery.nutrilog.app.domain.model.recipe.RecipeModel
import com.saliery.nutrilog.app.presentation.helper.toSafeDouble

class MealEntryReducer {

    fun reduce(
        state: MealEntryState,
        intent: MealEntryIntent
    ): MealEntryState {
        return when (intent) {
            is MealEntryIntent.LoadEntry -> {
                state.copy(
                    entryId = intent.entryId,
                    isNewEntry = false,
                    isLoading = true,
                    errorMessage = null
                )
            }

            is MealEntryIntent.MealTypeChanged -> {
                state.copy(
                    mealType = intent.mealType,
                    hasChanges = true
                )
            }

            is MealEntryIntent.TimeChanged -> {
                state.copy(
                    selectedTime = intent.time,
                    hasChanges = true
                )
            }

            MealEntryIntent.AddProductClicked -> state
            MealEntryIntent.AddRecipeClicked -> state
            MealEntryIntent.SaveAsRecipeClicked -> state

            is MealEntryIntent.ProductAdded -> {
                state.copy(
                    items = state.items + intent.product.toMealEntryProductUiItem(
                        id = generateDraftItemId(state.items)
                    ),
                    hasChanges = true
                )
            }

            is MealEntryIntent.RecipeAdded -> {
                state.copy(
                    items = state.items + intent.recipe.toMealEntryRecipeUiItem(
                        id = generateDraftItemId(state.items)
                    ),
                    hasChanges = true
                )
            }

            is MealEntryIntent.ItemAmountChanged -> {
                state.copy(
                    items = state.items.map { item ->
                        if (item.id == intent.itemId) {
                            item.updateAmount(intent.value)
                        } else item
                    },
                    hasChanges = true
                )
            }

            is MealEntryIntent.IncreaseItemAmountClicked -> {
                state.copy(
                    items = state.items.map { item ->
                        if (item.id == intent.itemId) item.stepUp() else item
                    },
                    hasChanges = true
                )
            }

            is MealEntryIntent.DecreaseItemAmountClicked -> {
                state.copy(
                    items = state.items.map { item ->
                        if (item.id == intent.itemId) item.stepDown() else item
                    },
                    hasChanges = true
                )
            }

            is MealEntryIntent.RemoveItemClicked -> {
                state.copy(
                    items = state.items.filterNot { it.id == intent.itemId },
                    hasChanges = true
                )
            }

            MealEntryIntent.SaveClicked -> {
                state.copy(
                    isSaving = true,
                    errorMessage = null
                )
            }

            MealEntryIntent.DeleteClicked -> {
                state.copy(
                    isDeleting = true,
                    errorMessage = null
                )
            }

            MealEntryIntent.RetryClicked -> {
                if (state.entryId == null) state
                else state.copy(
                    isLoading = true,
                    errorMessage = null
                )
            }
        }
    }

    fun onLoadSuccess(
        state: MealEntryState,
        entry: MealEntryModel
    ): MealEntryState {
        val uiItems = entry.items.map { it.toUiItem() }

        return state.copy(
            entryId = entry.id,
            isNewEntry = false,
            isLoading = false,
            isSaving = false,
            isDeleting = false,
            selectedDate = entry.dateTime.toLocalDate(),
            selectedTime = entry.dateTime.toLocalTime(),
            mealType = entry.mealType,
            items = uiItems,
            errorMessage = null,
            hasChanges = false
        )
    }

    fun onSaveSuccess(
        state: MealEntryState,
        entryId: Long
    ): MealEntryState {
        return state.copy(
            entryId = entryId,
            isNewEntry = false,
            isSaving = false,
            errorMessage = null,
            hasChanges = false
        )
    }

    fun onDeleteSuccess(
        state: MealEntryState
    ): MealEntryState {
        return state.copy(
            isDeleting = false,
            errorMessage = null
        )
    }

    fun onError(
        state: MealEntryState,
        message: String
    ): MealEntryState {
        return state.copy(
            isLoading = false,
            isSaving = false,
            isDeleting = false,
            errorMessage = message
        )
    }

    fun withSummary(
        state: MealEntryState,
        summary: MealEntrySummaryUiModel
    ): MealEntryState {
        return state.copy(summary = summary)
    }

    private fun generateDraftItemId(items: List<MealEntryItemUiModel>): Long {
        val minId = items.minOfOrNull { it.id } ?: 0L
        return if (minId <= 0L) minId - 1L else -1L
    }

    private fun FoodProductModelLite.toMealEntryProductUiItem(id: Long): MealEntryItemUiModel.ProductUiItem {
        val grams = 100.0

        return MealEntryItemUiModel.ProductUiItem(
            id = id,
            amountInput = "100",
            amountUnit = AmountUnit.GRAMS,
            calories = ((caloriesPer100g ?: 0.0) * grams) / 100.0,
            proteins = ((proteinsPer100g ?: 0.0) * grams) / 100.0,
            fats = ((fatsPer100g ?: 0.0) * grams) / 100.0,
            carbs = ((carbsPer100g ?: 0.0) * grams) / 100.0,
            productPreview = this
        )
    }

    private fun MealEntryItemUiModel.updateAmount(value: String): MealEntryItemUiModel {
        return when (this) {
            is MealEntryItemUiModel.ProductUiItem -> copy(amountInput = value).recalculate()
            is MealEntryItemUiModel.RecipeUiItem -> copy(amountInput = value).recalculate()
        }
    }

    private fun RecipeListItemModel.toMealEntryRecipeUiItem(id: Long): MealEntryItemUiModel.RecipeUiItem {
        val servings = 1.0
        val divisor = servingsYield.takeIf { it > 0.0 } ?: 1.0

        return MealEntryItemUiModel.RecipeUiItem(
            id = id,
            amountInput = "1",
            amountUnit = AmountUnit.PORTIONS,
            calories = (totalCalories / divisor) * servings,
            proteins = (totalProteins / divisor) * servings,
            fats = (totalFats / divisor) * servings,
            carbs = (totalCarbs / divisor) * servings,
            recipePreview = this
        )
    }

    private fun MealItemModel.toUiItem(): MealEntryItemUiModel {
        return when (this) {
            is MealItemModel.ProductItemModel -> {
                MealEntryItemUiModel.ProductUiItem(
                    id = id,
                    amountInput = consumedGrams.toInputString(),
                    amountUnit = AmountUnit.GRAMS,
                    calories = ((product.caloriesPer100g ?: 0.0) * consumedGrams) / 100.0,
                    proteins = ((product.proteinsPer100g ?: 0.0) * consumedGrams) / 100.0,
                    fats = ((product.fatsPer100g ?: 0.0) * consumedGrams) / 100.0,
                    carbs = ((product.carbsPer100g ?: 0.0) * consumedGrams) / 100.0,
                    productPreview = product
                )
            }

            is MealItemModel.RecipeItemModel -> {
                val divisor = recipeModel.servingsYield.takeIf { it > 0.0 } ?: 1.0
                val preview = recipeModel.toListItemModel()

                MealEntryItemUiModel.RecipeUiItem(
                    id = id,
                    amountInput = consumedServings.toInputString(),
                    amountUnit = AmountUnit.PORTIONS,
                    calories = (preview.totalCalories / divisor) * consumedServings,
                    proteins = (preview.totalProteins / divisor) * consumedServings,
                    fats = (preview.totalFats / divisor) * consumedServings,
                    carbs = (preview.totalCarbs / divisor) * consumedServings,
                    recipePreview = preview
                )
            }
        }
    }

    private fun RecipeModel.toListItemModel(): RecipeListItemModel {
        val totalCalories = ingredients.sumOf { ((it.productPreview.caloriesPer100g ?: 0.0) * it.amountGrams) / 100.0 }
        val totalProteins = ingredients.sumOf { ((it.productPreview.proteinsPer100g ?: 0.0) * it.amountGrams) / 100.0 }
        val totalFats = ingredients.sumOf { ((it.productPreview.fatsPer100g ?: 0.0) * it.amountGrams) / 100.0 }
        val totalCarbs = ingredients.sumOf { ((it.productPreview.carbsPer100g ?: 0.0) * it.amountGrams) / 100.0 }

        return RecipeListItemModel(
            id = id,
            name = name,
            servingsYield = servingsYield,
            ingredientCount = ingredients.size,
            totalCalories = totalCalories,
            totalProteins = totalProteins,
            totalFats = totalFats,
            totalCarbs = totalCarbs,
            updatedAt = updatedAt
        )
    }

    private fun MealEntryItemUiModel.stepUp(): MealEntryItemUiModel {
        return when (this) {
            is MealEntryItemUiModel.ProductUiItem -> {
                val current = amountInput.toSafeDouble().takeIf { it > 0.0 } ?: 100.0
                copy(amountInput = (current + 20.0).toInputString()).recalculate()
            }
            is MealEntryItemUiModel.RecipeUiItem -> {
                val current = amountInput.toSafeDouble().takeIf { it > 0.0 } ?: 1.0
                copy(amountInput = (current + 0.5).toInputString()).recalculate()
            }
        }
    }

    private fun MealEntryItemUiModel.stepDown(): MealEntryItemUiModel {
        return when (this) {
            is MealEntryItemUiModel.ProductUiItem -> {
                val current = amountInput.toSafeDouble().takeIf { it > 0.0 } ?: 100.0
                val next = (current - 20.0).coerceAtLeast(0.0)
                copy(amountInput = next.toInputString()).recalculate()
            }
            is MealEntryItemUiModel.RecipeUiItem -> {
                val current = amountInput.toSafeDouble().takeIf { it > 0.0 } ?: 1.0
                val next = (current - 0.5).coerceAtLeast(0.0)
                copy(amountInput = next.toInputString()).recalculate()
            }
        }
    }

    private fun MealEntryItemUiModel.ProductUiItem.recalculate(): MealEntryItemUiModel.ProductUiItem {
        val grams = amountInput.toSafeDouble()

        return copy(
            calories = ((productPreview.caloriesPer100g ?: 0.0) * grams) / 100.0,
            proteins = ((productPreview.proteinsPer100g ?: 0.0) * grams) / 100.0,
            fats = ((productPreview.fatsPer100g ?: 0.0) * grams) / 100.0,
            carbs = ((productPreview.carbsPer100g ?: 0.0) * grams) / 100.0
        )
    }

    private fun MealEntryItemUiModel.RecipeUiItem.recalculate(): MealEntryItemUiModel.RecipeUiItem {
        val servings = amountInput.toSafeDouble()
        val divisor = recipePreview.servingsYield.takeIf { it > 0.0 } ?: 1.0

        return copy(
            calories = (recipePreview.totalCalories / divisor) * servings,
            proteins = (recipePreview.totalProteins / divisor) * servings,
            fats = (recipePreview.totalFats / divisor) * servings,
            carbs = (recipePreview.totalCarbs / divisor) * servings
        )
    }

    private fun Double.toInputString(): String {
        return if (this % 1.0 == 0.0) {
            toInt().toString()
        } else {
            String.format(java.util.Locale.US, "%.1f", this)
                .replace('.', ',')
        }
    }
}