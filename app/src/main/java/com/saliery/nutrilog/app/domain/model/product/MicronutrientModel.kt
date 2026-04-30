package com.saliery.nutrilog.app.domain.model.product

data class MicronutrientModel(
    // === Macrominerals ===
    val potassiumMg: Double?,
    val calciumMg: Double?,
    val magnesiumMg: Double?,
    val phosphorusMg: Double?,

    // === Microelements ===
    val ironMg: Double?,
    val zincMg: Double?,
    val seleniumMcg: Double?,
    val copperMg: Double?,
    val manganeseMg: Double?,

    // === Vitamins (B-complex) ===
    val vitaminB1Mg: Double?, // Thiamin
    val vitaminB2Mg: Double?, // Riboflavin
    val vitaminB3Mg: Double?, // Niacin
    val vitaminB5Mg: Double?, // Pantothenic acid
    val vitaminB6Mg: Double?,
    val vitaminB12Mcg: Double?,

    // === Vitamins (fat-soluble) ===
    val vitaminAMcg: Double?, // RAE
    val vitaminDMcg: Double?,
    val vitaminEMg: Double?, // Alpha-tocopherol
    val vitaminKMcg: Double?,

    // === Vitamin C ===
    val vitaminCMg: Double?,

    // === Folate ===
    val folateTotalMcg: Double?,
    val folateFoodMcg: Double?,
    val folicAcidMcg: Double?,

    // === Active elements ===
    val caffeineMg: Double?,
    val cholineMg: Double?,

    // === Antioxidants ===
    val luteinZeaxanthinMcg: Double?,
    val epaMg: Double?, // Omega-3
    val dhaMg: Double?, // Omega-3

    // === Other components ===
    val waterG: Double?,
    val alcoholG: Double?,
    val ashG: Double?
)
