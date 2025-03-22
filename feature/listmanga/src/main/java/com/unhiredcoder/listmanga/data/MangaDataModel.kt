package com.unhiredcoder.listmanga.data

import com.unhiredcoder.database.model.MangaEntity

data class MangaDataModel(
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


fun MangaEntity.mapToMangaDataModel(): MangaDataModel {
    return MangaDataModel(
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