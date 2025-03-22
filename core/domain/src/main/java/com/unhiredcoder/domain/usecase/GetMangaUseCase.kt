package com.unhiredcoder.domain.usecase

import com.unhiredcoder.domain.MangaRepository
import com.unhiredcoder.domain.model.MangaDomainModel
import kotlinx.coroutines.flow.Flow

class GetMangaUseCase(private val mangaRepository: MangaRepository) {
    operator fun invoke(mangaId: String): Flow<MangaDomainModel?> {
        return mangaRepository.getMangaById(mangaId)
    }
}