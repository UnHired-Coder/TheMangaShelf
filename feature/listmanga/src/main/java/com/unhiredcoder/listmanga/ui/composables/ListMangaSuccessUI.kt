package com.unhiredcoder.listmanga.ui.composables

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.unhiredcoder.listmanga.R
import com.unhiredcoder.listmanga.ui.model.ListMangaUiState
import com.unhiredcoder.listmanga.ui.model.MangaUiModel
import com.unhiredcoder.ui.clickableWithNoRipple
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNotNull


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ListMangaSuccessUI(
    modifier: Modifier = Modifier,
    listMangaUiState: ListMangaUiState,
    onDateSelected: (dateIndex: Int) -> Unit,
    onMarkFavourite: (mangaUiModel: MangaUiModel) -> Unit,
    onDisplayManga: (mangaUiModel: MangaUiModel) -> Unit,
    onSetAutoScroll: (set: Boolean) -> Unit,
    onScrollToIndex: (index: Int) -> Unit,
) {
    val mangaMap = listMangaUiState.mangaGroupWithIndex.mangaUiModelMapByDates

    val lazyListState = rememberLazyListState()
    val screenHeightPx = LocalConfiguration.current.screenHeightDp

    LaunchedEffect(lazyListState) {
        snapshotFlow {
            getIndexToSelect(
                lazyListState = lazyListState,
                screenHeightPx = screenHeightPx
            )
        }.filterNotNull().distinctUntilChanged().collect { index ->
            onScrollToIndex(index)
        }
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .statusBarsPadding(),
        state = lazyListState,
    ) {
        item {
            Row(
                modifier = Modifier.padding(start = 10.dp, end = 10.dp, top = 34.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.Bottom
            ) {
                Image(
                    modifier = Modifier
                        .size(height = 30.dp, width = 40.dp),
                    painter = painterResource(id = R.drawable.anime),
                    contentDescription = null,
                    contentScale = ContentScale.FillBounds
                )
                Text(
                    modifier = Modifier,
                    text = stringResource(R.string.the_manga_app),
                    fontFamily = FontFamily.Serif
                )
            }
        }

        stickyHeader {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(start = 12.dp, end = 12.dp, top = 20.dp, bottom = 8.dp),
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black.copy(alpha = 0.6f),
                text = stringResource(R.string.select_date)
            )

            val dates =
                remember(listMangaUiState) { listMangaUiState.mangaGroupWithIndex.mangaUiModelMapByDates.keys.toList() }
            val lazyRowState = rememberLazyListState()


            LaunchedEffect(listMangaUiState.selectedDateIndex) {
                val visibleItems = lazyRowState.layoutInfo.visibleItemsInfo
                val selectedIndex = listMangaUiState.selectedDateIndex

                val selectedItem = visibleItems.find { it.index == selectedIndex }

                if (selectedItem != null) {
                    val itemStart = selectedItem.offset
                    val itemEnd = itemStart + selectedItem.size
                    val listStart = 0
                    val listEnd = lazyRowState.layoutInfo.viewportEndOffset

                    if (itemStart < listStart) {
                        lazyRowState.animateScrollBy(selectedItem.offset.toFloat())
                    } else if (itemEnd > listEnd) {
                        lazyRowState.animateScrollBy(selectedItem.size.toFloat())
                    }
                } else {
                    lazyRowState.animateScrollToItem(selectedIndex)
                }

                if (listMangaUiState.isAutoScroll) {
                    listMangaUiState.mangaGroupWithIndex.pillPosToFirstMangaPos[listMangaUiState.selectedDateIndex]?.let { scrollIndex ->
                        lazyListState.animateScrollToItem(
                            if (scrollIndex > 0) scrollIndex + 2 else 0 // this offset '+2' corresponds to the two items added above
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
                    DatePillViewUI(date = publishDate,
                        isSelected = remember(listMangaUiState.selectedDateIndex) { listMangaUiState.selectedDateIndex == dateIndex },
                        onDateSelected = {
                            onSetAutoScroll(true)
                            onDateSelected(dateIndex)
                        }
                    )
                }
            }
        }

        item {
            Spacer(
                modifier = Modifier.height(20.dp)
            )
        }

        mangaMap.onEachIndexed { index, (date, mangas) ->
            item(contentType = "date", key = index) {
                DateSeparatorUI(date = date)
            }

            items(mangas) { mangaUiModel ->
                MangaItemUI(
                    modifier = Modifier
                        .padding(bottom = 8.dp)
                        .clickableWithNoRipple {
                            onDisplayManga(mangaUiModel)
                        },
                    mangaUiModel = mangaUiModel,
                    onMarkFavourite = onMarkFavourite
                )
            }

        }

        item {
            Spacer(
                modifier = Modifier.height(20.dp)
            )
        }
    }


    if (listMangaUiState.mangaGroupWithIndex.pillPosToFirstMangaPos.isEmpty()) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                modifier = Modifier.align(Alignment.Center),
                textAlign = TextAlign.Center,
                text = stringResource(R.string.nothing_to_display_please_check_your_internet_connection)
            )
        }
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
