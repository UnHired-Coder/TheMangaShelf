package com.unhiredcoder.database.local

import com.unhiredcoder.database.model.MangaEntity
import kotlinx.coroutines.flow.Flow

interface MangaLocal {
    fun getMangaList(): Flow<List<MangaEntity>>
    suspend fun updateMangaList(mangas: List<MangaEntity>)
    suspend fun markMangaFavourite(mangaId: String): Boolean
}
