package com.saliery.nutrilog.app.presentation.mealEntryJournal

import java.time.LocalDate

sealed interface JournalIntent {

    data class ChangeDate(val date: LocalDate) : JournalIntent
    data object SelectToday : JournalIntent
    data object LoadUser : JournalIntent

    data class EditMeal(val entryId: Long) : JournalIntent
    data object AddNewMeal : JournalIntent

    data class DeleteMeal(val entryId: Long) : JournalIntent

    data object Refresh : JournalIntent
}