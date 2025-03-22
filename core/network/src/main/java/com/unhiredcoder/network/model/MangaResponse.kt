package com.unhiredcoder.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MangaResponse(
    val id: String?,
    @SerialName("image") val imageUrl: String?,
    val score: Double?,
    val popularity: Long?,
    val title: String?,
    val publishedChapterDate: Long?,
    val category: String?
) {
    fun MangaResponse.validateMangaResponse() {
        var invalidResponse: String? = null
        if (id == null) invalidResponse = "Invalid ID"

        if (imageUrl == null) invalidResponse = "Invalid imageUrl"

        if (score == null) invalidResponse = "Invalid score"

        if (popularity == null) invalidResponse = "Invalid popularity"

        if (title == null) invalidResponse = "Invalid title"

        if (publishedChapterDate == null) invalidResponse = "Invalid publishedChapterDate"

        if (category == null) invalidResponse = "Invalid category"

        invalidResponse?.let {
            throw Throwable(invalidResponse)
        }
    }
}