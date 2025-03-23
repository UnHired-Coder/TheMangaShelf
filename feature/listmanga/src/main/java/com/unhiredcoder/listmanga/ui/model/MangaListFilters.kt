package com.unhiredcoder.listmanga.ui.model

enum class MangaListFilters(val filterName: String) {
    SortByScore("Score"),
    SortByPopularity("Popularity"),
    SortByDate("Date");

    companion object {
        fun getDefaultFilter(): MangaListFilters = SortByDate
    }
}