package com.unhiredcoder.ui.navigation


sealed class NavRoutes(val route: String) {
    data object ListManga : NavRoutes("list_manga")
    data object MangaDetails : NavRoutes("manga_details/{mangaId}") {
        fun createRoute(mangaId: String) = "manga_details/$mangaId"
    }
}