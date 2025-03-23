package com.unhiredcoder.listmanga.ui.model

sealed interface ListMangaScreenActions {
    class OnDateSelected(val dateIndex: Int) : ListMangaScreenActions
    class OnSetAutoScroll(val set: Boolean) : ListMangaScreenActions
    class OnMarkFavourite(val mangaUiModel: MangaUiModel) : ListMangaScreenActions
    class OnDisplayManga(val mangaUiModel: MangaUiModel) : ListMangaScreenActions
    class OnScrollToIndex(val index: Int) : ListMangaScreenActions
    data object OnSortByScore : ListMangaScreenActions
    data object OnSortByPopularity : ListMangaScreenActions
    data object OnResetFilters : ListMangaScreenActions
}