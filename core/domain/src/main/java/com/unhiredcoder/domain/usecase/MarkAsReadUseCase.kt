package com.unhiredcoder.domain.usecase

import com.unhiredcoder.domain.MangaRepository


class MarkAsReadUseCase(private val mangaRepository: MangaRepository) {
    suspend operator fun invoke(mangaId: String){
        mangaRepository.markAsRead(mangaId)
    }
}