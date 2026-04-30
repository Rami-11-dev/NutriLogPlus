package com.saliery.nutrilog.app.presentation.helper

import java.util.Locale

fun Double.trimSmart(): String {
    return if (this % 1.0 == 0.0) {
        toInt().toString()
    } else {
        String.format(Locale.US, "%.1f", this)
    }
}