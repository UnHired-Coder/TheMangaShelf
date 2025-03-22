package com.unhiredcoder.listmanga.domain

import com.unhiredcoder.listmanga.data.IMangaC
import com.unhiredcoder.listmanga.data.MangaDataModel
import kotlinx.coroutines.flow.combine

class SyncManagUseCase(private val mangaRepository: IMangaC.Repository) {
    suspend operator fun invoke() {
        combine(
            mangaRepository.getMangaListRemote(),
            mangaRepository.getMangaListLocal()
        ) { remoteMangasList, localMangasList ->
            mergeMangaLists(remoteMangasList, localMangasList)
        }.collect {
            mangaRepository.updateManagaList(it)
        }
    }

    private fun mergeMangaLists(
        remoteList: List<MangaDataModel>,
        localList: List<MangaDataModel>
    ): List<MangaDataModel> {
        return remoteList.map { remoteMangaEntity ->
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
        }
    }
}
