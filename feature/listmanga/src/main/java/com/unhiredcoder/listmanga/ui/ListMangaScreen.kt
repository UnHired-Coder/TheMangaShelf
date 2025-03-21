package com.unhiredcoder.listmanga.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.unhiredcoder.common.data.Resource
import com.unhiredcoder.common.ui.ScreenStateComposable
import com.unhiredcoder.listmanga.ui.model.ListMangaUiState
import com.unhiredcoder.listmanga.ui.model.Manga
import kotlinx.coroutines.flow.StateFlow


@Composable
fun ListMangaScreen(
    modifier: Modifier = Modifier,
    listMangaUiStateFlow: StateFlow<Resource<ListMangaUiState>>
) {
    ScreenStateComposable(
        modifier = modifier.background(Color.Red),
        resourceFlow = listMangaUiStateFlow,
        onSuccessComposable = { mangaState ->
            ListMangaSuccessUI(mangaList = mangaState.mangaList)
        }
    )
}

@Composable
fun ListMangaSuccessUI(modifier: Modifier = Modifier, mangaList: List<Manga>) {
    val lazyListState = rememberLazyListState()
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        state = lazyListState,
    ) {
        items(mangaList) { manga ->
            MangaItem(modifier = Modifier.padding(bottom = 12.dp), manga = manga)
        }
    }
}

@Composable
fun MangaItem(modifier: Modifier = Modifier, manga: Manga) {
    Card(
        modifier = modifier
            .padding(8.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
    ) {
        Row(
            modifier = Modifier.padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box() {
                Image(
                    painter = painterResource(id = android.R.drawable.ic_btn_speak_now), // Dummy image
                    contentDescription = "Manga Cover",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(60.dp)
                        .clip(RoundedCornerShape(6.dp))
                )
                Text(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .offset(y = -6.dp)
                        .background(color = Color.White, shape = RoundedCornerShape(5.dp))
                        .padding(2.dp),
                    text = "^${manga.score}",
                    fontSize = 12.sp,
                    color = Color.Red
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            Column(
                modifier = Modifier.height(IntrinsicSize.Min),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                Text(
                    modifier = Modifier.weight(1f),
                    text = manga.title,
                    fontSize = 18.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Category: ${manga.category}",
                        fontSize = 14.sp,
                        color = Color.Gray
                    )

                    Text(
                        text = "Pub: ${manga.publishedChapterDate}",
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }
            }
        }
    }
}


@Preview
@Composable
fun MangaItemPreview() {
    MangaItem(
        manga = Manga(
            id = "1",
            imageUrl = "",
            score = 100.0,
            popularity = 100,
            title = "This is some long title that will go out of bounds",
            publishedChapterDate = "20-03-2010",
            category = "Anime",
            isFavourite = false,
            isReadByUser = false,
        )
    )
}