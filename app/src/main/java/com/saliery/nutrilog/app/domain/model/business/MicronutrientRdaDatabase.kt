package com.saliery.nutrilog.app.domain.model.business

import com.saliery.nutrilog.app.domain.model.user.Sex

object MicronutrientRdaDatabase {

    // === Macrominerals ===
    val potassiumMg = listOf(
        RdaValue(AgeRange(9, 13), Sex.MALE, 4500.0, null),
        RdaValue(AgeRange(9, 13), Sex.FEMALE, 4500.0, null),
        RdaValue(AgeRange(14, 18), Sex.MALE, 4700.0, null),
        RdaValue(AgeRange(14, 18), Sex.FEMALE, 4700.0, null),
        RdaValue(AgeRange(19, 50), Sex.MALE, 4700.0, null),
        RdaValue(AgeRange(19, 50), Sex.FEMALE, 4700.0, null),
        RdaValue(AgeRange(51, Int.MAX_VALUE), Sex.MALE, 4700.0, null),
        RdaValue(AgeRange(51, Int.MAX_VALUE), Sex.FEMALE, 4700.0, null),
    )
    val calciumMg = listOf(
        RdaValue(AgeRange(9, 13), Sex.MALE, 1300.0, 2500.0),
        RdaValue(AgeRange(9, 13), Sex.FEMALE, 1300.0, 2500.0),
        RdaValue(AgeRange(14, 18), Sex.MALE, 1300.0, 2500.0),
        RdaValue(AgeRange(14, 18), Sex.FEMALE, 1300.0, 2500.0),
        RdaValue(AgeRange(19, 50), Sex.MALE, 1000.0, 2500.0),
        RdaValue(AgeRange(19, 50), Sex.FEMALE, 1000.0, 2500.0),
        RdaValue(AgeRange(51, Int.MAX_VALUE), Sex.MALE, 1200.0, 2500.0),
        RdaValue(AgeRange(51, Int.MAX_VALUE), Sex.FEMALE, 1200.0, 2500.0),
    )
    val magnesiumMg = listOf(
        RdaValue(AgeRange(9, 13), Sex.MALE, 240.0, null),
        RdaValue(AgeRange(9, 13), Sex.FEMALE, 240.0, null),
        RdaValue(AgeRange(14, 18), Sex.MALE, 410.0, null),
        RdaValue(AgeRange(14, 18), Sex.FEMALE, 360.0, null),
        RdaValue(AgeRange(19, 50), Sex.MALE, 420.0, null),
        RdaValue(AgeRange(19, 50), Sex.FEMALE, 320.0, null),
        RdaValue(AgeRange(51, Int.MAX_VALUE), Sex.MALE, 420.0, null),
        RdaValue(AgeRange(51, Int.MAX_VALUE), Sex.FEMALE, 320.0, null),
    )
    val phosphorusMg = listOf(
        RdaValue(AgeRange(9, 13), Sex.MALE, 1250.0, null),
        RdaValue(AgeRange(9, 13), Sex.FEMALE, 1250.0, null),
        RdaValue(AgeRange(14, 18), Sex.MALE, 1250.0, null),
        RdaValue(AgeRange(14, 18), Sex.FEMALE, 1250.0, null),
        RdaValue(AgeRange(19, 50), Sex.MALE, 700.0, null),
        RdaValue(AgeRange(19, 50), Sex.FEMALE, 700.0, null),
        RdaValue(AgeRange(51, Int.MAX_VALUE), Sex.MALE, 700.0, null),
        RdaValue(AgeRange(51, Int.MAX_VALUE), Sex.FEMALE, 700.0, null),
    )

    // === Microelements ===
    val ironMg = listOf(
        RdaValue(AgeRange(9, 13), Sex.MALE, 8.0, null),
        RdaValue(AgeRange(9, 13), Sex.FEMALE, 8.0, null),
        RdaValue(AgeRange(14, 18), Sex.MALE, 11.0, null),
        RdaValue(AgeRange(14, 18), Sex.FEMALE, 15.0, null),
        RdaValue(AgeRange(19, 50), Sex.MALE, 8.0, null),
        RdaValue(AgeRange(19, 50), Sex.FEMALE, 18.0, null),
        RdaValue(AgeRange(51, Int.MAX_VALUE), Sex.MALE, 8.0, null),
        RdaValue(AgeRange(51, Int.MAX_VALUE), Sex.FEMALE, 8.0, null),
    )
    val zincMg = listOf(
        RdaValue(AgeRange(9, 13), Sex.MALE, 8.0, null),
        RdaValue(AgeRange(9, 13), Sex.FEMALE, 8.0, null),
        RdaValue(AgeRange(14, 18), Sex.MALE, 11.0, null),
        RdaValue(AgeRange(14, 18), Sex.FEMALE, 9.0, null),
        RdaValue(AgeRange(19, 50), Sex.MALE, 11.0, null),
        RdaValue(AgeRange(19, 50), Sex.FEMALE, 8.0, null),
        RdaValue(AgeRange(51, Int.MAX_VALUE), Sex.MALE, 11.0, null),
        RdaValue(AgeRange(51, Int.MAX_VALUE), Sex.FEMALE, 8.0, null),
    )
    val seleniumMcg = listOf(
        RdaValue(AgeRange(9, 13), Sex.MALE, 40.0, null),
        RdaValue(AgeRange(9, 13), Sex.FEMALE, 40.0, null),
        RdaValue(AgeRange(14, 18), Sex.MALE, 55.0, null),
        RdaValue(AgeRange(14, 18), Sex.FEMALE, 55.0, null),
        RdaValue(AgeRange(19, 50), Sex.MALE, 55.0, null),
        RdaValue(AgeRange(19, 50), Sex.FEMALE, 55.0, null),
        RdaValue(AgeRange(51, Int.MAX_VALUE), Sex.MALE, 55.0, null),
        RdaValue(AgeRange(51, Int.MAX_VALUE), Sex.FEMALE, 55.0, null),
    )
    val copperMg = listOf(
        RdaValue(AgeRange(9, 13), Sex.MALE, 0.7, null),
        RdaValue(AgeRange(9, 13), Sex.FEMALE, 0.7, null),
        RdaValue(AgeRange(14, 18), Sex.MALE, 0.89, null),
        RdaValue(AgeRange(14, 18), Sex.FEMALE, 0.89, null),
        RdaValue(AgeRange(19, 50), Sex.MALE, 0.9, null),
        RdaValue(AgeRange(19, 50), Sex.FEMALE, 0.9, null),
        RdaValue(AgeRange(51, Int.MAX_VALUE), Sex.MALE, 0.9, null),
        RdaValue(AgeRange(51, Int.MAX_VALUE), Sex.FEMALE, 0.9, null),
    )
    val manganeseMg = listOf(
        RdaValue(AgeRange(9, 13), Sex.MALE, 1.9, null),
        RdaValue(AgeRange(9, 13), Sex.FEMALE, 1.6, null),
        RdaValue(AgeRange(14, 18), Sex.MALE, 2.2, null),
        RdaValue(AgeRange(14, 18), Sex.FEMALE, 1.6, null),
        RdaValue(AgeRange(19, 50), Sex.MALE, 2.3, null),
        RdaValue(AgeRange(19, 50), Sex.FEMALE, 1.8, null),
        RdaValue(AgeRange(51, Int.MAX_VALUE), Sex.MALE, 2.3, null),
        RdaValue(AgeRange(51, Int.MAX_VALUE), Sex.FEMALE, 1.8, null),
    )

    // === Vitamins (B-complex) ===
    val vitaminB1Mg = listOf(
        RdaValue(AgeRange(9, 13), Sex.MALE, 0.9, null),
        RdaValue(AgeRange(9, 13), Sex.FEMALE, 0.9, null),
        RdaValue(AgeRange(14, 18), Sex.MALE, 1.2, null),
        RdaValue(AgeRange(14, 18), Sex.FEMALE, 1.0, null),
        RdaValue(AgeRange(19, 50), Sex.MALE, 1.2, null),
        RdaValue(AgeRange(19, 50), Sex.FEMALE, 1.1, null),
        RdaValue(AgeRange(51, Int.MAX_VALUE), Sex.MALE, 1.2, null),
        RdaValue(AgeRange(51, Int.MAX_VALUE), Sex.FEMALE, 1.1, null),
    )
    val vitaminB2Mg = listOf(
        RdaValue(AgeRange(9, 13), Sex.MALE, 0.9, null),
        RdaValue(AgeRange(9, 13), Sex.FEMALE, 0.9, null),
        RdaValue(AgeRange(14, 18), Sex.MALE, 1.3, null),
        RdaValue(AgeRange(14, 18), Sex.FEMALE, 1.0, null),
        RdaValue(AgeRange(19, 50), Sex.MALE, 1.3, null),
        RdaValue(AgeRange(19, 50), Sex.FEMALE, 1.1, null),
        RdaValue(AgeRange(51, Int.MAX_VALUE), Sex.MALE, 1.3, null),
        RdaValue(AgeRange(51, Int.MAX_VALUE), Sex.FEMALE, 1.1, null),
    )
    val vitaminB3Mg = listOf(
        RdaValue(AgeRange(9, 13), Sex.MALE, 12.0, null),
        RdaValue(AgeRange(9, 13), Sex.FEMALE, 12.0, null),
        RdaValue(AgeRange(14, 18), Sex.MALE, 16.0, null),
        RdaValue(AgeRange(14, 18), Sex.FEMALE, 14.0, null),
        RdaValue(AgeRange(19, 50), Sex.MALE, 16.0, null),
        RdaValue(AgeRange(19, 50), Sex.FEMALE, 14.0, null),
        RdaValue(AgeRange(51, Int.MAX_VALUE), Sex.MALE, 16.0, null),
        RdaValue(AgeRange(51, Int.MAX_VALUE), Sex.FEMALE, 14.0, null),
    )
    val vitaminB5Mg = listOf(
        RdaValue(AgeRange(9, 13), Sex.MALE, 4.0, null),
        RdaValue(AgeRange(9, 13), Sex.FEMALE, 4.0, null),
        RdaValue(AgeRange(14, 18), Sex.MALE, 5.0, null),
        RdaValue(AgeRange(14, 18), Sex.FEMALE, 5.0, null),
        RdaValue(AgeRange(19, 50), Sex.MALE, 5.0, null),
        RdaValue(AgeRange(19, 50), Sex.FEMALE, 5.0, null),
        RdaValue(AgeRange(51, Int.MAX_VALUE), Sex.MALE, 5.0, null),
        RdaValue(AgeRange(51, Int.MAX_VALUE), Sex.FEMALE, 5.0, null),
    )
    val vitaminB6Mg = listOf(
        RdaValue(AgeRange(9, 13), Sex.MALE, 1.0, null),
        RdaValue(AgeRange(9, 13), Sex.FEMALE, 1.0, null),
        RdaValue(AgeRange(14, 18), Sex.MALE, 1.3, null),
        RdaValue(AgeRange(14, 18), Sex.FEMALE, 1.2, null),
        RdaValue(AgeRange(19, 50), Sex.MALE, 1.3, null),
        RdaValue(AgeRange(19, 50), Sex.FEMALE, 1.3, null),
        RdaValue(AgeRange(51, Int.MAX_VALUE), Sex.MALE, 1.7, null),
        RdaValue(AgeRange(51, Int.MAX_VALUE), Sex.FEMALE, 1.5, null),
    )
    val vitaminB12Mcg = listOf(
        RdaValue(AgeRange(9, 13), Sex.MALE, 1.8, null),
        RdaValue(AgeRange(9, 13), Sex.FEMALE, 1.8, null),
        RdaValue(AgeRange(14, 18), Sex.MALE, 2.4, null),
        RdaValue(AgeRange(14, 18), Sex.FEMALE, 2.4, null),
        RdaValue(AgeRange(19, 50), Sex.MALE, 2.4, null),
        RdaValue(AgeRange(19, 50), Sex.FEMALE, 2.4, null),
        RdaValue(AgeRange(51, Int.MAX_VALUE), Sex.MALE, 2.4, null),
        RdaValue(AgeRange(51, Int.MAX_VALUE), Sex.FEMALE, 2.4, null),
    )
    // === Vitamins (fat-soluble) ===
    val vitaminAMcg = listOf(
        RdaValue(AgeRange(9, 13), Sex.MALE, 600.0, null),
        RdaValue(AgeRange(9, 13), Sex.FEMALE, 600.0, null),
        RdaValue(AgeRange(14, 18), Sex.MALE, 900.0, null),
        RdaValue(AgeRange(14, 18), Sex.FEMALE, 700.0, null),
        RdaValue(AgeRange(19, 50), Sex.MALE, 900.0, null),
        RdaValue(AgeRange(19, 50), Sex.FEMALE, 700.0, null),
        RdaValue(AgeRange(51, Int.MAX_VALUE), Sex.MALE, 900.0, null),
        RdaValue(AgeRange(51, Int.MAX_VALUE), Sex.FEMALE, 700.0, null),
    )
    val vitaminDMcg = listOf(
        RdaValue(AgeRange(9, 13), Sex.MALE, 5.0, null),
        RdaValue(AgeRange(9, 13), Sex.FEMALE, 5.0, null),
        RdaValue(AgeRange(14, 18), Sex.MALE, 5.0, null),
        RdaValue(AgeRange(14, 18), Sex.FEMALE, 5.0, null),
        RdaValue(AgeRange(19, 50), Sex.MALE, 5.0, null),
        RdaValue(AgeRange(19, 50), Sex.FEMALE, 5.0, null),
        RdaValue(AgeRange(51, Int.MAX_VALUE), Sex.MALE, 10.0, null),
        RdaValue(AgeRange(51, Int.MAX_VALUE), Sex.FEMALE, 10.0, null),
    )
    val vitaminEMg = listOf(
        RdaValue(AgeRange(9, 13), Sex.MALE, 11.0, null),
        RdaValue(AgeRange(9, 13), Sex.FEMALE, 11.0, null),
        RdaValue(AgeRange(14, 18), Sex.MALE, 15.0, null),
        RdaValue(AgeRange(14, 18), Sex.FEMALE, 15.0, null),
        RdaValue(AgeRange(19, 50), Sex.MALE, 15.0, null),
        RdaValue(AgeRange(19, 50), Sex.FEMALE, 15.0, null),
        RdaValue(AgeRange(51, Int.MAX_VALUE), Sex.MALE, 15.0, null),
        RdaValue(AgeRange(51, Int.MAX_VALUE), Sex.FEMALE, 15.0, null),
    )
    val vitaminKMcg = listOf(
        RdaValue(AgeRange(9, 13), Sex.MALE, 60.0, null),
        RdaValue(AgeRange(9, 13), Sex.FEMALE, 60.0, null),
        RdaValue(AgeRange(14, 18), Sex.MALE, 75.0, null),
        RdaValue(AgeRange(14, 18), Sex.FEMALE, 75.0, null),
        RdaValue(AgeRange(19, 50), Sex.MALE, 120.0, null),
        RdaValue(AgeRange(19, 50), Sex.FEMALE, 90.0, null),
        RdaValue(AgeRange(51, Int.MAX_VALUE), Sex.MALE, 120.0, null),
        RdaValue(AgeRange(51, Int.MAX_VALUE), Sex.FEMALE, 90.0, null),
    )
    // === Vitamin C ===
    val vitaminCMg = listOf(
        RdaValue(AgeRange(9, 13), Sex.MALE, 45.0, null),
        RdaValue(AgeRange(9, 13), Sex.FEMALE, 45.0, null),
        RdaValue(AgeRange(14, 18), Sex.MALE, 75.0, null),
        RdaValue(AgeRange(14, 18), Sex.FEMALE, 65.0, null),
        RdaValue(AgeRange(19, 50), Sex.MALE, 90.0, null),
        RdaValue(AgeRange(19, 50), Sex.FEMALE, 75.0, null),
        RdaValue(AgeRange(51, Int.MAX_VALUE), Sex.MALE, 90.0, null),
        RdaValue(AgeRange(51, Int.MAX_VALUE), Sex.FEMALE, 75.0, null),
    )

    // === Folate ===
    val folateTotalMcg = listOf<RdaValue>()
    val folateFoodMcg = listOf<RdaValue>()
    val folicAcidMcg = listOf(
        RdaValue(AgeRange(9, 13), Sex.MALE, 300.0, null),
        RdaValue(AgeRange(9, 13), Sex.FEMALE, 300.0, null),
        RdaValue(AgeRange(14, 18), Sex.MALE, 400.0, null),
        RdaValue(AgeRange(14, 18), Sex.FEMALE, 400.0, null),
        RdaValue(AgeRange(19, 50), Sex.MALE, 400.0, null),
        RdaValue(AgeRange(19, 50), Sex.FEMALE, 400.0, null),
        RdaValue(AgeRange(51, Int.MAX_VALUE), Sex.MALE, 400.0, null),
        RdaValue(AgeRange(51, Int.MAX_VALUE), Sex.FEMALE, 400.0, null),
    )
    
    // === Active elements ===
    val caffeineMg = listOf<RdaValue>() 
    val cholineMg = listOf(
        RdaValue(AgeRange(9, 13), Sex.MALE, 375.0, null),
        RdaValue(AgeRange(9, 13), Sex.FEMALE, 375.0, null),
        RdaValue(AgeRange(14, 18), Sex.MALE, 550.0, null),
        RdaValue(AgeRange(14, 18), Sex.FEMALE, 400.0, null),
        RdaValue(AgeRange(19, 50), Sex.MALE, 550.0, null),
        RdaValue(AgeRange(19, 50), Sex.FEMALE, 425.0, null),
        RdaValue(AgeRange(51, Int.MAX_VALUE), Sex.MALE, 550.0, null),
        RdaValue(AgeRange(51, Int.MAX_VALUE), Sex.FEMALE, 425.0, null),
    )
    
    // === Antioxidants ===
    val luteinZeaxanthinMcg = listOf<RdaValue>() 
    val epaMg = listOf<RdaValue>() 
    val dhaMg = listOf<RdaValue>() 
    
    // === Other components ===
    val waterG = listOf<RdaValue>() 
    val alcoholG = listOf<RdaValue>() 
    val ashG = listOf<RdaValue>() 
}