package com.unhiredcoder.listmanga.ui.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.skydoves.landscapist.glide.GlideImage
import com.unhiredcoder.ui.clickableWithNoRipple
import com.unhiredcoder.listmanga.R
import com.unhiredcoder.listmanga.ui.model.MangaUiModel
import kotlinx.coroutines.delay

@Composable
fun MangaItemUI(
    modifier: Modifier = Modifier,
    mangaUiModel: MangaUiModel,
    onMarkFavourite: (mangaUiModel: MangaUiModel) -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
    ) {
        Row(
            modifier = Modifier.padding(8.dp),
            verticalAlignment = Alignment.Top
        ) {
            GlideImage(
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(6.dp)),
                imageModel = {
                    mangaUiModel.imageUrl
                },
                success = { _, painter ->
                    Image(
                        painter = painter,
                        contentDescription = stringResource(R.string.manga_cover),
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                },
            )

            Spacer(modifier = Modifier.width(8.dp))

            Column(
                modifier = Modifier.height(IntrinsicSize.Min),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Row(horizontalArrangement = Arrangement.Start) {
                    Column(
                        modifier = Modifier
                            .weight(1f),
                        verticalArrangement = Arrangement.spacedBy(2.dp),
                        horizontalAlignment = Alignment.Start
                    ) {
                        Text(
                            modifier = Modifier,
                            text = mangaUiModel.title,
                            fontSize = 14.sp,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )

                        Text(
                            text = stringResource(R.string.popularity, mangaUiModel.popularity),
                            fontSize = 12.sp,
                            color = Color.Gray
                        )

                        Text(
                            text = stringResource(R.string.category, mangaUiModel.category),
                            fontSize = 12.sp,
                            color = Color.Gray
                        )
                    }

                    var showFavouritePopup by remember { mutableStateOf(false) }
                    LaunchedEffect(showFavouritePopup) {
                        if (showFavouritePopup) {
                            delay(1000)
                            showFavouritePopup = false
                        }
                    }

                    Column(
                        modifier = Modifier.wrapContentSize(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            modifier = Modifier
                                .size(30.dp)
                                .clickableWithNoRipple {
                                    showFavouritePopup = !mangaUiModel.isFavourite
                                    onMarkFavourite(mangaUiModel)
                                },
                            painter = painterResource(
                                if (mangaUiModel.isFavourite) R.drawable.ic_favourite_filled
                                else R.drawable.ic_favourite_outlined
                            ),
                            contentDescription = null
                        )

                        if (showFavouritePopup) {
                            Popup(
                                alignment = Alignment.BottomCenter,
                                properties = PopupProperties(
                                    focusable = false,
                                    dismissOnClickOutside = true,
                                    dismissOnBackPress = true,
                                    clippingEnabled = false
                                )
                            ) {
                                val composition by rememberLottieComposition(
                                    LottieCompositionSpec.RawRes(
                                        R.raw.favourite_lottie
                                    )
                                )
                                LottieAnimation(
                                    modifier = Modifier,
                                    composition = composition
                                )
                            }
                        }
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        modifier = Modifier
                            .border(
                                width = 0.5.dp,
                                color = Color.Red,
                                shape = RoundedCornerShape(5.dp)
                            )
                            .padding(horizontal = 6.dp, vertical = 2.dp),
                        text = stringResource(R.string.score, mangaUiModel.score),
                        fontSize = 12.sp,
                        color = Color.Red
                    )

                    Text(
                        text = stringResource(R.string.pub_date, mangaUiModel.publishedChapterDate),
                        fontSize = 10.sp,
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
    MangaItemUI(
        mangaUiModel = MangaUiModel(
            id = "1",
            imageUrl = "",
            score = 100.0,
            popularity = 100,
            title = "This is some long title that will go out of bounds",
            publishedChapterDate = "20-03-2010",
            category = "Anime",
            isFavourite = false
        ),
    ) {}
}