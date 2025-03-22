package com.unhiredcoder.domain

import com.unhiredcoder.domain.model.MangaDomainModel
import kotlinx.coroutines.flow.Flow

interface MangaRepository {
    fun getMangaById(mangaId: String): Flow<MangaDomainModel?>
    fun getMangaList(): Flow<List<MangaDomainModel>>
    fun getMangaListRemote(): Flow<List<MangaDomainModel>>
    suspend fun refreshMangaList(mangaDataModels: List<MangaDomainModel>)
    suspend fun markFavorite(id: String): Boolean
}