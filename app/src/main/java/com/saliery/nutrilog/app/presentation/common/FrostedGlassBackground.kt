package com.saliery.nutrilog.app.presentation.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.Wallpapers
import androidx.compose.ui.unit.dp
import com.saliery.nutrilog.ui.theme.NutriLogTheme

@Composable
fun FrostedGlassBackground(
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit
) {
    Box(
        modifier = modifier
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFFF8FFFA),
                        Color(0xFFEFF9F1),
                        Color(0xFFE8F6EC)
                    )
                )
            )
    ) {

        content()
    }
}

@Preview(
    wallpaper = Wallpapers.NONE
)
@Composable
private fun FrostedGlassBackgroundPreview() {
    NutriLogTheme(
        darkTheme = true
    ) {
        FrostedGlassBackground(
            content = {}
        )
    }
}