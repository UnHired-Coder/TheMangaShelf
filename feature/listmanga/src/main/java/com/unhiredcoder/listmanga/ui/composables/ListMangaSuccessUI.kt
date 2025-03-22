package com.unhiredcoder.listmanga.ui.composables

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.unhiredcoder.listmanga.R
import com.unhiredcoder.listmanga.ui.model.ListMangaUiState
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNotNull


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ListMangaSuccessUI(
    modifier: Modifier = Modifier,
    listMangaUiState: ListMangaUiState,
    onDateSelected: (dateIndex: Int) -> Unit
) {
    val mangaMap = listMangaUiState.mangaGroupWithIndex.mangaMapByDates

    val lazyListState = rememberLazyListState()
    val screenHeightPx = LocalConfiguration.current.screenHeightDp
    var isAutoScroll by remember { mutableStateOf(false) }

    LaunchedEffect(lazyListState) {
        snapshotFlow {
            val firstVisibleDate =
                lazyListState.layoutInfo.visibleItemsInfo.firstOrNull { it.contentType == "date" }
                    ?: return@snapshotFlow null
            val visibleIndex = firstVisibleDate.key as Int
            if (firstVisibleDate.offset < (screenHeightPx / 2)) visibleIndex
            else null
        }.filterNotNull().distinctUntilChanged().collect { index ->
            if (!isAutoScroll) {
                onDateSelected(index)
            } else {
                isAutoScroll = false
            }
        }
    }

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        state = lazyListState,
    ) {

        item {
            Text(
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 16.dp),
                text = stringResource(R.string.the_manga_app),
                fontFamily = FontFamily.Serif
            )
        }

        stickyHeader {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(horizontal = 12.dp, vertical = 4.dp),
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black.copy(alpha = 0.6f),
                text = stringResource(R.string.select_date)
            )

            val dates =
                remember(listMangaUiState) { listMangaUiState.mangaGroupWithIndex.mangaMapByDates.keys.toList() }
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

                if (isAutoScroll) {
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
                itemsIndexed(dates) { dateIndex, date ->
                    DatePillViewUI(date = date,
                        isSelected = remember(listMangaUiState.selectedDateIndex) { listMangaUiState.selectedDateIndex == dateIndex },
                        onDateSelected = {
                            isAutoScroll = true
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

            items(mangas) { manga ->
                MangaItemUI(
                    modifier = Modifier.padding(bottom = 8.dp), manga = manga
                )
            }

        }

        item {
            Spacer(
                modifier = Modifier.height(20.dp)
            )
        }
    }
}

