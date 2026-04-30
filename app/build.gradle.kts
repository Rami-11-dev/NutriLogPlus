import com.android.build.gradle.internal.packaging.createDefaultDebugStore


plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)

    // My plugins
    alias(libs.plugins.koin.compiler)
    alias(libs.plugins.ksp)
    alias(libs.plugins.serialization)
}



android {
    namespace = "com.saliery.nutrilog"
    compileSdk {
        version = release(36) {
            minorApiLevel = 1
        }
    }

    defaultConfig {
        applicationId = "com.saliery.nutrilog"
        minSdk = 26
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        vectorDrawables.useSupportLibrary = true
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
}



dependencies {
    implementation(libs.androidx.camera.lifecycle)
    implementation(libs.androidx.camera.view)
    implementation(libs.androidx.camera.camera2)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.junit.ktx)
    implementation(libs.androidx.espresso.core)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.navigation.runtime.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)

    // KOIN
    implementation(libs.koin.core)
    implementation(libs.koin.annotations)  // KSP version (separate versioning)
    implementation(libs.koin.compose)
    implementation(libs.koin.android)
    implementation(libs.koin.test)
    implementation(libs.koin.compose.viewmodel)
    // ROOM DATABASE
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    implementation(libs.room.paging)
    ksp(libs.room.compiler)
    // TIMBER
    implementation(libs.timber)
    // RETROFIT2
    implementation(libs.retrofit)
    implementation(libs.retrofit.gson)
    // DATASTORE
    implementation(libs.datastore)
    implementation(libs.datastore.preferences)
    // COROUTINES
    implementation(libs.coroutines)
    testImplementation(libs.coroutines.test)
    // SERIALIZATION
    implementation(libs.serialization)
    // MOCKITO
    //testImplementation(libs.mockito)
    //testImplementation(libs.mockito.inline)
    testImplementation(libs.mockito.kotlin)
    // MOCKK
    testImplementation(libs.mockk)
    // TURBINE
    testImplementation(libs.turbine)
    // KOTLIN-TEST
    testImplementation(libs.kotlin.test)
    // JUNIT
    testImplementation(libs.junit.jupiter.api)
    testRuntimeOnly(libs.junit.jupiter.engine)
    // COIL
    implementation(libs.coil.compose)
    implementation(libs.coil.network.okhttp)
    // ML Kit
    implementation(libs.camera.core)
    implementation(libs.mlkit.barcode)
    // HAZE
    implementation(libs.haze)
    // MATERIAL ICONS
    implementation(libs.material.icons.core)
    // KMP-DATETIME-PICKER
    implementation(libs.kmp.datetime.picker)
}

koinCompiler {
    userLogs = true  // Log component detection
}





