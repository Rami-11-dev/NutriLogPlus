package com.saliery.nutrilog.app.presentation.mealEntryJournal

interface JournalEffect {
    data class NavigateToEntryDetail(val entryId: Long?) : JournalEffect // null для новой записи
    data class ShowError(val message: String) : JournalEffect
    data object TriggerHapticFeedback : JournalEffect
}