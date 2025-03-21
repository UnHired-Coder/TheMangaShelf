package com.unhiredcoder.listmanga.ui.model

sealed class ListMangaUiState {
    data object Idle : ListMangaUiState()
    data object Loading : ListMangaUiState()
    data class Failure(val errorMessage: String?) : ListMangaUiState()
    data class Success(
        val list: List<Manga> = listOf()
    ) : ListMangaUiState()
}