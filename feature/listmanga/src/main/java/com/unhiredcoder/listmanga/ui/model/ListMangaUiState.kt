package com.unhiredcoder.listmanga.ui.model


data class ListMangaUiState(
    val mangaGroupWithIndex: MangaGroupWithIndex,
    var isAutoScroll: Boolean = false,
    var isFilterActive: Boolean = false,
    var sortedBy: MangaListFilters = MangaListFilters.SortByDate,
    val selectedDateIndex: Int
)
