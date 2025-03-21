package com.unhiredcoder.listmanga.data

import com.unhiredcoder.database.model.MangaEntity
import com.unhiredcoder.listmanga.data.remote.model.MangaResponse
import com.unhiredcoder.listmanga.domain.model.MangaModel
import kotlinx.coroutines.flow.Flow

interface IMangaC {

    interface Repository {
        fun getMangaList(): Flow<List<MangaModel>>
        suspend fun markMangaFavourite(mangaId: String): Boolean
    }

    interface Remote {
        fun getMangaList(): Flow<List<MangaResponse>>
    }

    interface Local {
        suspend fun updateMangaItem(mangaId: String, manga: MangaEntity): Boolean
        suspend fun updateMangaList(mangas: List<MangaEntity>)
        fun getMangaList(): Flow<List<MangaEntity>>
    }
}