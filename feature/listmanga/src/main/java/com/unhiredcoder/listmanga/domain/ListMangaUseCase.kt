package com.unhiredcoder.listmanga.domain

import com.unhiredcoder.listmanga.data.IMangaC
import com.unhiredcoder.listmanga.ui.model.Manga
import com.unhiredcoder.listmanga.ui.model.mapToManga
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ListMangaUseCase(private val mangaRepository: IMangaC.Repository) {
    operator fun invoke(): Flow<List<Manga>> {
        return mangaRepository.getMangaList().map { mangaModelsList ->
            mangaModelsList.sortedByDescending { it.publishedChapterDate }.map { mangaModel ->
                mangaModel.mapToManga()
            }
        }
    }
}