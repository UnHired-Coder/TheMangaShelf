package com.unhiredcoder.listmanga.ui.model

import com.unhiredcoder.domain.model.MangaDomainModel
import com.unhiredcoder.ui.toReadableDate

data class MangaGroupWithIndex(
    val mangaUiModelMapByDates: Map<String, List<MangaUiModel>>,
    val pillPosToFirstMangaPos: Map<Int, Int>
)

data class MangaUiModel(
    val id: String,
    val imageUrl: String,
    val score: Double,
    val popularity: Long,
    val title: String,
    val publishedChapterDate: String,
    val category: String,
    val isFavourite: Boolean,
)

fun MangaDomainModel.mapToMangaUiModel(): MangaUiModel {
    return MangaUiModel(
        id = id,
        imageUrl = imageUrl,
        score = score,
        popularity = popularity,
        title = title,
        publishedChapterDate = publishedChapterDate.toReadableDate(),
        category = category,
        isFavourite = isFavourite,
    )
}


fun List<MangaUiModel>.mapToMangaGroupWithIndex(): MangaGroupWithIndex {
    val mangaMapByDates = groupBy { it.publishedChapterDate }
    var firstMangaIndex = 0
    var dateIndex = 0
    val datesMap: MutableMap<Int, Int> = mutableMapOf()

    mangaMapByDates.map { (_, u) ->
        datesMap += mapOf(dateIndex to firstMangaIndex)
        dateIndex++
        firstMangaIndex += u.size + 1
    }

    return MangaGroupWithIndex(
        mangaUiModelMapByDates = mangaMapByDates,
        datesMap
    )
}