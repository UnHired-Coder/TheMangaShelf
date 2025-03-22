package com.unhiredcoder.themangashelf

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.unhiredcoder.listmanga.ui.ListMangaScreen
import com.unhiredcoder.listmanga.ui.MangaViewModel
import com.unhiredcoder.mangadetails.ui.MangaDetailsScreen
import com.unhiredcoder.mangadetails.ui.MangaDetailsViewModel
import com.unhiredcoder.ui.navigation.NavRoutes
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val navController = rememberNavController()
            NavHost(
                navController = navController,
                startDestination = NavRoutes.ListManga.route
            ) {
                composable(NavRoutes.ListManga.route) {
                    val listMangaViewMode: MangaViewModel by viewModel()

                    ListMangaScreen(
                        modifier = Modifier.fillMaxSize(),
                        listMangaUiStateFlow = listMangaViewMode.listMangaUiState,
                        onDateSelected = listMangaViewMode::onDateSelected,
                        onMarkFavourite = listMangaViewMode::markFavourite,
                        onDisplayManga = { mangaUiModel ->
                            navController.navigate(NavRoutes.MangaDetails.createRoute(mangaUiModel.id))
                        }
                    )
                }

                composable(
                    NavRoutes.MangaDetails.route,
                    arguments = listOf(navArgument("mangaId") { type = NavType.StringType })
                ) { backStackEntry ->
                    val mangaDetailsViewModel: MangaDetailsViewModel by viewModel()
                    val mangaId = backStackEntry.arguments?.getString("mangaId") ?: ""
                    LaunchedEffect(Unit) {
                        mangaDetailsViewModel.getMangaDetails(mangaId)
                    }
                    MangaDetailsScreen(mangaDetailsUiStateFlow = mangaDetailsViewModel.mangaDetailsUiState) {
                        mangaDetailsViewModel.markFavourite(it)
                    }
                }
            }
        }
    }
}
