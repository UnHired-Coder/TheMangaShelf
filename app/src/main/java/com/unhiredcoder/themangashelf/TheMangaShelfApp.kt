package com.unhiredcoder.themangashelf

import android.app.Application
import com.unhiredcoder.database.di.databaseModule
import com.unhiredcoder.listmanga.di.mangaModule
import com.unhiredcoder.network.di.networkModule
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
                networkModule,
                databaseModule,
                mangaModule
            )
        }
    }
}