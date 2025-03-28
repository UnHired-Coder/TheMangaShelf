package com.unhiredcoder.listmanga.di

import com.unhiredcoder.listmanga.ui.MangaViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val mangaModule = module {
    viewModelOf(::MangaViewModel)
}