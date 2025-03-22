package com.unhiredcoder.themangashelf

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import com.unhiredcoder.listmanga.ui.ListMangaScreen
import com.unhiredcoder.listmanga.ui.ListMangaViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val listMangaViewMode: ListMangaViewModel by viewModel()
        setContent {
            ListMangaScreen(
                modifier = Modifier.fillMaxSize(),
                listMangaUiStateFlow = listMangaViewMode.listMangaUiState,
                onDateSelected = listMangaViewMode::onDateSelected,
                onMarkFavourite = listMangaViewMode::markFavourite
            )
        }
    }
}
