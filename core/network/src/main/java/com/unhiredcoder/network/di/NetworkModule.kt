package com.unhiredcoder.network.di

import com.unhiredcoder.network.getRetrofitClient
import org.koin.dsl.module

val networkModule = module {
    single {
        getRetrofitClient()
    }
}