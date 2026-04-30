package com.saliery.nutrilog.app.data.local.room.mapper.product

import com.saliery.nutrilog.app.data.local.room.entity.product.ProductMicronutrientEntity
import com.saliery.nutrilog.app.domain.model.product.MicronutrientModel

fun ProductMicronutrientEntity.toDomain(): MicronutrientModel {
    return MicronutrientModel(
        potassiumMg = potassiumMg,
        calciumMg = calciumMg,
        magnesiumMg = magnesiumMg,
        phosphorusMg = phosphorusMg,
        ironMg = ironMg,
        zincMg = zincMg,
        seleniumMcg = seleniumMcg,
        copperMg = copperMg,
        manganeseMg = manganeseMg,
        vitaminB1Mg = vitaminB1Mg,
        vitaminB2Mg = vitaminB2Mg,
        vitaminB3Mg = vitaminB3Mg,
        vitaminB5Mg = vitaminB5Mg,
        vitaminB6Mg = vitaminB6Mg,
        vitaminB12Mcg = vitaminB12Mcg,
        vitaminAMcg = vitaminAMcg,
        vitaminDMcg = vitaminDMcg,
        vitaminEMg = vitaminEMg,
        vitaminKMcg = vitaminKMcg,
        vitaminCMg = vitaminCMg,
        folateTotalMcg = folateTotalMcg,
        folateFoodMcg = folateFoodMcg,
        folicAcidMcg = folicAcidMcg,
        caffeineMg = caffeineMg,
        cholineMg = cholineMg,
        luteinZeaxanthinMcg = luteinZeaxanthinMcg,
        epaMg = epaMg,
        dhaMg = dhaMg,
        waterG = waterG,
        alcoholG = alcoholG,
        ashG = ashG
    )
}