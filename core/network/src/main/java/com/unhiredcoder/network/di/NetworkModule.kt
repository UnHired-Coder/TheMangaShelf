package com.unhiredcoder.network.di

import com.unhiredcoder.network.RetrofitClientProvider
import org.koin.dsl.module

val networkModule = module {
    single {
        RetrofitClientProvider.getRetrofitClient()
    }
}