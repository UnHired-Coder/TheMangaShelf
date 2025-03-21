package com.unhiredcoder.network.di

import com.unhiredcoder.network.getRetrofitClient
import org.koin.dsl.module
import retrofit2.Retrofit

val networkModule = module {
    single<Retrofit> {
        getRetrofitClient()
    }
}