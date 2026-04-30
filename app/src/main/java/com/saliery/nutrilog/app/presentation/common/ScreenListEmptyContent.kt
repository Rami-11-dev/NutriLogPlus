package com.saliery.nutrilog.app.presentation.common

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.Wallpapers
import androidx.compose.ui.unit.dp
import com.saliery.nutrilog.R
import com.saliery.nutrilog.ui.theme.NutriLogTheme
import com.saliery.nutrilog.ui.theme.OnboardingGlassTokens

@Composable
fun ScreenListEmptyContent(
    query: String?,
    contentPadding: PaddingValues,
    onRetryClick: () -> Unit
) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(contentPadding)
            .padding(horizontal = 24.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            Surface(
                shape = CircleShape,
                color = OnboardingGlassTokens.GlassSurfaceStrong,
                border = BorderStroke(1.dp, OnboardingGlassTokens.GlassBorder)
            ) {
                Icon(
                    painter = painterResource(R.drawable.box_open_svgrepo_com),
                    contentDescription = null,
                    modifier = Modifier.padding(18.dp).size(28.dp),
                    tint = OnboardingGlassTokens.TextSecondary
                )
            }

            Text(
                text = stringResource(R.string.nothing_found_str),
                style = MaterialTheme.typography.titleLarge,
                color = OnboardingGlassTokens.TextPrimary
            )

            Text(
                text = stringResource(R.string.no_res_for_search_query_str, query ?: ""),
                style = MaterialTheme.typography.bodyMedium,
                color = OnboardingGlassTokens.TextSecondary,
                textAlign = TextAlign.Center
            )

            TextButton(onClick = onRetryClick) {
                Text(
                    text = stringResource(R.string.try_again_str)
                )
            }
        }
    }
}

@Preview(
    wallpaper = Wallpapers.GREEN_DOMINATED_EXAMPLE,
    locale = "ru"
)
@Composable
private fun ScreenListEmptyContentPreview() {

    NutriLogTheme(
        darkTheme = true
    ) {
        ScreenListEmptyContent(
            contentPadding = PaddingValues(4.dp),
            query = "apple",
            onRetryClick = {}
        )
    }
}