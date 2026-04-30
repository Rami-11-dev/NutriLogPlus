package com.saliery.nutrilog.app.data.remote.dto

data class NutriscoreDataDto(
    val components: ComponentsDto
)

data class ComponentsDto(
    val negative: List<SingleComponentDto>?,
    val positive: List<SingleComponentDto>?
)

data class SingleComponentDto(
    val id: String?,
    val points: Int,
    val points_max: Int,
    val unit: String?,
    val value: Any?
)