package com.saliery.nutrilog.app.di

import com.saliery.nutrilog.app.data.remote.OpenFoodFactsApi
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val networkModule = module {

    single {

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .header("User-Agent", "NutriLog+ - Android - Version 1.0 - Contact: schmertzsoldaten@gmail.com")
                    .header("Accept", "application/json")
                    .build()
                chain.proceed(request)
            }
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()

        Retrofit.Builder()
            .baseUrl("https://world.openfoodfacts.net/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single<OpenFoodFactsApi> {
        get<Retrofit>().create(OpenFoodFactsApi::class.java)
    }
}