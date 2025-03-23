package com.unhiredcoder.mangadetails.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.skydoves.landscapist.glide.GlideImage
import com.unhiredcoder.common.data.Resource
import com.unhiredcoder.mangadetails.R
import com.unhiredcoder.mangadetails.ui.model.MangaDetailsUiModel
import com.unhiredcoder.mangadetails.ui.model.MangaDetailsUiState
import com.unhiredcoder.ui.clickableWithNoRipple
import com.unhiredcoder.ui.ui.ScreenStateComposable
import kotlinx.coroutines.flow.StateFlow


@Composable
fun MangaDetailsScreen(
    modifier: Modifier = Modifier,
    mangaDetailsUiStateFlow: StateFlow<Resource<MangaDetailsUiState>>,
    onMarkFavourite: (mangaUiModel: MangaDetailsUiModel) -> Unit,
    onMarkRead: (mangaUiModel: MangaDetailsUiModel) -> Unit,
) {
    ScreenStateComposable(
        modifier = modifier.background(Color.Red),
        resourceFlow = mangaDetailsUiStateFlow,
        onSuccessComposable = { mangaDetailsUiModel ->
            MangaDetailsSuccessUi(
                modifier = Modifier.background(Color.White),
                mangaDetailsUiModel = mangaDetailsUiModel.mangaDetailsUiModel,
                onMarkFavourite = onMarkFavourite,
                onMarkRead = onMarkRead
            )
        }
    )
}

@Composable
fun MangaDetailsSuccessUi(
    modifier: Modifier = Modifier,
    mangaDetailsUiModel: MangaDetailsUiModel,
    onMarkFavourite: (mangaUiModel: MangaDetailsUiModel) -> Unit,
    onMarkRead: (mangaUiModel: MangaDetailsUiModel) -> Unit,
) {
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    val halfScreenHeight = screenHeight / 2


    Box(modifier = modifier.fillMaxSize()) {
        Image(
            modifier = Modifier
                .fillMaxSize()
                .blur(5.dp),
            painter = painterResource(id = R.drawable.anime_bg),
            contentDescription = null,
            contentScale = ContentScale.FillBounds
        )

        Column(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            GlideImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
                    .height(halfScreenHeight)
                    .clip(shape = RoundedCornerShape(20.dp)),
                imageModel = {
                    mangaDetailsUiModel.imageUrl
                },
                success = { _, imagePainter ->
                    Image(
                        modifier = Modifier
                            .fillMaxSize(),
                        painter = imagePainter,
                        contentScale = ContentScale.FillBounds,
                        contentDescription = null
                    )
                }
            )

            MangaDetailsCardUi(
                mangaDetailsUiModel = mangaDetailsUiModel,
                onMarkFavourite = onMarkFavourite
            )

            Spacer(modifier = Modifier.weight(1f))

            AnimatedContent(
                targetState = mangaDetailsUiModel.isReadByUser,
                label = "isReadByUser"
            ) { isReadByUser ->
                if (isReadByUser) {
                    Text(
                        modifier = Modifier
                            .padding(bottom = 20.dp)
                            .padding(horizontal = 12.dp, vertical = 6.dp),
                        text = stringResource(R.string.marked_as_read),
                        color = Color.White
                    )
                } else {
                    Text(
                        modifier = Modifier
                            .padding(bottom = 20.dp)
                            .border(
                                width = 1.dp,
                                color = Color.White,
                                shape = RoundedCornerShape(6.dp)
                            )
                            .background(Color.Black.copy(0.8f))
                            .padding(horizontal = 12.dp, vertical = 6.dp)
                            .clickableWithNoRipple {
                                onMarkRead(mangaDetailsUiModel)
                            },
                        text = stringResource(R.string.mark_as_read),
                        color = Color.White
                    )
                }
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
            publishedChapterDate = "20th, May 2010",
            category = "Anime",
            isFavourite = false,
            isReadByUser = false,
        ),
        onMarkRead = { it -> },
        onMarkFavourite = { it -> }
    )
}