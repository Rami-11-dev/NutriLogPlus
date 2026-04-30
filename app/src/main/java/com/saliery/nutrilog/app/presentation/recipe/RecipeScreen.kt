package com.saliery.nutrilog.app.presentation.recipe

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
import com.saliery.nutrilog.app.presentation.helper.previewEditableRecipeIngredientUiModel
import com.saliery.nutrilog.app.presentation.recipe.components.RecipeBottomActionBar
import com.saliery.nutrilog.app.presentation.recipe.components.RecipeTopBar
import com.saliery.nutrilog.ui.theme.NutriLogTheme
import com.saliery.nutrilog.ui.theme.OnboardingGlassTokens
import dev.chrisbanes.haze.hazeSource
import dev.chrisbanes.haze.rememberHazeState

@Composable
fun RecipeScreen(
    state: RecipeState,
    snackbarHostState: SnackbarHostState,
    onBackClick: () -> Unit,
    onIntent: (RecipeIntent) -> Unit
) {
    val hazeState = rememberHazeState()

    Scaffold(
        topBar = {
            RecipeTopBar(
                isNewRecipe = state.isNewRecipe,
                isSaving = state.isSaving,
                isDeleting = state.isDeleting,
                hazeState = hazeState,
                onBackClick = onBackClick,
                onDeleteClick = { onIntent(RecipeIntent.DeleteClicked) }
            )
        },
        bottomBar = {
            RecipeBottomActionBar(
                isNewRecipe = state.isNewRecipe,
                enabled = !state.isSaving && !state.isDeleting,
                isLoading = state.isSaving,
                hazeState = hazeState,
                onSaveClick = { onIntent(RecipeIntent.SaveClicked) }
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
            RecipeContent(
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
private fun RecipeScreenPreview() {

    val state = remember { RecipeState() }

    NutriLogTheme(
        darkTheme = true
    ) {
        RecipeScreen(
            state = state.copy(
                recipeId = 1212,
                isNewRecipe = false,
                nameInput = "Caesar salad",
                ingredients = previewEditableRecipeIngredientUiModel()
            ),
            snackbarHostState = SnackbarHostState(),
            onBackClick = {},
            onIntent = {}
        )
    }
}