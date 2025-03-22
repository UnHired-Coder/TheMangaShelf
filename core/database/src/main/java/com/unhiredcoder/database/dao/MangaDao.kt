package com.unhiredcoder.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.unhiredcoder.database.model.MangaEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MangaDao {
    @Upsert
    suspend fun updateMangaList(mangas: List<MangaEntity>): List<Long>

    @Query("SELECT * FROM manga_entity")
    fun getMangaList(): Flow<List<MangaEntity>>

    @Query("UPDATE manga_entity SET isFavourite = NOT isFavourite WHERE id = :mangaId")
    suspend fun markMangaFavourite(mangaId: String): Int
}