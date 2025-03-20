package com.unhiredcoder.core.di

import com.unhiredcoder.core.RetrofitClientProvider
import org.koin.dsl.module

val networkModule = module {
    single {
        RetrofitClientProvider.getRetrofitClient()
    }
}