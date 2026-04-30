package com.saliery.nutrilog.app.data.remote.dto

import com.google.gson.annotations.SerializedName
import com.saliery.nutrilog.app.data.remote.dto.NutrimentsDto

data class ProductDto(
    val code: String?,
    val product_name: String?,
    val brands: String?,
    val food_groups: String?,
    val allergens: String?,
    val traces: String?,
    @SerializedName("additives_tags")
    val additivesTags: List<String>?,
    @SerializedName("nova_group")
    val novaGroup: Int?,
    @SerializedName("last_edit_dates_tags")
    val lastModified: List<String>?,
    val ingredients: List<IngredientDto>?,
    @SerializedName("nutrition_grades")
    val nutritionGrades: String?,
    val nutriments: NutrimentsDto?,
    val selected_images: SelectedImagesDto?,
    val nutriscore_data: NutriscoreDataDto?,
    @SerializedName("product_quantity")
    val productQuantity: Double?,
    @SerializedName("product_quantity_unit")
    val productQuantityUnit: String?,
    val quantity: String?,
    @SerializedName("created_t")
    val createdAt: Long?,
    @SerializedName("last_updated_t")
    val lastUpdatedAt: Long?,
    @SerializedName("last_editor")
    val lastEditor: String?
)
