package com.unhiredcoder.database

import android.content.Context
import androidx.room.Room

fun getMangaAppDatabase(applicationContext: Context): MangaAppDatabase {
    return Room.databaseBuilder(
        applicationContext,
        MangaAppDatabase::class.java, "manga-app-database"
    ).build()
}

