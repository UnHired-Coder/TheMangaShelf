package com.unhiredcoder.data.model

import com.unhiredcoder.database.model.MangaEntity
import com.unhiredcoder.domain.model.MangaDomainModel
import com.unhiredcoder.network.model.MangaResponse

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

fun MangaResponse.mapToMangaEntity(): MangaEntity {
    validateMangaResponse()
    return MangaEntity(
        id = id!!,
        imageUrl = imageUrl!!,
        score = score!!,
        popularity = popularity!!,
        title = title!!,
        publishedChapterDate = publishedChapterDate!!,
        category = category!!,
        isFavourite = false,
        isReadByUser = false
    )
}

fun MangaDomainModel.mapToMangaEntity(): MangaEntity {
    return MangaEntity(
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

fun MangaEntity.mapToMangaDomainModel(): MangaDomainModel {
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

fun MangaResponse.mapToMangaDomainUiModel(): MangaDomainModel {
    validateMangaResponse()
    return MangaDomainModel(
        id = id!!,
        imageUrl = imageUrl!!,
        score = score!!,
        popularity = popularity!!,
        title = title!!,
        publishedChapterDate = publishedChapterDate!!,
        category = category!!,
        isFavourite = false,
        isReadByUser = false
    )
}
