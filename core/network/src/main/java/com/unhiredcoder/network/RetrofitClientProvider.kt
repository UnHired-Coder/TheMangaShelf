package com.unhiredcoder.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit

private val json = Json {
    ignoreUnknownKeys = true
}

private const val CONTENT_TYPE = "application/json"

private val unsafeOkHttpClient: OkHttpClient = OkHttpClient.Builder()
    .hostnameVerifier { _, _ -> true } // Ignore SSL errors (DEBUG only!)
    .build()

@OptIn(ExperimentalSerializationApi::class)
fun getRetrofitClient(): Retrofit {
    return Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .client(unsafeOkHttpClient)
        .addConverterFactory(json.asConverterFactory(MediaType.parse(CONTENT_TYPE)!!))
        .build()
}
