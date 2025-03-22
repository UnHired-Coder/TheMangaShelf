package com.unhiredcoder.mangadetails.di

import com.unhiredcoder.mangadetails.ui.MangaDetailsViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val mangaDetailsModule = module {
    viewModel {
        MangaDetailsViewModel(get(), get())
    }
}