package com.unhiredcoder.listmanga.ui.model

import com.unhiredcoder.listmanga.domain.model.MangaModel
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

//select date -> scroll to ui index
//scroll -> update the index on UI

data class MangaGroupWithIndex(
    val mangaMapByDates: Map<String, List<Manga>>,
    val pillPosToFirstMangaPos: Map<Int, Int>
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
        publishedChapterDate = publishedChapterDate.let {
            val dateTime = Instant.fromEpochSeconds(publishedChapterDate!!)
                .toLocalDateTime(TimeZone.currentSystemDefault())
            val daySuffix = getDaySuffix(dateTime.dayOfMonth)
            val month = dateTime.month.name.lowercase()
                .replaceFirstChar { it.uppercase() } // Capitalize month
            val year = dateTime.year
            "${dateTime.dayOfMonth}$daySuffix $month, $year"
        },
        category = category,
        isFavourite = isFavourite,
        isReadByUser = isReadByUser
    )
}

private fun getDaySuffix(day: Int): String {
    return when {
        day in 11..13 -> "th"
        day % 10 == 1 -> "st"
        day % 10 == 2 -> "nd"
        day % 10 == 3 -> "rd"
        else -> "th"
    }
}

fun List<Manga>.mapToMangaGroupWithIndex(): MangaGroupWithIndex {
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
        mangaMapByDates = mangaMapByDates,
        datesMap
    )
}