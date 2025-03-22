package com.unhiredcoder.mangadetails.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unhiredcoder.common.data.Resource
import com.unhiredcoder.domain.usecase.GetMangaUseCase
import com.unhiredcoder.domain.usecase.MarkMangaFavouriteUseCase
import com.unhiredcoder.mangadetails.ui.model.MangaDetailsUiModel
import com.unhiredcoder.mangadetails.ui.model.MangaDetailsUiState
import com.unhiredcoder.mangadetails.ui.model.mapToMangaDetailsUiModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.*

class MangaDetailsViewModel(
    private val getMangaUseCase: GetMangaUseCase,
    private val markMangaFavouriteUseCase: MarkMangaFavouriteUseCase
) : ViewModel() {

    private val _mangaDetailsUiStateFlow =
        MutableStateFlow<Resource<MangaDetailsUiState>>(Resource.Idle(null))
    val mangaDetailsUiState: StateFlow<Resource<MangaDetailsUiState>> =
        _mangaDetailsUiStateFlow.asStateFlow()

    fun getMangaDetails(mangaId: String) {
        println("mangaId: $mangaId")
        viewModelScope.launch {
            getMangaUseCase(mangaId = mangaId)
                .onStart {
                    _mangaDetailsUiStateFlow.update {
                        Resource.Loading(_mangaDetailsUiStateFlow.value.data)
                    }
                }.catch { error ->
                    _mangaDetailsUiStateFlow.update {
                        Resource.Failure(_mangaDetailsUiStateFlow.value.data, errorMessage = error)
                    }
                }.collect { mangaDetailsUiModel ->
                    mangaDetailsUiModel?.let {
                        _mangaDetailsUiStateFlow.update {
                            Resource.Success(
                                MangaDetailsUiState(
                                    mangaDetailsUiModel = mangaDetailsUiModel.mapToMangaDetailsUiModel()
                                )
                            )
                        }
                    } ?: kotlin.run {
                        _mangaDetailsUiStateFlow.update {
                            Resource.Failure(
                                _mangaDetailsUiStateFlow.value.data,
                                errorMessage = Throwable("No Record Found!")
                            )
                        }
                    }
                }
        }
    }

    fun markFavourite(mangaUiModel: MangaDetailsUiModel) {
        viewModelScope.launch {
            markMangaFavouriteUseCase(mangaUiModel.id)
        }
    }
}
