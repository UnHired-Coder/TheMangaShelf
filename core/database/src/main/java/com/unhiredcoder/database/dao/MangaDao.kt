package com.unhiredcoder.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.unhiredcoder.database.model.MangaEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MangaDao {
    @Upsert
    suspend fun updateMangaList(mangas: List<MangaEntity>): Long

    @Query("SELECT * FROM manga_entity")
    fun getMangaList(): Flow<List<MangaEntity>>

    @Upsert
    suspend fun updateMangaItem(mangaId: String, manga: MangaEntity): Int
}