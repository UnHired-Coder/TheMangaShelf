package com.unhiredcoder.listmanga.domain.model

import com.unhiredcoder.listmanga.data.MangaDataModel

data class MangaDomainModel(
    val id: String,
    val imageUrl: String,
    val score: Double,
    val popularity: Long,
    val title: String,
    val publishedChapterDate: Long,
    val category: String,
    val isFavourite: Boolean,
    val isReadByUser: Boolean
)

fun MangaDataModel.mapToMangaDomainModel(): MangaDomainModel {
    return MangaDomainModel(
        id = id,
        imageUrl = imageUrl,
        score = score,
        popularity = popularity,
        title = title,
        publishedChapterDate = publishedChapterDate,
        category = category,
        isFavourite = isFavourite,
        isReadByUser = isReadByUser
    )
}