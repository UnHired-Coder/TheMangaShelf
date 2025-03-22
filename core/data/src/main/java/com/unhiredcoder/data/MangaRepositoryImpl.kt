package com.unhiredcoder.data

import com.unhiredcoder.data.model.mapToMangaDomainModel
import com.unhiredcoder.data.model.mapToMangaDomainUiModel
import com.unhiredcoder.data.model.mapToMangaEntity
import com.unhiredcoder.database.local.MangaLocal
import com.unhiredcoder.domain.MangaRepository
import com.unhiredcoder.domain.model.MangaDomainModel
import com.unhiredcoder.network.remote.MangaRemote
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MangaRepositoryImpl(
    private val mangaRemote: MangaRemote,
    private val mangaLocal: MangaLocal
) : MangaRepository {

    private val localMangaFlow = mangaLocal.getMangaList()

    override fun getMangaById(mangaId: String): Flow<MangaDomainModel?> {
        return mangaLocal.getMangaById(mangaId = mangaId).map { it?.mapToMangaDomainModel() }
    }

    override fun getMangaList(): Flow<List<MangaDomainModel>> {
        return localMangaFlow.map { mangaList ->
            mangaList.map { mangaEntity ->
                mangaEntity.mapToMangaDomainModel()
            }
        }
    }

    override fun getMangaListRemote(): Flow<List<MangaDomainModel>> {
        return mangaRemote.getMangaList().map { mangaList ->
            mangaList.map { mangaResponse ->
                mangaResponse.mapToMangaDomainUiModel()
            }
        }
    }


    override suspend fun refreshMangaList(mangaDataModels: List<MangaDomainModel>) {
        mangaDataModels.map { it.mapToMangaEntity() }.let {
            mangaLocal.updateMangaList(it)
        }
    }

    override suspend fun markFavorite(id: String): Boolean {
        return mangaLocal.markMangaFavourite(id)
    }
}