package com.saliery.nutrilog.app.presentation.product.components

import android.os.Build
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material.icons.rounded.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.Wallpapers
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.saliery.nutrilog.R
import com.saliery.nutrilog.ui.theme.NutriLogTheme
import com.saliery.nutrilog.ui.theme.OnboardingGlassTokens
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.HazeStyle
import dev.chrisbanes.haze.hazeChild
import dev.chrisbanes.haze.hazeEffect

@Composable
fun ExpandableProductSection(
    title: String,
    icon: Painter,
    expanded: Boolean,
    onToggle: () -> Unit,
    hazeState: HazeState? = null,
    content: @Composable () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(20.dp))
                .clickable(
                    interactionSource = interactionSource,
                    indication = ripple(color = OnboardingGlassTokens.Primary.copy(alpha = 0.1f)),
                    onClick = onToggle
                )
                .padding(vertical = 12.dp, horizontal = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                Box(
                    modifier = Modifier
                        .size(38.dp)
                        .background(
                            OnboardingGlassTokens.Primary.copy(alpha = 0.12f),
                            CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = icon,
                        contentDescription = null,
                        tint = OnboardingGlassTokens.Primary,
                        modifier = Modifier.size(20.dp)
                    )
                }

                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 0.2.sp
                    ),
                    color = OnboardingGlassTokens.TextPrimary
                )
            }


            Icon(
                imageVector = if (expanded) Icons.Rounded.KeyboardArrowUp else Icons.Rounded.KeyboardArrowDown,
                contentDescription = null,
                tint = OnboardingGlassTokens.TextSecondary.copy(alpha = 0.6f),
                modifier = Modifier.size(28.dp)
            )
        }


        AnimatedVisibility(
            visible = expanded,
            enter = fadeIn(animationSpec = tween(300)) + expandVertically(),
            exit = fadeOut(animationSpec = tween(300)) + shrinkVertically()
        ) {
            Box(
                modifier = Modifier
                    .padding(top = 8.dp, bottom = 8.dp)
                    .fillMaxWidth()
                    // Эффект Haze для Android 12+
                    .then(
                        if (hazeState != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                            Modifier.hazeEffect(hazeState, HazeStyle.Unspecified, null)
                        } else Modifier
                    )

                    .background(
                        OnboardingGlassTokens.GlassSurface,
                        RoundedCornerShape(24.dp)
                    )
                    .border(
                        width = 1.dp,
                        color = OnboardingGlassTokens.GlassBorder,
                        shape = RoundedCornerShape(24.dp)
                    )
                    .padding(20.dp)
            ) {
                content()
            }
        }
    }
}

@Preview(
    wallpaper = Wallpapers.GREEN_DOMINATED_EXAMPLE
)
@Composable
private fun ExpandableProductSectionPreview() {
    NutriLogTheme(
        darkTheme = false
    ) {
        ExpandableProductSection(
            title = "Title",
            icon = painterResource(R.drawable.unknown_filled_svgrepo_com),
            expanded = false,
            onToggle = {},
            content = {}
        )
    }
}