package com.unhiredcoder.listmanga.ui.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import com.unhiredcoder.listmanga.R

@Composable
fun HeaderUi(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier.padding(start = 10.dp, end = 10.dp, top = 34.dp),
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