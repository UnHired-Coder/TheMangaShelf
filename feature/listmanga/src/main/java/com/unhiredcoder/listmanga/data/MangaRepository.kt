package com.unhiredcoder.listmanga.data

import com.unhiredcoder.listmanga.data.remote.model.mapToMangaEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MangaRepository(
    private val mangaRemote: IMangaC.Remote,
    private val mangaLocal: IMangaC.Local
) : IMangaC.Repository {

    private val localMangaFlow = mangaLocal.getMangaList()

    override fun getMangaListLocal(): Flow<List<MangaDataModel>> {
        return localMangaFlow.map { mangaList ->
            mangaList.map { mangaEntity ->
                mangaEntity.mapToMangaDataModel()
            }
        }
    }

    override suspend fun getMangaListRemote(): Flow<List<MangaDataModel>> {
        return mangaRemote.getMangaList().map { mangaList ->
            mangaList.map { mangaResponse ->
                mangaResponse.mapToMangaEntity().mapToMangaDataModel()
            }
        }
    }

    override suspend fun updateManagaList(mangaDataModels: List<MangaDataModel>) {
        mangaDataModels.map { it.mapToMangaEntity() }.let {
            mangaLocal.updateMangaList(it)
        }
    }

    override suspend fun markMangaFavourite(mangaId: String): Boolean {
        return true
    }
}