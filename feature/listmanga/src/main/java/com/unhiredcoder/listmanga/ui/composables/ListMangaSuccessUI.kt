package com.unhiredcoder.listmanga.ui.composables

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.unhiredcoder.listmanga.R
import com.unhiredcoder.listmanga.ui.model.ListMangaUiState


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ListMangaSuccessUI(modifier: Modifier = Modifier, listMangaUiState: ListMangaUiState) {
    val lazyListState = rememberLazyListState()
    val mangas = listMangaUiState.mangaGroupWithIndex.mangaMap
    LazyColumn(
        modifier = modifier
            .fillMaxSize(),
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
                modifier = Modifier.padding(start = 12.dp, bottom = 4.dp),
                fontSize = 10.sp,
                color = Color.Black.copy(alpha = 0.6f),
                text = "Select date"
            )

            val dates = remember { listMangaUiState.mangaGroupWithIndex.mangaMap.keys.toList() }
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color.Gray.copy(0.2f)),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                items(dates) { date ->
                    DatePillViewUI(date = date,
                        isSelected = true,
                        onDateSelected = {

                        })
                }
            }
        }

        item {
            Spacer(
                modifier = Modifier.height(20.dp)
            )
        }

        mangas.forEach { (date, mangas) ->
            item {
                DateSeparatorUI(date = date)
            }

            items(mangas) { manga ->
                MangaItemUI(
                    modifier = Modifier.padding(bottom = 8.dp),
                    manga = manga
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
