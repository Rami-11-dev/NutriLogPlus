package com.saliery.nutrilog.app.presentation.common

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.Wallpapers
import androidx.compose.ui.unit.dp
import com.saliery.nutrilog.R
import com.saliery.nutrilog.ui.theme.NutriLogTheme
import com.saliery.nutrilog.ui.theme.OnboardingGlassTokens
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.HazeStyle
import dev.chrisbanes.haze.HazeTint
import dev.chrisbanes.haze.hazeEffect
import dev.chrisbanes.haze.rememberHazeState

@Composable
fun ScreenListTopBar(
    query: String,
    placeholder: String,
    hazeState: HazeState,
    onBackClick: () -> Unit,
    onQueryChange: (String) -> Unit,
    onClearClick: () -> Unit,
    onSearchClick: () -> Unit,
    // Available in future releases.
    // todo: Implement search filters
    filtersContent: (@Composable RowScope.() -> Unit)? = null
) {
    Surface(
        modifier = Modifier
                .fillMaxWidth()
            .hazeEffect(
                state = hazeState,
                style = HazeStyle(
                    blurRadius = 25.dp,
                    tint = HazeTint(color = Color.Unspecified)
                ),
                block = null
            ),
        color = Color.Transparent
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                    contentDescription = stringResource(R.string.back_btn),
                    tint = OnboardingGlassTokens.TextPrimary
                )
            }

            SearchField(
                query = query,
                placeholder = placeholder,
                modifier = Modifier.weight(1f),
                onQueryChange = onQueryChange,
                onClearClick = onClearClick,
                onSearchClick = onSearchClick
            )
        }

        if (filtersContent != null) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                content = filtersContent
            )
        }
    }
}

@Composable
private fun SearchField(
    query: String,
    placeholder: String,
    modifier: Modifier = Modifier,
    onQueryChange: (String) -> Unit,
    onClearClick: () -> Unit,
    onSearchClick: () -> Unit
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(18.dp),
        color = OnboardingGlassTokens.GlassSurfaceStrong,
        border = BorderStroke(1.dp, OnboardingGlassTokens.GlassBorder)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 14.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Icon(
                imageVector = Icons.Rounded.Search,
                contentDescription = null,
                tint = OnboardingGlassTokens.TextTertiary
            )

            BasicTextField(
                value = query,
                onValueChange = onQueryChange,
                modifier = Modifier.weight(1f),
                singleLine = true,
                textStyle = MaterialTheme.typography.bodyLarge.copy(
                    color = OnboardingGlassTokens.TextPrimary
                ),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Search
                ),
                keyboardActions = KeyboardActions(
                    onSearch = { onSearchClick() }
                ),
                decorationBox = { innerTextField ->
                    if (query.isBlank()) {
                        Text(
                            text = placeholder,
                            style = MaterialTheme.typography.bodyLarge,
                            color = OnboardingGlassTokens.TextTertiary
                        )
                    }
                    innerTextField()
                }
            )

            AnimatedVisibility(visible = query.isNotBlank()) {
                IconButton(onClick = onClearClick) {
                    Icon(
                        imageVector = Icons.Rounded.Close,
                        contentDescription = stringResource(R.string.clean_btn_desc_str),
                        tint = OnboardingGlassTokens.TextSecondary
                    )
                }
            }
        }
    }
}

@Preview(
    wallpaper = Wallpapers.GREEN_DOMINATED_EXAMPLE
)
@Composable
private fun ScreenListTopBarPreview() {

    val hazeState = rememberHazeState()

    NutriLogTheme(
        darkTheme = true
    ) {
        ScreenListTopBar(
            query = "apple",
            placeholder = "Try to find something",
            hazeState = hazeState,
            onBackClick = {},
            onQueryChange = {},
            onClearClick = {},
            onSearchClick = {}
        )
    }
}