package com.saliery.nutrilog.app.data.remote.dto

import com.google.gson.annotations.SerializedName

data class IngredientDto(
    val id: String?,
    @SerializedName("is_in_taxonomy")
    val isInTaxonomy: Int?,
    @SerializedName("percent_estimate")
    val percentEstimate: Double?,
    @SerializedName("percent_max")
    val percentMax: Double?,
    @SerializedName("percent_min")
    val percentMin: Double?,
    val text: String?
)
