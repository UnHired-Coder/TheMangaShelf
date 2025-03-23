package com.unhiredcoder.themangashelf

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding
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
                modifier = Modifier.systemBarsPadding(),
                navController = navController,
                startDestination = NavRoutes.ListManga.route
            ) {
                composable(
                    route = NavRoutes.ListManga.route,
                    enterTransition = {
                        slideIntoContainer(
                            towards =  AnimatedContentTransitionScope. SlideDirection.End,
                            animationSpec = tween(
                                durationMillis = 220,
                                delayMillis = 90
                            )
                        )
                    },
                    exitTransition = {
                        slideOutOfContainer(
                            towards =  AnimatedContentTransitionScope. SlideDirection.Start,
                            animationSpec = tween(
                                durationMillis = 220,
                                delayMillis = 90
                            )
                        )
                    }
                ) {
                    val listMangaViewMode: MangaViewModel by viewModel()

                    ListMangaScreen(
                        modifier = Modifier.fillMaxSize(),
                        listMangaUiStateFlow = listMangaViewMode.listMangaUiState,
                        onDateSelected = listMangaViewMode::onDateSelected,
                        onMarkFavourite = listMangaViewMode::markFavourite,
                        onSetAutoScroll = listMangaViewMode::onSetAutoScroll,
                        onScrollToIndex = listMangaViewMode::onScrollToIndex,
                        onDisplayManga = { mangaUiModel ->
                            navController.navigate(NavRoutes.MangaDetails.createRoute(mangaUiModel.id))
                        },
                        onSortByScore = listMangaViewMode::onSortByScore,
                        onSortByPopularity = listMangaViewMode::onSortByPopularity,
                        onResetFilters = listMangaViewMode::onResetFilters
                    )
                }

                composable(
                    route = NavRoutes.MangaDetails.route,
                    enterTransition = {
                        slideIntoContainer(
                            towards =  AnimatedContentTransitionScope. SlideDirection.Start,
                            animationSpec = tween(
                                durationMillis = 220,
                                delayMillis = 90
                            )
                        )
                    },
                    exitTransition = {
                        slideOutOfContainer(
                            towards =  AnimatedContentTransitionScope. SlideDirection.End,
                            animationSpec = tween(
                                durationMillis = 220,
                                delayMillis = 90
                            )
                        )
                    },
                    arguments = listOf(navArgument(NavRoutes.ARG_MANGA_ID) {
                        type = NavType.StringType
                    })
                ) { backStackEntry ->
                    val mangaDetailsViewModel: MangaDetailsViewModel by viewModel()
                    val mangaId = backStackEntry.arguments?.getString(NavRoutes.ARG_MANGA_ID) ?: ""
                    LaunchedEffect(Unit) {
                        mangaDetailsViewModel.getMangaDetails(mangaId)
                    }
                    MangaDetailsScreen(
                        mangaDetailsUiStateFlow = mangaDetailsViewModel.mangaDetailsUiState,
                        onMarkFavourite = mangaDetailsViewModel::markFavourite,
                        onMarkRead = mangaDetailsViewModel::markAsRead,
                    )
                }
            }
        }
    }
}
