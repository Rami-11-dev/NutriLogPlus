package com.saliery.nutrilog.app.presentation.helper

fun String.toSafeDouble(): Double {
    return replace(',', '.').toDoubleOrNull() ?: 0.0
}