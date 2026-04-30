package com.saliery.nutrilog.app.presentation.mealEntryScreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.Wallpapers
import com.saliery.nutrilog.app.domain.model.entries.LocalMealType
import com.saliery.nutrilog.app.presentation.helper.previewMealEntryList
import com.saliery.nutrilog.app.presentation.mealEntryScreen.components.MealEntryBottomActionBar
import com.saliery.nutrilog.app.presentation.mealEntryScreen.components.MealEntryTopBar
import com.saliery.nutrilog.ui.theme.NutriLogTheme
import com.saliery.nutrilog.ui.theme.OnboardingGlassTokens
import dev.chrisbanes.haze.hazeSource
import dev.chrisbanes.haze.rememberHazeState
import java.time.LocalDate
import java.time.LocalTime

@Composable
fun MealEntryScreen(
    state: MealEntryState,
    snackbarHostState: SnackbarHostState,
    onBackClick: () -> Unit,
    onIntent: (MealEntryIntent) -> Unit
) {

    val hazeState = rememberHazeState()

    Scaffold(
        topBar = {
            MealEntryTopBar(
                isNewEntry = state.isNewEntry,
                isSaving = state.isSaving,
                isDeleting = state.isDeleting,
                hazeState = hazeState,
                onBackClick = onBackClick,
                onDeleteClick = { onIntent(MealEntryIntent.DeleteClicked) }
            )
        },
        bottomBar = {
            MealEntryBottomActionBar(
                enabled = state.items.isNotEmpty() && !state.isSaving && !state.isDeleting,
                isNewEntry = state.isNewEntry,
                isLoading = state.isSaving,
                hazeState = hazeState,
                onSaveClick = { onIntent(MealEntryIntent.SaveClicked) }
            )
        },
        containerColor = OnboardingGlassTokens.ScreenBackground,
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .hazeSource(state = hazeState)
        ) {
            MealEntryContent(
                state = state,
                contentPadding = innerPadding,
                hazeState = hazeState,
                onIntent = onIntent
            )
        }
    }
}

@Preview(
    wallpaper = Wallpapers.GREEN_DOMINATED_EXAMPLE,
    locale = "en"
)
@Composable
private fun MealEntryScreenPreview() {

    val state = remember { MealEntryState(
        entryId = 1212,
        isNewEntry = false,
        isLoading = false,
        isSaving = false,
        isDeleting = false,
        selectedDate = LocalDate.now(),
        selectedTime = LocalTime.now(),
        mealType = LocalMealType.LUNCH,
        items = previewMealEntryList(),
        summary = MealEntrySummaryUiModel(
            totalCalories = 2300.0,
            totalProteins = 24.4,
            totalFats = 43.5,
            totalCarbs = 54.2
        ),
        errorMessage = null,
        hasChanges = false
    ) }

    NutriLogTheme(
        darkTheme = true
    ) {
        MealEntryScreen(
            state = state,
            snackbarHostState = SnackbarHostState(),
            onBackClick = {},
            onIntent = {},
        )
    }
}