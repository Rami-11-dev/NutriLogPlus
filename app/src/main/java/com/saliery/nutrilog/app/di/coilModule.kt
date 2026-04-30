package com.saliery.nutrilog.app.di

import coil3.ImageLoader
import coil3.request.CachePolicy
import coil3.request.crossfade
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val coilModule = module {

    single<ImageLoader> {
        ImageLoader.Builder(androidContext())
            .crossfade(true)
            .memoryCachePolicy(CachePolicy.ENABLED)
            .diskCachePolicy(CachePolicy.ENABLED)
            .build()
    }
}