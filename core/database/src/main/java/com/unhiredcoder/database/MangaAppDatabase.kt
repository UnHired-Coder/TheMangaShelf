package com.unhiredcoder.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.unhiredcoder.database.dao.MangaDao
import com.unhiredcoder.database.model.MangaEntity

@Database(entities = [MangaEntity::class], version = 1)
abstract class MangaAppDatabase : RoomDatabase() {
    abstract fun mangaDao(): MangaDao
}