package com.unhiredcoder.mangadetails.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.unhiredcoder.mangadetails.R
import com.unhiredcoder.mangadetails.ui.model.MangaDetailsUiModel
import com.unhiredcoder.ui.clickableWithNoRipple

@Composable
fun MangaDetailsCardUi(
    modifier: Modifier = Modifier,
    mangaDetailsUiModel: MangaDetailsUiModel,
    onMarkFavourite: (mangaDetailsUiModel: MangaDetailsUiModel) -> Unit
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(6.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp),
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
}