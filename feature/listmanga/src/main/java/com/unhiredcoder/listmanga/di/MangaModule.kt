package com.unhiredcoder.listmanga.di

import com.unhiredcoder.listmanga.ui.ListMangaViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val mangaModule = module {
    viewModelOf(::ListMangaViewModel)
}