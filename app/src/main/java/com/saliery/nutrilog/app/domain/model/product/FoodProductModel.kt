package com.saliery.nutrilog.app.domain.model.product

data class FoodProductModel(
    val id: String,                       // OFF: code(EAN), USDA: fdcId.toString()
    val source: ProductDataSourceEnum,

    // ---- Basic identification ----
    val name: String?,
    val brand: String?,
    val foodCategory: String?,

    // ---- Quality / classification ----
    val nutritionGrade: String?,         // OFF only, usually A-E
    val novaGroup: Int?,                 // OFF only, usually 1..4

    // ---- Product info ----
    val ingredients: List<ProductIngredientItem> = emptyList(),
    val allergens: List<String> = emptyList(),
    val additives: List<String> = emptyList(),
    val traces: List<String> = emptyList(),

    // ---- Nutrition ----
    val nutritionFacts: NutritionFacts,
    val micronutrients: MicronutrientModel?,

    // ---- Media / portions ----
    val images: List<FoodImageModel> = emptyList(),
    val portions: List<Portion> = emptyList(),

    // ---- Nutri-Score ----
    val nutriscoreSummary: NutriscoreSummary?,
    val nutriscoreComponents: List<NutriscoreComponent> = emptyList(),

    // ---- Source metadata ----
    val sourceMetadata: SourceMetadata?,

    val packageInfo: ProductPackageInfo?,

    // ---- App flags ----
    val isFavorite: Boolean = false,
    val isCached: Boolean = false
)