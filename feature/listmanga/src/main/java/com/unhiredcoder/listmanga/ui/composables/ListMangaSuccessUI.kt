package com.unhiredcoder.listmanga.ui.composables

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.unhiredcoder.listmanga.R
import com.unhiredcoder.listmanga.ui.model.ListMangaScreenActions
import com.unhiredcoder.listmanga.ui.model.ListMangaUiState
import com.unhiredcoder.ui.clickableWithNoRipple
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNotNull


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ListMangaSuccessUI(
    modifier: Modifier = Modifier,
    listMangaUiState: ListMangaUiState,
    onListMangaScreenActions: (ListMangaScreenActions) -> Unit,
) {
    val mangaUiModelMapByDates = listMangaUiState.mangaGroupWithIndex.mangaUiModelMapByDates
    val pillPosToFirstMangaPos = listMangaUiState.mangaGroupWithIndex.pillPosToFirstMangaPos

    val lazyListState = rememberLazyListState()
    val screenHeightPx = LocalConfiguration.current.screenHeightDp

    LaunchedEffect(lazyListState) {
        snapshotFlow {
            getIndexToSelect(
                lazyListState = lazyListState,
                screenHeightPx = screenHeightPx
            )
        }.filterNotNull().distinctUntilChanged().collect { index ->
            onListMangaScreenActions(ListMangaScreenActions.OnScrollToIndex(index))
        }
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .statusBarsPadding(),
        state = lazyListState,
    ) {
        item {
            HeaderUi()
        }

        stickyHeader {
            Row(modifier = Modifier
                .clickableWithNoRipple {
                    //Do nothing
                }
                .fillMaxWidth()
                .background(Color.White)
                .padding(start = 12.dp, end = 12.dp, top = 20.dp, bottom = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black.copy(alpha = 0.6f),
                    text = if (listMangaUiState.isFilterActive) stringResource(
                        R.string.sorted_by,
                        listMangaUiState.sortedBy.filterName
                    ) else stringResource(R.string.select_date)
                )

                Spacer(Modifier.weight(1f))

                FilterPopupUi(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    isFilterActive = listMangaUiState.isFilterActive,
                    onSortByScore = {
                        onListMangaScreenActions(ListMangaScreenActions.OnSortByScore)
                    },
                    onResetFilters = {
                        onListMangaScreenActions(ListMangaScreenActions.OnResetFilters)
                    },
                    onSortByPopularity = {
                        onListMangaScreenActions(ListMangaScreenActions.OnSortByPopularity)
                    }
                )
            }

            val dates = remember(listMangaUiState) { mangaUiModelMapByDates.keys.toList() }
            val lazyRowState = rememberLazyListState()

            AnimatedVisibility(
                !listMangaUiState.isFilterActive,
                enter = expandVertically(),
                exit = shrinkVertically()
            ) {
                LaunchedEffect(listMangaUiState.selectedDateIndex) {
                    val visibleItems = lazyRowState.layoutInfo.visibleItemsInfo
                    val selectedIndex = listMangaUiState.selectedDateIndex

                    val selectedItem = visibleItems.find { it.index == selectedIndex }

                    // Scrolling the horizontal pills
                    if (selectedItem != null) {
                        val itemStart = selectedItem.offset
                        val itemEnd = itemStart + selectedItem.size
                        val listEnd = lazyRowState.layoutInfo.viewportEndOffset

                        if (itemStart < 0) {
                            lazyRowState.animateScrollBy(itemStart.toFloat())
                        } else if (itemEnd > listEnd) {
                            lazyRowState.animateScrollBy((itemEnd - listEnd).toFloat())
                        }
                    } else {
                        lazyRowState.animateScrollToItem(selectedIndex)
                    }

                    // Vertical scroll to section
                    if (listMangaUiState.isAutoScroll) {
                        pillPosToFirstMangaPos[selectedIndex]?.let { scrollIndex ->
                            lazyListState.animateScrollToItem(
                                if (scrollIndex > 0) scrollIndex + 2 else 0
                            )
                        }
                    }
                }

                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = Color.Gray.copy(0.2f)),
                    state = lazyRowState,
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    itemsIndexed(dates) { dateIndex, publishDate ->
                        DatePillViewUI(
                            date = publishDate,
                            isSelected = listMangaUiState.selectedDateIndex == dateIndex,
                            onDateSelected = {
                                onListMangaScreenActions(ListMangaScreenActions.OnSetAutoScroll(true))
                                onListMangaScreenActions(ListMangaScreenActions.OnDateSelected(dateIndex))
                            }
                        )
                    }
                }
            }
        }

        item { Spacer(modifier = Modifier.height(20.dp)) }

        mangaUiModelMapByDates.onEachIndexed { index, (date, mangas) ->
            item(contentType = "date", key = index) {
                DateSeparatorUI(date = date, isFilterActive = listMangaUiState.isFilterActive)
            }

            items(mangas) { mangaUiModel ->
                MangaItemUI(
                    modifier = Modifier
                        .padding(bottom = 8.dp)
                        .clickableWithNoRipple {
                            onListMangaScreenActions(ListMangaScreenActions.OnDisplayManga(mangaUiModel))
                        },
                    mangaUiModel = mangaUiModel,
                    onMarkFavourite = {
                        onListMangaScreenActions(ListMangaScreenActions.OnMarkFavourite(mangaUiModel))
                    }
                )
            }
        }

        item { Spacer(modifier = Modifier.height(20.dp)) }
    }

    if (pillPosToFirstMangaPos.isEmpty()) {
        EmptyResultsUI()
    }
}


private fun getIndexToSelect(lazyListState: LazyListState, screenHeightPx: Int): Int? {
    val firstVisibleDate =
        lazyListState.layoutInfo.visibleItemsInfo.firstOrNull { it.contentType == "date" }
            ?: return null
    val visibleIndex = firstVisibleDate.key as Int
    return if (firstVisibleDate.offset < (screenHeightPx / 2)) visibleIndex
    else null
}
