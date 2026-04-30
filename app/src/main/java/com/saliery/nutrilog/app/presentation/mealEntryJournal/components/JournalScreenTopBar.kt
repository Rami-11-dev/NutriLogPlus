package com.saliery.nutrilog.app.presentation.mealEntryJournal.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.DateRange
import androidx.compose.material.icons.rounded.KeyboardArrowLeft
import androidx.compose.material.icons.rounded.KeyboardArrowRight
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.Wallpapers
import androidx.compose.ui.unit.dp
import com.saliery.nutrilog.app.presentation.common.GlassWheelDatePicker
import com.saliery.nutrilog.app.presentation.helper.toFormattedDate
import com.saliery.nutrilog.ui.theme.NutriLogTheme
import com.saliery.nutrilog.ui.theme.OnboardingGlassTokens
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.HazeStyle
import dev.chrisbanes.haze.HazeTint
import dev.chrisbanes.haze.hazeEffect
import dev.chrisbanes.haze.rememberHazeState
import kotlinx.coroutines.delay
import kotlinx.datetime.toJavaLocalDate
import java.time.LocalDate

@Composable
fun JournalScreenTopBar(
    hazeState: HazeState,
    selectedDate: LocalDate,
    onDateChange: (LocalDate) -> Unit,
    // Available in future releases.
    // todo: Implement search filters
    filtersContent: (@Composable RowScope.() -> Unit)? = null
) {
    var showDatePicker by rememberSaveable { mutableStateOf(false) }
    val today = remember { LocalDate.now() }
    val isToday = selectedDate == today

    var pendingDate by remember { mutableStateOf(selectedDate) }

    LaunchedEffect(pendingDate) {
        if (pendingDate != selectedDate) {
            delay(500)
            onDateChange(pendingDate)
        }
    }

    LaunchedEffect(selectedDate) {
        pendingDate = selectedDate
    }

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
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .padding(horizontal = 8.dp, vertical = 12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Spacer(modifier = Modifier.width(16.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    IconButton(
                        onClick = { pendingDate = pendingDate.minusDays(1) }
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.KeyboardArrowLeft,
                            contentDescription = "Previous day",
                            tint = OnboardingGlassTokens.TextPrimary
                        )
                    }

                    Text(
                        text = pendingDate.toFormattedDate(),
                        color = OnboardingGlassTokens.TextPrimary,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .clickable { showDatePicker = true }
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    )

                    IconButton(
                        onClick = { pendingDate = pendingDate.plusDays(1) }
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.KeyboardArrowRight,
                            contentDescription = "Next day",
                            tint = OnboardingGlassTokens.TextPrimary
                        )
                    }
                }

                IconButton(
                    onClick = { if (!isToday) pendingDate = today },
                    enabled = true,
                    modifier = Modifier.weight(0.5f)
                ) {
                    Icon(
                        imageVector = if (isToday) Icons.Rounded.DateRange else Icons.Rounded.Refresh,
                        contentDescription = "Today",
                        tint = if (isToday) {
                            OnboardingGlassTokens.TextTertiary.copy(alpha = 0.5f)
                        } else {
                            OnboardingGlassTokens.Primary
                        }
                    )
                }
            }

            if (filtersContent != null) {
                Row(
                    modifier = Modifier.padding(start = 8.dp, end = 8.dp, top = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    content = filtersContent
                )
            }
        }

        if (showDatePicker) {
            GlassWheelDatePicker(
                showDatePicker = showDatePicker,
                hazeState = hazeState,
                onDismiss = { showDatePicker = false },
                onDoneClick = { date ->
                    pendingDate = date.toJavaLocalDate()
                    showDatePicker = false
                }
            )
        }
    }
}

@Preview(
    wallpaper = Wallpapers.GREEN_DOMINATED_EXAMPLE,
    locale = "en"
)
@Composable
private fun JournalScreenTopBarPreview() {

    NutriLogTheme(
        darkTheme = true
    ) {
        JournalScreenTopBar(
            hazeState = rememberHazeState(),
            selectedDate = LocalDate.now(),
            onDateChange = {}
        )
    }
}