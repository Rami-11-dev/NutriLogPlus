package com.saliery.nutrilog.app.presentation.mealEntryJournal

import com.saliery.nutrilog.app.domain.model.entries.MealEntryPreviewModel
import com.saliery.nutrilog.app.domain.model.user.User
import com.saliery.nutrilog.app.presentation.mealEntryScreen.MealEntrySummaryUiModel
import java.time.LocalDate

data class JournalState(
    val selectedDate: LocalDate = LocalDate.now(),
    val user: User? = null,
    val dailySummary: MealEntrySummaryUiModel = MealEntrySummaryUiModel(),
    val meals: List<MealEntryPreviewModel> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)