package com.unhiredcoder.mangadetails.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.skydoves.landscapist.glide.GlideImage
import com.unhiredcoder.common.data.Resource
import com.unhiredcoder.mangadetails.ui.model.MangaDetailsUiModel
import com.unhiredcoder.mangadetails.ui.model.MangaDetailsUiState
import com.unhiredcoder.ui.ui.ScreenStateComposable
import kotlinx.coroutines.flow.StateFlow


@Composable
fun MangaDetailsScreen(
    modifier: Modifier = Modifier,
    mangaDetailsUiStateFlow: StateFlow<Resource<MangaDetailsUiState>>,
    onMarkFavourite: (mangaUiModel: MangaDetailsUiModel) -> Unit,
) {
    ScreenStateComposable(
        modifier = modifier.background(Color.Red),
        resourceFlow = mangaDetailsUiStateFlow,
        onSuccessComposable = { mangaDetailsUiModel ->
            MangaDetailsSuccessUi(
                modifier = Modifier.background(Color.White),
                mangaDetailsUiModel = mangaDetailsUiModel.mangaDetailsUiModel,
                onMarkFavourite = onMarkFavourite,
            )
        }
    )
}

@Composable
fun MangaDetailsSuccessUi(
    modifier: Modifier = Modifier,
    mangaDetailsUiModel: MangaDetailsUiModel,
    onMarkFavourite: (mangaUiModel: MangaDetailsUiModel) -> Unit,
) {
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    val halfScreenHeight = screenHeight / 2

    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {
        GlideImage(
            modifier = Modifier
                .fillMaxWidth()
                .height(halfScreenHeight),
            imageModel = {
                mangaDetailsUiModel.imageUrl
            },
            success = { _, imagePainter ->
                Image(
                    modifier = Modifier
                        .fillMaxSize(),
                    painter = imagePainter,
                    contentDescription = null
                )
            }
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = mangaDetailsUiModel.title,
                style = MaterialTheme.typography.bodySmall
            )
            Spacer(modifier = Modifier.height(8.dp))

            Text(text = "Score: ${mangaDetailsUiModel.score}")
            Text(text = "Popularity: ${mangaDetailsUiModel.popularity}")
            Text(text = "Published: ${mangaDetailsUiModel.publishedChapterDate}")
            Text(text = "Category: ${mangaDetailsUiModel.category}")

            Spacer(modifier = Modifier.height(16.dp))

            val favoriteLabel = if (mangaDetailsUiModel.isFavourite) "Unfavorite" else "Favorite"
            Button(
                onClick = { onMarkFavourite(mangaDetailsUiModel) },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text(text = favoriteLabel, textAlign = TextAlign.Center)
            }
        }
    }
}

@Preview
@Composable
fun MangaDetailsScreenPreview() {
    MangaDetailsSuccessUi(
        mangaDetailsUiModel = MangaDetailsUiModel(
            id = "1",
            imageUrl = "",
            score = 100.0,
            popularity = 100,
            title = "This is some long title that will go out of bounds",
            publishedChapterDate = "20-03-2010",
            category = "Anime",
            isFavourite = false,
            isReadByUser = false,
        ),
    ) {

    }
}