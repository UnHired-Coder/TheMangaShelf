package com.unhiredcoder.listmanga.ui.model

import com.unhiredcoder.listmanga.domain.model.MangaModel

data class Manga(
    val id: String,
    val imageUrl: String,
    val score: Double,
    val popularity: Long,
    val title: String,
    val publishedChapterDate: String,
    val category: String,
    val isFavourite: Boolean,
    val isReadByUser: Boolean
)

fun MangaModel.mapToManga(): Manga {
    return Manga(
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