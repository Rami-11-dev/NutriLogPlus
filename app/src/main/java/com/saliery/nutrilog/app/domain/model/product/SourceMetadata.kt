package com.saliery.nutrilog.app.domain.model.product

data class SourceMetadata(
    val dataType: String?,       // SR Legacy, Branded, etc.
    val publicationDate: Long?,
    val externalNumericId: Int?, // USDA fdcId if needed as Int
    val lastModified: Long?,
    val lastEditor: String? // OFF specific only
)
