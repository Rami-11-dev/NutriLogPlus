package com.saliery.nutrilog.app.presentation.helper

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import androidx.compose.ui.platform.LocalConfiguration
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun Long.toFormattedDate(): String {
    val locale = LocalConfiguration.current.locales[0]

    return remember(this, locale) {
        val date = Date(this * 1000)
        SimpleDateFormat("dd MMMM yyyy", locale).format(date)
    }
}

@Composable
fun LocalDate.toFormattedDate(): String {
    val locale = LocalConfiguration.current.locales[0]

    return remember(this, locale) {
        val formatter = DateTimeFormatter.ofPattern("dd MMM yyyy", locale)

        this.format(formatter)
    }
}