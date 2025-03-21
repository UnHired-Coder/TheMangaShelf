package com.unhiredcoder.listmanga.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unhiredcoder.listmanga.domain.ListMangaUseCase
import com.unhiredcoder.listmanga.ui.model.ListMangaUiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn

class ListMangaViewModel(private val listMangaUseCase: ListMangaUseCase) : ViewModel() {
    val listMangaUiState: StateFlow<ListMangaUiState> = getMangaUiState().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = ListMangaUiState.Idle,
    )

    private fun getMangaUiState(): Flow<ListMangaUiState> {
        return listMangaUseCase().map {
            ListMangaUiState.Success(it)
        }.onStart {
            ListMangaUiState.Loading
        }.catch { ListMangaUiState.Failure(it.message) }
    }
}