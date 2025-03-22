package com.unhiredcoder.listmanga.data

import com.unhiredcoder.database.model.MangaEntity
import com.unhiredcoder.listmanga.data.remote.model.MangaResponse
import kotlinx.coroutines.flow.Flow

interface IMangaC {

    interface Repository {
        fun getMangaListLocal(): Flow<List<MangaDataModel>>
        suspend fun getMangaListRemote(): Flow<List<MangaDataModel>>
        fun updateManagaList(mangaDataModels: List<MangaDataModel>)
        suspend fun markMangaFavourite(mangaId: String): Boolean
    }

    interface Remote {
        fun getMangaList(): Flow<List<MangaResponse>>
    }

    interface Local {
        suspend fun updateMangaItem(manga: MangaEntity): Boolean
        suspend fun updateMangaList(mangas: List<MangaEntity>)
        fun getMangaList(): Flow<List<MangaEntity>>
    }
}