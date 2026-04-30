package com.saliery.nutrilog.app.presentation.recipe.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material.icons.rounded.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.Wallpapers
import androidx.compose.ui.unit.dp
import com.saliery.nutrilog.app.presentation.common.GlassCard
import com.saliery.nutrilog.ui.theme.NutriLogTheme
import com.saliery.nutrilog.ui.theme.OnboardingGlassTokens
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.rememberHazeState

@Composable
fun RecipeBasicSection(
    nameInput: String,
    servingsYieldInput: String,
    hazeState: HazeState,
    onNameChanged: (String) -> Unit,
    onServingsYieldChanged: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val haptic = LocalHapticFeedback.current

    GlassCard(
        modifier = modifier.fillMaxWidth(),
        hazeState = hazeState,
        contentPadding = PaddingValues(20.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = "Основная информация",
                    style = MaterialTheme.typography.titleMedium,
                    color = OnboardingGlassTokens.TextPrimary
                )

                Text(
                    text = "Название рецепта и количество выходных порций.",
                    style = MaterialTheme.typography.bodySmall,
                    color = OnboardingGlassTokens.TextTertiary
                )
            }

            RecipeInputField(
                value = nameInput,
                label = "Название рецепта",
                placeholder = "Например, Гречка с овощами",
                onValueChange = onNameChanged
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.Bottom
            ) {
                RecipeInputField(
                    value = servingsYieldInput,
                    label = "Выход порций",
                    placeholder = "1",
                    modifier = Modifier.weight(1f),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Decimal,
                        imeAction = ImeAction.Done
                    ),
                    onValueChange = { newValue ->
                        val filtered = newValue.filter { it.isDigit() || it == '.' || it == ',' }
                        onServingsYieldChanged(filtered)
                    }
                )

                val step = 0.5f
                val minValue = 0.25f

                Surface(
                    modifier = Modifier.padding(bottom = 2.5.dp),
                    shape = RoundedCornerShape(16.dp),
                    color = OnboardingGlassTokens.GlassSurface,
                    border = BorderStroke(1.dp, OnboardingGlassTokens.GlassBorder)
                ) {
                    IconButton(
                        onClick = {
                            val currentValue = servingsYieldInput
                                .replace(",", ".")
                                .toFloatOrNull()
                                ?: 1f

                            val newValue = (currentValue - step).coerceAtLeast(minValue)
                            onServingsYieldChanged(newValue.toDisplayString())
                            haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                        },
                        modifier = Modifier.size(40.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.KeyboardArrowDown,
                            contentDescription = "Уменьшить порции",
                            tint = OnboardingGlassTokens.TextPrimary
                        )
                    }
                }

                Surface(
                    modifier = Modifier.padding(bottom = 2.5.dp),
                    shape = RoundedCornerShape(16.dp),
                    color = OnboardingGlassTokens.GlassSurface,
                    border = BorderStroke(1.dp, OnboardingGlassTokens.GlassBorder)
                ) {
                    IconButton(
                        onClick = {
                            val currentValue = servingsYieldInput
                                .replace(",", ".")
                                .toFloatOrNull()
                                ?: 1f

                            val newValue = currentValue + step
                            onServingsYieldChanged(newValue.toDisplayString())
                            haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                        },
                        modifier = Modifier.size(40.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.KeyboardArrowUp,
                            contentDescription = "Увеличить порции",
                            tint = OnboardingGlassTokens.TextPrimary
                        )
                    }
                }
            }

            Text(
                text = "Количество порций используется для расчёта пищевой ценности на 1 порцию.",
                style = MaterialTheme.typography.bodySmall,
                color = OnboardingGlassTokens.TextTertiary
            )
        }
    }
}

@Composable
private fun RecipeInputField(
    value: String,
    label: String,
    placeholder: String,
    modifier: Modifier = Modifier,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    onValueChange: (String) -> Unit
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelLarge,
            color = OnboardingGlassTokens.TextSecondary
        )

        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            color = OnboardingGlassTokens.GlassSurface,
            border = BorderStroke(1.dp, OnboardingGlassTokens.GlassBorder)
        ) {
            BasicTextField(
                value = value,
                onValueChange = onValueChange,
                singleLine = true,
                textStyle = MaterialTheme.typography.bodyLarge.copy(
                    color = OnboardingGlassTokens.TextPrimary
                ),
                keyboardOptions = keyboardOptions,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 14.dp),
                decorationBox = { innerTextField ->
                    if (value.isBlank()) {
                        Text(
                            text = placeholder,
                            style = MaterialTheme.typography.bodyLarge,
                            color = OnboardingGlassTokens.TextTertiary
                        )
                    }
                    innerTextField()
                }
            )
        }
    }
}

private fun Float.toDisplayString(): String {
    return if (this % 1f == 0f) {
        this.toInt().toString()
    } else {
        this.toString()
            .replace(".0", "")
            .replace('.', ',')
    }
}

@Preview(
    wallpaper = Wallpapers.GREEN_DOMINATED_EXAMPLE
)
@Composable
private fun RecipeBasicSectionPreview() {

    NutriLogTheme(
        darkTheme = true
    ) {
        RecipeBasicSection(
            nameInput = "Pelmeni",
            servingsYieldInput = "1",
            hazeState = rememberHazeState(),
            onNameChanged = {},
            onServingsYieldChanged = {}
        )
    }
}