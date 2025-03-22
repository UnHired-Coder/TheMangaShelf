package com.unhiredcoder.listmanga.data.local

import com.unhiredcoder.database.dao.MangaDao
import com.unhiredcoder.database.model.MangaEntity
import com.unhiredcoder.listmanga.data.IMangaC
import kotlinx.coroutines.flow.Flow


class MangaLocal(private val mangaDao: MangaDao) : IMangaC.Local {
    override suspend fun markMangaFavourite(mangaId: String):Boolean {
        return mangaDao.markMangaFavourite(mangaId = mangaId) > 0
    }

    override suspend fun updateMangaList(mangas: List<MangaEntity>) {
        mangaDao.updateMangaList(mangas)
    }

    override fun getMangaList(): Flow<List<MangaEntity>> {
        return mangaDao.getMangaList()
    }
}