package com.unhiredcoder.listmanga.ui

import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.unhiredcoder.common.data.Resource
import com.unhiredcoder.ui.ui.ScreenStateComposable
import com.unhiredcoder.listmanga.ui.composables.ListMangaSuccessUI
import com.unhiredcoder.listmanga.ui.model.ListMangaScreenActions
import com.unhiredcoder.listmanga.ui.model.ListMangaUiState
import kotlinx.coroutines.flow.StateFlow


@Composable
fun ListMangaScreen(
    modifier: Modifier = Modifier,
    listMangaUiStateFlow: StateFlow<Resource<ListMangaUiState>>,
    onListMangaScreenActions: (ListMangaScreenActions) -> Unit,
) {
    ScreenStateComposable(
        modifier = modifier.background(Color.Red),
        resourceFlow = listMangaUiStateFlow,
        onSuccessComposable = { listMangaUiState ->
            ListMangaSuccessUI(
                modifier = Modifier.background(Color.White),
                listMangaUiState = listMangaUiState,
                onListMangaScreenActions = onListMangaScreenActions
            )
        }
    )
}