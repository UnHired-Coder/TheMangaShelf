package com.unhiredcoder.listmanga.domain

import com.unhiredcoder.listmanga.data.IMangaC
import com.unhiredcoder.listmanga.domain.model.MangaDomainModel
import com.unhiredcoder.listmanga.domain.model.mapToMangaDomainModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetMangaListUseCase(private val mangaRepository: IMangaC.Repository) {
    operator fun invoke(): Flow<List<MangaDomainModel>> {
        return mangaRepository.getMangaListLocal().map { mangaModelsList ->
            mangaModelsList.sortedByDescending { it.publishedChapterDate }.map { mangaModel ->
                mangaModel.mapToMangaDomainModel()
            }
        }
    }
}