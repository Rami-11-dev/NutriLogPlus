package com.saliery.nutrilog.app.presentation.product.components

import android.os.Build
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductTopBar(
    title: String,
    isRefreshing: Boolean,
    isFavorite: Boolean,
    hazeState: HazeState?,
    onBackClick: () -> Unit,
    onRefreshClick: () -> Unit,
    onFavoriteClick: () -> Unit
) {

    TopAppBar(
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = OnboardingGlassTokens.TextPrimary
            )
        },
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                    contentDescription = "Назад",
                    tint = OnboardingGlassTokens.Primary
                )
            }
        },
        actions = {
            IconButton(enabled = !isRefreshing, onClick = onRefreshClick) {
                Icon(
                    imageVector = Icons.Rounded.Refresh,
                    contentDescription = stringResource(R.string.refresh_str),
                    tint = OnboardingGlassTokens.TextSecondary
                )
            }

            IconButton(onClick = onFavoriteClick) {
                Icon(
                    imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Rounded.FavoriteBorder,
                    contentDescription = stringResource(R.string.favorite_str),
                    tint = if (isFavorite) OnboardingGlassTokens.BmiRed else OnboardingGlassTokens.TextSecondary
                )
            }
        },
        // Transparent background and blur
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent,
            scrolledContainerColor = Color.Transparent
        ),
        modifier = Modifier.then(
            if (hazeState != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                Modifier
                    .hazeEffect(
                        state = hazeState,
                        style = HazeStyle(
                            blurRadius = 30.dp,
                            noiseFactor = 0.05f,
                            tint = HazeTint(OnboardingGlassTokens.GlassSurface.copy(alpha = 0.2f))
                        ),
                        block = null
                    )
            } else Modifier
        )
    )
}

@Preview(
    wallpaper = Wallpapers.GREEN_DOMINATED_EXAMPLE
)
@Composable
private fun ProductTopBarPreview() {

    val hazeState = rememberHazeState()

    NutriLogTheme(
        darkTheme = true
    ) {
        ProductTopBar(
            title = "Product",
            isRefreshing = false,
            isFavorite = true,
            onBackClick = {},
            onRefreshClick = {},
            hazeState = hazeState
        ) { }
    }
}