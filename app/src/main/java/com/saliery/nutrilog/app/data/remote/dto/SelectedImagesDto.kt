package com.saliery.nutrilog.app.data.remote.dto

data class SelectedImagesDto(
    val front: ImageDetailsDto?,
    val ingredients: ImageDetailsDto?,
    val nutrition: ImageDetailsDto?,
    val packaging: ImageDetailsDto?
)

data class ImageDetailsDto(
    val display: Map<String, String>?,
    val small: Map<String, String>?,
    val thumb: Map<String, String>?
)
