package com.unhiredcoder.listmanga.ui.model

import com.unhiredcoder.listmanga.domain.model.MangaModel

//select date -> scroll to ui index
//scroll -> update the index on UI

data class MangaGroupWithIndex(
    val mangaMap: Map<String, List<Manga>>,
    val mangaPosToPillPos: Map<Int, Int>,
    val pillPosToMangaPos: Map<Int, Int>
)

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


fun List<Manga>.mapToMangaGroupWithIndex(): MangaGroupWithIndex {
    val map = sortedBy { it.publishedChapterDate }.groupBy { it.publishedChapterDate }
    return MangaGroupWithIndex(
        mangaMap = map,
        mapOf(),
        mapOf()
    )
}