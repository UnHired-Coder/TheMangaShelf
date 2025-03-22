package com.unhiredcoder.domain.usecase

import com.unhiredcoder.domain.MangaRepository


class MarkMangaFavouriteUseCase(private val mangaRepository: MangaRepository) {
    suspend operator fun invoke(mangaId: String){
        mangaRepository.markFavorite(mangaId)
    }
}