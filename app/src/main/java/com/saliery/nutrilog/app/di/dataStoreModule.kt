package com.saliery.nutrilog.app.di


import com.saliery.nutrilog.app.data.local.preferences.UserPreferencesManager
import kotlinx.serialization.json.Json
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module


val dataStoreModule = module {

    single<Json> {
        Json {
            ignoreUnknownKeys = true
            encodeDefaults = true
        }
    }

    singleOf(::UserPreferencesManager)
}
