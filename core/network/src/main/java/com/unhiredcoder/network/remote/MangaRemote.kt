package com.unhiredcoder.network.remote

import com.unhiredcoder.network.model.MangaResponse
import kotlinx.coroutines.flow.Flow

interface MangaRemote {
    fun getMangaList(): Flow<List<MangaResponse>>
}
