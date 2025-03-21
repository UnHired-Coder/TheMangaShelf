package com.unhiredcoder.database

import android.app.Application
import androidx.room.Room

fun getMangaAppDatabase(applicationContext: Application): MangaAppDatabase {
    return Room.databaseBuilder(
        applicationContext,
        MangaAppDatabase::class.java, "manga-app-database"
    ).build()
}

