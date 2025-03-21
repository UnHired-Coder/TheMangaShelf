package com.unhiredcoder.listmanga.ui.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.skydoves.landscapist.glide.GlideImage
import com.unhiredcoder.listmanga.ui.model.Manga

@Composable
fun MangaItemUI(modifier: Modifier = Modifier, manga: Manga) {
    Card(
        modifier = modifier
            .fillMaxWidth(),
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
                    .size(60.dp)
                    .clip(RoundedCornerShape(6.dp)),
                imageModel = {
                    manga.imageUrl
                },
                success = { _, painter ->
                    Image(
                        painter = painter, // Dummy image
                        contentDescription = "Manga Cover",
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

                Text(
                    modifier = Modifier.weight(1f),
                    text = manga.title,
                    fontSize = 14.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = "Category: ${manga.category}",
                    fontSize = 12.sp,
                    color = Color.Gray
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        modifier = Modifier
                            .background(color = Color.White, shape = RoundedCornerShape(5.dp))
                            .padding(2.dp),
                        text = "^${manga.score}",
                        fontSize = 12.sp,
                        color = Color.Red
                    )

                    Text(
                        text = "Pub: ${manga.publishedChapterDate}",
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