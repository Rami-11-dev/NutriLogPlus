package com.saliery.nutrilog.app.presentation.mealEntryJournal

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun JournalRoute(
    onNavigateToEntry: (Long?) -> Unit,
    viewModel: JournalViewModel = koinViewModel()
) {

    val state by viewModel.state.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is JournalEffect.NavigateToEntryDetail -> onNavigateToEntry(effect.entryId)
                is JournalEffect.ShowError -> snackbarHostState.showSnackbar(effect.message)
                JournalEffect.TriggerHapticFeedback -> {  }
            }
        }
    }

    JournalScreen(
        state = state,
        snackbarHostState = snackbarHostState,
        onIntent = viewModel::onIntent
    )
}