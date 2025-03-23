package com.unhiredcoder.domain.usecase

import com.unhiredcoder.domain.MangaRepository
import com.unhiredcoder.domain.model.MangaDomainModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

class SyncManagUseCase(private val mangaRepository: MangaRepository) {
    operator fun invoke(): Flow<Unit> {
       return combine(
            mangaRepository.getMangaListRemote(),
            mangaRepository.getMangaList()
        ) { remoteMangasList, localMangasList ->
            mangaRepository.refreshMangaList(mergeMangaLists(remoteMangasList, localMangasList))
        }
    }

    private fun mergeMangaLists(
        remoteList: List<MangaDomainModel>,
        localList: List<MangaDomainModel>
    ): List<MangaDomainModel> {
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
