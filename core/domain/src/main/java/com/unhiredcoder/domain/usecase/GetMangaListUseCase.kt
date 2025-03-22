package com.unhiredcoder.domain.usecase

import com.unhiredcoder.domain.MangaRepository
import com.unhiredcoder.domain.model.MangaDomainModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetMangaListUseCase(private val mangaRepository: MangaRepository) {
    operator fun invoke(): Flow<List<MangaDomainModel>> {
        return mangaRepository.getMangaList().map { mangaModelsList ->
            mangaModelsList.sortedByDescending { it.publishedChapterDate }
        }
    }
}