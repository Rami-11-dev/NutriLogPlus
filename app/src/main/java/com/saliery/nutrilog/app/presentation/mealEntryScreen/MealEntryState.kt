package com.saliery.nutrilog.app.presentation.mealEntryScreen

import com.saliery.nutrilog.app.domain.model.entries.LocalMealType
import java.time.LocalDate
import java.time.LocalTime

data class MealEntryState(
    val entryId: Long? = null,
    val isNewEntry: Boolean = true,

    val isLoading: Boolean = false,
    val isSaving: Boolean = false,
    val isDeleting: Boolean = false,

    val selectedDate: LocalDate,
    val selectedTime: LocalTime,
    val mealType: LocalMealType = LocalMealType.BREAKFAST,

    val items: List<MealEntryItemUiModel> = emptyList(),
    val summary: MealEntrySummaryUiModel = MealEntrySummaryUiModel(),

    val errorMessage: String? = null,
    val hasChanges: Boolean = false
)
