package com.unhiredcoder.database.di

import com.unhiredcoder.database.MangaAppDatabase
import com.unhiredcoder.database.dao.MangaDao
import com.unhiredcoder.database.getMangaAppDatabase
import org.koin.dsl.module

val databaseModule = module {
    single<MangaAppDatabase> {
        getMangaAppDatabase(get())
    }
    single<MangaDao> {
        get<MangaAppDatabase>().mangaDao()
    }
}