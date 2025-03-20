package com.unhiredcoder.core

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit

object RetrofitClientProvider {
    private val json = Json {
        ignoreUnknownKeys = true
    }

    private const val CONTENT_TYPE = "application/json"

    @OptIn(ExperimentalSerializationApi::class)
    fun getRetrofitClient(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(OkHttpClient())
            .addConverterFactory(json.asConverterFactory(MediaType.parse(CONTENT_TYPE)!!))
            .build()
    }
}
