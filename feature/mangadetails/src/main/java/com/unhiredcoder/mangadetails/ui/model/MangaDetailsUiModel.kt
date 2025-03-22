package com.unhiredcoder.mangadetails.ui.model

import com.unhiredcoder.domain.model.MangaDomainModel
import com.unhiredcoder.ui.toReadableDate
data class MangaDetailsUiModel(
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

fun MangaDomainModel.mapToMangaDetailsUiModel(): MangaDetailsUiModel {
    return MangaDetailsUiModel(
        id = id,
        imageUrl = imageUrl,
        score = score,
        popularity = popularity,
        title = title,
        publishedChapterDate = publishedChapterDate.toReadableDate(),
        category = category,
        isFavourite = isFavourite,
        isReadByUser = isReadByUser
    )
}