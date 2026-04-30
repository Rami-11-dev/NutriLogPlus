package com.saliery.nutrilog.app.presentation.mealEntryScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.Wallpapers
import androidx.compose.ui.unit.dp
import com.saliery.nutrilog.R
import com.saliery.nutrilog.app.domain.model.entries.LocalMealType
import com.saliery.nutrilog.app.presentation.common.ScreenErrorContent
import com.saliery.nutrilog.app.presentation.common.ScreenLoadingContent
import com.saliery.nutrilog.app.presentation.helper.previewMealEntryList
import com.saliery.nutrilog.app.presentation.mealEntryScreen.components.MealEntryItemsSection
import com.saliery.nutrilog.app.presentation.mealEntryScreen.components.MealEntrySummaryHeroCard
import com.saliery.nutrilog.app.presentation.mealEntryScreen.components.MealTimeSection
import com.saliery.nutrilog.app.presentation.mealEntryScreen.components.MealTypeSelectorSection
import com.saliery.nutrilog.ui.theme.NutriLogTheme
import com.saliery.nutrilog.ui.theme.OnboardingGlassTokens
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.rememberHazeState
import java.time.LocalDate
import java.time.LocalTime

@Composable
fun MealEntryContent(
    state: MealEntryState,
    contentPadding: PaddingValues,
    hazeState: HazeState,
    onIntent: (MealEntryIntent) -> Unit
) {
    when {
        state.isLoading -> {
            ScreenLoadingContent(contentPadding)
        }

        state.errorMessage != null && state.entryId != null && state.items.isEmpty() -> {
            ScreenErrorContent(
                message = state.errorMessage,
                contentPadding = contentPadding,
                onRetryClick = { onIntent(MealEntryIntent.RetryClicked) }
            )
        }

        else -> {

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize(),
                contentPadding = PaddingValues(
                    start = 16.dp,
                    end = 16.dp,
                    top = contentPadding.calculateTopPadding() + 8.dp,
                    bottom = contentPadding.calculateBottomPadding() + 16.dp
                ),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                item {
                    MealTypeSelectorSection(
                        selectedType = state.mealType,
                        hazeState = hazeState,
                        onMealTypeSelected = {
                            onIntent(MealEntryIntent.MealTypeChanged(it))
                        }
                    )
                }

                item {
                    MealTimeSection(
                        selectedTime = state.selectedTime,
                        hazeState = hazeState,
                        onTimeChanged = { time ->
                            onIntent(MealEntryIntent.TimeChanged(time))
                        }
                    )
                }

                item {
                    MealEntrySummaryHeroCard(
                        summary = state.summary,
                        hazeState = hazeState
                    )
                }

                item {
                    MealEntryItemsSection(
                        items = state.items,
                        hazeState = hazeState,
                        modifier = Modifier,
                        onAddProductClick = { onIntent(MealEntryIntent.AddProductClicked) },
                        onAddRecipeClick = { onIntent(MealEntryIntent.AddRecipeClicked) },
                        onAmountChanged = { itemId, value ->
                            onIntent(MealEntryIntent.ItemAmountChanged(itemId, value))
                        },
                        onIncreaseClick = { itemId ->
                            onIntent(MealEntryIntent.IncreaseItemAmountClicked(itemId))
                        },
                        onDecreaseClick = { itemId ->
                            onIntent(MealEntryIntent.DecreaseItemAmountClicked(itemId))
                        },
                        onRemoveClick = { itemId ->
                            onIntent(MealEntryIntent.RemoveItemClicked(itemId))
                        }
                    )
                }

                item {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        TextButton(
                            onClick = { onIntent(MealEntryIntent.SaveAsRecipeClicked) },
                            colors = ButtonDefaults.textButtonColors(
                                contentColor = OnboardingGlassTokens.TextSecondary
                            )
                            ) {
                            Text(
                                text = stringResource(R.string.save_as_recipe_str),
                                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.85f),
                                style = MaterialTheme.typography.labelLarge
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(
    wallpaper = Wallpapers.GREEN_DOMINATED_EXAMPLE,
    locale = "en"
)
@Composable
private fun MealEntryContentPreview() {

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
            totalCarbs = 54.2,
            caloriesTarget = 2300.0,
            proteinTarget = 123.9,
            fatTarget = 32.0,
            carbTarget = 21.3
        ),
        errorMessage = null,
        hasChanges = false
    ) }

    NutriLogTheme(
        darkTheme = true
    ) {
        MealEntryContent(
            state = state,
            contentPadding = PaddingValues(vertical = 4.dp),
            hazeState = rememberHazeState(),
            onIntent = {}
        )
    }
}