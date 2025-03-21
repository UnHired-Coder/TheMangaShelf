package com.unhiredcoder.listmanga.data.remote

import com.unhiredcoder.listmanga.data.remote.api.MangaWebService
import com.unhiredcoder.listmanga.data.remote.model.MangaResponse
import com.unhiredcoder.listmanga.data.IMangaC
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MangaRemote(private val mangaWebService: MangaWebService) : IMangaC.Remote {
    override fun getMangaList(): Flow<List<MangaResponse>> {
        return flow {
            emit(mangaWebService.getMangaList())
        }
    }
}