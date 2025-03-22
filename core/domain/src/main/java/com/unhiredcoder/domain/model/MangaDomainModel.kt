package com.unhiredcoder.domain.model


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