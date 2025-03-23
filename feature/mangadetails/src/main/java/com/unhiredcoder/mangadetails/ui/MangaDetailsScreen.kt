package com.unhiredcoder.mangadetails.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.material3.Text
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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

    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally
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
                    contentScale = ContentScale.FillBounds,
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

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        modifier = Modifier
                            .background(
                                color = Color.Black,
                                shape = RoundedCornerShape(6.dp)
                            )
                            .padding(
                                vertical = 6.dp,
                                horizontal = 12.dp
                            ),
                        color = Color.White,
                        fontSize = 12.sp,
                        text = "Popularity: ${mangaDetailsUiModel.popularity}"
                    )

                    Text(
                        modifier = Modifier.padding(end = 4.dp),
                        text = mangaDetailsUiModel.title,
                        fontSize = 20.sp,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = stringResource(R.string.category, mangaDetailsUiModel.category),
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                }

                Column(
                    horizontalAlignment = Alignment.End,
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {


                    Image(
                        modifier = Modifier
                            .size(30.dp)
                            .clickableWithNoRipple {
                                onMarkFavourite(mangaDetailsUiModel)
                            },
                        painter = painterResource(remember(mangaDetailsUiModel.isFavourite) {
                            if (mangaDetailsUiModel.isFavourite) {
                                R.drawable.ic_favourite_filled
                            } else
                                R.drawable.ic_favourite_outlined
                        }),
                        contentDescription = null
                    )

                    Text(
                        modifier = Modifier
                            .border(
                                width = 0.5.dp,
                                color = Color.Red,
                                shape = RoundedCornerShape(5.dp)
                            )
                            .padding(horizontal = 6.dp, vertical = 2.dp),
                        text = stringResource(R.string.score, mangaDetailsUiModel.score),
                        fontSize = 20.sp,
                        color = Color.Red
                    )

                    Text(
                        text = stringResource(
                            R.string.pub_date,
                            mangaDetailsUiModel.publishedChapterDate
                        ),
                        fontSize = 10.sp,
                        color = Color.Gray
                    )
                }
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Text(
            modifier = Modifier
                .padding(bottom = 20.dp)
                .border(
                    width = 1.dp,
                    color = Color.Gray,
                    shape = RoundedCornerShape(6.dp)
                )
                .padding(horizontal = 12.dp, vertical = 6.dp)
                .clickableWithNoRipple {
                    onMarkRead(mangaDetailsUiModel)
                },
            text = "Mark as read"
        )

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