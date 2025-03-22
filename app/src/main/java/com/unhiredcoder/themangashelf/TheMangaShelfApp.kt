package com.unhiredcoder.themangashelf

import android.app.Application
import com.unhiredcoder.di.di.commonModule
import com.unhiredcoder.di.di.databaseModule
import com.unhiredcoder.di.di.networkModule
import com.unhiredcoder.listmanga.di.mangaModule
import com.unhiredcoder.mangadetails.di.mangaDetailsModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class TheMangaShelfApp : Application() {
    override fun onCreate() {
        super.onCreate()
        setupKoinDI()
    }

    private fun setupKoinDI() {
        startKoin {
            androidContext(applicationContext)
            modules(
                commonModule,
                networkModule,
                databaseModule,
                mangaModule,
                mangaDetailsModule
            )
        }
    }
}