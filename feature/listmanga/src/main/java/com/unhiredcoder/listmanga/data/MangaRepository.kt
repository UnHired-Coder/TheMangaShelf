package com.unhiredcoder.listmanga.data

import com.unhiredcoder.database.model.MangaEntity
import com.unhiredcoder.listmanga.data.remote.model.MangaResponse
import com.unhiredcoder.listmanga.data.remote.model.mapToMangaEntity
import com.unhiredcoder.listmanga.domain.model.MangaModel
import com.unhiredcoder.listmanga.domain.model.mapToMangaModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

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
            val remoteMangaEntity = remoteMangaItem.mapToMangaEntity()

            val localMangaEntity =
                localList.find { localMangaItem ->
                    localMangaItem.id == remoteMangaEntity.id
                } ?: remoteMangaEntity

            localMangaEntity.copy(
                imageUrl = remoteMangaEntity.imageUrl,
                title = remoteMangaEntity.title,
                publishedChapterDate = remoteMangaEntity.publishedChapterDate,
                score = remoteMangaEntity.score,
                popularity = remoteMangaEntity.popularity,
                category = remoteMangaEntity.category,
            )
        }.also { mergeEntities ->
            CoroutineScope(Dispatchers.IO).launch {
                mangaLocal.updateMangaList(mergeEntities)
            }
        }.map {
            it.mapToMangaModel()
        }
    }
}