package com.unhiredcoder.network.api

import com.unhiredcoder.network.model.MangaResponse
import retrofit2.http.GET

interface MangaWebService {
    @GET("KEJO/")
    suspend fun getMangaList(): List<MangaResponse>
}