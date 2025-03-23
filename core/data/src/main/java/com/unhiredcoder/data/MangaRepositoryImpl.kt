package com.unhiredcoder.data

import com.unhiredcoder.data.model.mapToMangaDomainModel
import com.unhiredcoder.data.model.mapToMangaDomainUiModel
import com.unhiredcoder.data.model.mapToMangaEntity
import com.unhiredcoder.database.local.MangaLocal
import com.unhiredcoder.domain.MangaRepository
import com.unhiredcoder.domain.model.MangaDomainModel
import com.unhiredcoder.network.remote.MangaRemote
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapLatest

class MangaRepositoryImpl(
    private val mangaRemote: MangaRemote,
    private val mangaLocal: MangaLocal
) : MangaRepository {

    private val localMangaFlow = mangaLocal.getMangaList()

    override fun getMangaById(mangaId: String): Flow<MangaDomainModel?> {
        return mangaLocal.getMangaById(mangaId = mangaId).map { it?.mapToMangaDomainModel() }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getMangaList(): Flow<List<MangaDomainModel>> {
        return localMangaFlow
            .mapLatest { mangaList ->
                mangaList.toSet().map { mangaEntity ->
                    mangaEntity.mapToMangaDomainModel()
                }
            }.distinctUntilChanged()
    }


    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getMangaListRemote(): Flow<List<MangaDomainModel>> {
        return mangaRemote.getMangaList()
            .mapLatest { mangaList ->
                mangaList.toSet().map { mangaResponse ->
                    mangaResponse.mapToMangaDomainUiModel()
                }
            }.distinctUntilChanged()
    }


    override suspend fun refreshMangaList(mangaDataModels: List<MangaDomainModel>) {
        mangaDataModels.map { it.mapToMangaEntity() }.let {
            mangaLocal.updateMangaList(it)
        }
    }

    override suspend fun markFavorite(id: String): Boolean {
        return mangaLocal.markMangaFavourite(id)
    }

    override suspend fun markAsRead(id: String): Boolean {
        return mangaLocal.markAsRead(id)
    }
}