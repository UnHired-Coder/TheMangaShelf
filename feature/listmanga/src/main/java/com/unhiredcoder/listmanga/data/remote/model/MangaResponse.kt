package com.unhiredcoder.listmanga.data.remote.model

import com.unhiredcoder.database.model.MangaEntity
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MangaResponse(
    val id: String,
    @SerialName("image")
    val imageUrl: String,
    val score: Double,
    val popularity: Long,
    val title: String,
    val publishedChapterDate: Long,
    val category: String
)

fun MangaResponse.mapToMangaEntity(): MangaEntity {
    return MangaEntity(
        id = id,
        imageUrl = imageUrl,
        score = score,
        popularity = popularity,
        title = title,
        publishedChapterDate = publishedChapterDate.let {
            val dateTime = Instant.fromEpochSeconds(publishedChapterDate)
                .toLocalDateTime(TimeZone.currentSystemDefault())
            "${dateTime.dayOfMonth}-${dateTime.monthNumber}-${dateTime.year}"
        },
        category = category,
        isFavourite = false,
        isReadByUser = false
    )
}