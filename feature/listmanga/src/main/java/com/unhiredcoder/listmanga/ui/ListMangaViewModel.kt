package com.unhiredcoder.listmanga.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unhiredcoder.common.data.Resource
import com.unhiredcoder.listmanga.domain.ListMangaUseCase
import com.unhiredcoder.listmanga.ui.model.ListMangaUiState
import com.unhiredcoder.listmanga.ui.model.mapToMangaGroupWithIndex
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.*

class ListMangaViewModel(listMangaUseCase: ListMangaUseCase) : ViewModel() {

    private val _mangaUiStateFlow =
        MutableStateFlow<Resource<ListMangaUiState>>(Resource.Idle(null))
    val listMangaUiState: StateFlow<Resource<ListMangaUiState>> = _mangaUiStateFlow.asStateFlow()

    init {
        viewModelScope.launch {
            listMangaUseCase()
                .map { mangaList ->
                    Resource.Success(
                        ListMangaUiState(
                            mangaList.mapToMangaGroupWithIndex(),
                            selectedDateIndex = _mangaUiStateFlow.value.data?.selectedDateIndex ?: 0
                        )
                    )
                }
                .onStart {
                    _mangaUiStateFlow.value = Resource.Loading(_mangaUiStateFlow.value.data)
                }
                .catch { e ->
                    _mangaUiStateFlow.value = Resource.Failure(_mangaUiStateFlow.value.data, e)
                }
                .collect { result -> _mangaUiStateFlow.update { result } }
        }
    }

    fun onDateSelected(dateIndex: Int) {
        _mangaUiStateFlow.update { state ->
            (state as? Resource.Success)?.let {
                state.copyWith(
                    newData = state.data?.copy(
                        selectedDateIndex = dateIndex
                    )
                )
            } ?: state
        }
    }
}
