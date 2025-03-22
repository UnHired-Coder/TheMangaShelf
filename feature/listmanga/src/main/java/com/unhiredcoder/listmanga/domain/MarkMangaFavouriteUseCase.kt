package com.unhiredcoder.listmanga.domain

import com.unhiredcoder.listmanga.data.IMangaC

class MarkMangaFavouriteUseCase(private val mangaRepository: IMangaC.Repository) {
    suspend operator fun invoke(mangaId: String){
        mangaRepository.markMangaFavourite(mangaId)
    }
}