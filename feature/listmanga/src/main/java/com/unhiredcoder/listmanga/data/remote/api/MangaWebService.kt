package com.unhiredcoder.listmanga.data.remote.api

import com.unhiredcoder.listmanga.data.remote.model.MangaResponse
import retrofit2.http.GET

interface MangaWebService {
    @GET("KEJO/")
    suspend fun getMangaList(): List<MangaResponse>
}