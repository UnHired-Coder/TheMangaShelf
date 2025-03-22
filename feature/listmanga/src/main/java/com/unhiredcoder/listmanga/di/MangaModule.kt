package com.unhiredcoder.listmanga.di

import com.unhiredcoder.listmanga.data.IMangaC
import com.unhiredcoder.listmanga.data.MangaRepository
import com.unhiredcoder.listmanga.data.local.MangaLocal
import com.unhiredcoder.listmanga.data.remote.MangaRemote
import com.unhiredcoder.listmanga.data.remote.api.MangaWebService
import com.unhiredcoder.listmanga.domain.GetMangaListUseCase
import com.unhiredcoder.listmanga.domain.MarkMangaFavouriteUseCase
import com.unhiredcoder.listmanga.domain.SyncManagUseCase
import com.unhiredcoder.listmanga.ui.ListMangaViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.core.scope.get
import org.koin.dsl.module
import retrofit2.Retrofit

val mangaModule = module {
    factory<MangaWebService> {
        get<Retrofit>().create(MangaWebService::class.java)
    }

    factory<IMangaC.Local> {
        MangaLocal(get())
    }

    factory<IMangaC.Remote> {
        MangaRemote(get())
    }

    factory<IMangaC.Repository> {
        MangaRepository(get(), get())
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

    viewModelOf(::ListMangaViewModel)
}