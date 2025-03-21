package com.unhiredcoder.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("manga_entity")
data class MangaEntity(
    @PrimaryKey
    val id: String,
    val imageUrl: String,
    val score: Double,
    val popularity: Long,
    val title: String,
    val publishedChapterDate: Long,
    val category: String,
    val isFavourite: Boolean = false,
    val isReadByUser: Boolean = false
)