package com.saliery.nutrilog.app.data.remote.dto

data class GetNutriscoreDataResponse(
    val code: String?,
    val product: NutriscoreDataProduct?,
    val status: Int,
    val status_verbose: String?
)

data class NutriscoreDataProduct(
    val nutriscore_data: NutriscoreDataDto?
)
