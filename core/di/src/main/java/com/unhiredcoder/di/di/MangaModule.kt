package com.unhiredcoder.di.di

import com.unhiredcoder.data.MangaRepositoryImpl
import com.unhiredcoder.database.local.MangaLocalImpl
import com.unhiredcoder.database.local.MangaLocal
import com.unhiredcoder.domain.MangaRepository
import com.unhiredcoder.domain.usecase.GetMangaListUseCase
import com.unhiredcoder.domain.usecase.GetMangaUseCase
import com.unhiredcoder.domain.usecase.MarkMangaFavouriteUseCase
import com.unhiredcoder.domain.usecase.SyncManagUseCase
import com.unhiredcoder.network.api.MangaWebService
import com.unhiredcoder.network.remote.MangaRemoteImpl
import com.unhiredcoder.network.remote.MangaRemote
import org.koin.dsl.module
import retrofit2.Retrofit

val commonModule = module {
    factory<MangaWebService> {
        get<Retrofit>().create(MangaWebService::class.java)
    }

    factory<MangaLocal> {
        MangaLocalImpl(get())
    }

    factory<MangaRemote> {
        MangaRemoteImpl(get())
    }

    factory<MangaRepository> {
        MangaRepositoryImpl(get(), get())
    }

    factory<GetMangaListUseCase> {
        GetMangaListUseCase(get())
    }

    factory<SyncManagUseCase> {
        SyncManagUseCase(get())
    }

    factory<MarkMangaFavouriteUseCase> {
        MarkMangaFavouriteUseCase(get())
    }

    factory<GetMangaUseCase> {
        GetMangaUseCase(get())
    }
}