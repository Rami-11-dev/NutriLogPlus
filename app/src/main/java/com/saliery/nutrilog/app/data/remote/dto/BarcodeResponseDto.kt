package com.saliery.nutrilog.app.data.remote.dto

data class BarcodeResponseDto(
    val code: String,
    val product: ProductDto?,
    val status: Int,
    val status_verbose: String?
)
