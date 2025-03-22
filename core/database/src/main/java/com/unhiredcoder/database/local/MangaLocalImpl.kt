package com.unhiredcoder.database.local

import com.unhiredcoder.database.dao.MangaDao
import com.unhiredcoder.database.model.MangaEntity
import kotlinx.coroutines.flow.Flow


class MangaLocalImpl(private val mangaDao: MangaDao) : MangaLocal {
    override suspend fun markMangaFavourite(mangaId: String): Boolean {
        return mangaDao.markMangaFavourite(mangaId = mangaId) > 0
    }

    override suspend fun updateMangaList(mangas: List<MangaEntity>) {
        mangaDao.updateMangaList(mangas)
    }

    override fun getMangaList(): Flow<List<MangaEntity>> {
        return mangaDao.getMangaList()
    }
}