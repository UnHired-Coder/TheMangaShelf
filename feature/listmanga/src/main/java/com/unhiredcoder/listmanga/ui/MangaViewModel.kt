package com.unhiredcoder.listmanga.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unhiredcoder.common.data.Resource
import com.unhiredcoder.domain.usecase.GetMangaListUseCase
import com.unhiredcoder.domain.usecase.MarkMangaFavouriteUseCase
import com.unhiredcoder.domain.usecase.SyncManagUseCase
import com.unhiredcoder.listmanga.ui.model.ListMangaUiState
import com.unhiredcoder.listmanga.ui.model.MangaListFilters
import com.unhiredcoder.listmanga.ui.model.MangaUiModel
import com.unhiredcoder.listmanga.ui.model.mapToMangaGroupWithIndex
import com.unhiredcoder.listmanga.ui.model.mapToMangaUiModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.supervisorScope

class MangaViewModel(
    getMangaListUseCase: GetMangaListUseCase,
    syncManagUseCase: SyncManagUseCase,
    private val markMangaFavouriteUseCase: MarkMangaFavouriteUseCase
) : ViewModel() {

    private val _mangaUiStateFlow =
        MutableStateFlow<Resource<ListMangaUiState>>(Resource.Idle(null))
    val listMangaUiState: StateFlow<Resource<ListMangaUiState>> = _mangaUiStateFlow.asStateFlow()


    private val _filterFlow = MutableStateFlow(MangaListFilters.SortByDate)

    init {

        viewModelScope.launch {
            supervisorScope {
                val mangaFlow = getMangaListUseCase()
                    .map { mangaList -> mangaList.map { it.mapToMangaUiModel() } }
                    .shareIn(this, SharingStarted.Eagerly, replay = 1)

                combine(
                    mangaFlow,
                    _filterFlow
                ) { mangaList, filter ->
                    val sortedList = when (filter) {
                        MangaListFilters.SortByDate -> mangaList.sortedByDescending { it.publishedChapterDate }
                        MangaListFilters.SortByScore -> mangaList.sortedByDescending { it.score }
                        MangaListFilters.SortByPopularity -> mangaList.sortedByDescending { it.popularity }
                    }

                    ListMangaUiState(
                        isFilterActive = filter != MangaListFilters.SortByDate,
                        sortedBy = filter,
                        mangaGroupWithIndex = sortedList.mapToMangaGroupWithIndex(),
                        isAutoScroll = listMangaUiState.value.data?.isAutoScroll ?: false,
                        selectedDateIndex = listMangaUiState.value.data?.selectedDateIndex ?: 0
                    )
                }.catch { e ->
                    _mangaUiStateFlow.value = Resource.Failure(_mangaUiStateFlow.value.data, e)
                }.collect { state ->
                    _mangaUiStateFlow.value = Resource.Success(state)
                }

                launch {
                    try {
                        _mangaUiStateFlow.value = Resource.Loading(_mangaUiStateFlow.value.data)
                        syncManagUseCase()
                    } catch (e: Exception) {
                        _mangaUiStateFlow.update {
                            Resource.Failure(_mangaUiStateFlow.value.data, e)
                        }
                    }
                }
            }
        }


        /*viewModelScope.launch {
            supervisorScope {
                launch {
                    getMangaListUseCase()
                        .map { mangaList ->
                            Resource.Success(
                                ListMangaUiState(
                                    mangaGroupWithIndex = mangaList.sortedByDescending {
                                        MangaListFilters.SortByPopularity
                                    }.map {
                                        it.mapToMangaUiModel()
                                    }.mapToMangaGroupWithIndex(),
                                    selectedDateIndex = _mangaUiStateFlow.value.data?.selectedDateIndex
                                        ?: 0
                                )
                            )
                        }.catch { e ->
                            _mangaUiStateFlow.value =
                                Resource.Failure(_mangaUiStateFlow.value.data, e)
                        }
                        .collect { listMangaUiModelResource ->
                            println(listMangaUiModelResource.data)
                            _mangaUiStateFlow.update {
                                listMangaUiModelResource
                            }
                        }
                }

                launch {
                    try {
                        _mangaUiStateFlow.value = Resource.Loading(_mangaUiStateFlow.value.data)
                        syncManagUseCase()
                    } catch (e: Exception) {
                        _mangaUiStateFlow.update {
                            Resource.Failure(_mangaUiStateFlow.value.data, e)
                        }
                    }
                }
            }
        }*/
    }

    fun onDateSelected(dateIndex: Int) {
        _mangaUiStateFlow.update { state ->
            (state as? Resource)?.let {
                state.data?.copy(
                    selectedDateIndex = dateIndex
                )?.let {
                    Resource.Success(
                        it
                    )
                }
            } ?: state
        }
    }

    fun markFavourite(mangaUiModel: MangaUiModel) {
        viewModelScope.launch {
            markMangaFavouriteUseCase(mangaUiModel.id)
        }
    }

    fun onSetAutoScroll(set: Boolean) {
        _mangaUiStateFlow.update { state ->
            (state as? Resource)?.let {
                state.data?.copy(
                    isAutoScroll = set
                )?.let {
                    Resource.Success(
                        it
                    )
                }
            } ?: state
        }
    }

    fun onScrollToIndex(index: Int) {
        listMangaUiState.value.data?.let { state ->
            if (!state.isAutoScroll) {
                onDateSelected(index)
            } else {
                onSetAutoScroll(false)
            }
        }
    }

    fun onResetFilters() {
        sort(sortBy = MangaListFilters.SortByDate)
    }

    fun onSortByScore() {
        sort(sortBy = MangaListFilters.SortByScore)
    }

    fun onSortByPopularity() {
        sort(sortBy = MangaListFilters.SortByPopularity)
    }

    private fun sort(sortBy: MangaListFilters) {
        _filterFlow.update {
            sortBy
        }
    }
}
