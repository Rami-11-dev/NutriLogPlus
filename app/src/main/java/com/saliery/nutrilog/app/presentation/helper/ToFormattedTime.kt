package com.saliery.nutrilog.app.presentation.helper

import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

fun LocalTime.toFormattedTime(): String {
    val timeFormatter = DateTimeFormatter.ofPattern("HH:mm")

    return this.format(timeFormatter)
}

fun LocalDateTime.toFormattedTime(): String {
    val timeFormatter = DateTimeFormatter.ofPattern("HH:mm")

    return this.format(timeFormatter)
}