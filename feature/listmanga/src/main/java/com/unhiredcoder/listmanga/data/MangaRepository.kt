package com.unhiredcoder.listmanga.data

import com.unhiredcoder.database.model.MangaEntity
import com.unhiredcoder.listmanga.data.remote.model.MangaResponse
import com.unhiredcoder.listmanga.data.remote.model.mapToMangaModel
import com.unhiredcoder.listmanga.domain.model.MangaModel
import com.unhiredcoder.listmanga.domain.model.mapToMangaModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

class MangaRepository(
    private val mangaRemote: IMangaC.Remote,
    private val mangaLocal: IMangaC.Local
) : IMangaC.Repository {
    override fun getMangaList(): Flow<List<MangaModel>> {
        return combine(
            mangaRemote.getMangaList(),
            mangaLocal.getMangaList()
        ) { remoteMangasList, localMangasList ->
            mergeMangaLists(remoteMangasList, localMangasList)
        }
    }

    override suspend fun markMangaFavourite(mangaId: String): Boolean {
        return true
    }

    private fun mergeMangaLists(
        remoteList: List<MangaResponse>,
        localList: List<MangaEntity>
    ): List<MangaModel> {
        return remoteList.map { remoteMangaItem ->
            val remoteMangaModel = remoteMangaItem.mapToMangaModel()

            val localMangaItem =
                localList.find { localMangaItem ->
                    localMangaItem.id == remoteMangaModel.id
                }?.mapToMangaModel() ?: remoteMangaModel

            localMangaItem.copy(
                imageUrl = remoteMangaModel.imageUrl,
                title = remoteMangaModel.title,
                publishedChapterDate = remoteMangaModel.publishedChapterDate,
                score = remoteMangaModel.score,
                popularity = remoteMangaModel.popularity,
                category = remoteMangaModel.category,
            )
        }
    }
}