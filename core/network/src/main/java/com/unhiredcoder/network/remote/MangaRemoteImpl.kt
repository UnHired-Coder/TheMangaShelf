package com.unhiredcoder.network.remote

import com.unhiredcoder.network.api.MangaWebService
import com.unhiredcoder.network.model.MangaResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MangaRemoteImpl(private val mangaWebService: MangaWebService) : MangaRemote {
    override fun getMangaList(): Flow<List<MangaResponse>> {
        return flow {
            emit(mangaWebService.getMangaList())
        }
    }
}