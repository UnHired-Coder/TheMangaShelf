package com.unhiredcoder.listmanga.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun DatePillViewUI(date: String, isSelected: Boolean, onDateSelected: () -> Unit = {}) {
    val pilColor = remember(isSelected) {
        if (isSelected)
            Color.Yellow
        else
            Color.White
    }

    Box(
        modifier = Modifier
            .padding(8.dp)
            .background(pilColor, shape = RoundedCornerShape(50.dp))
            .clickable { onDateSelected() }
            .padding(horizontal = 16.dp, vertical = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = date,
            fontSize = 16.sp,
            color = Color.Black,
            textAlign = TextAlign.Center
        )
    }
}

@Preview
@Composable
fun DatePillViewUIPreview() {
    DatePillViewUI("20-03-2022", isSelected = true)
}