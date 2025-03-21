package com.unhiredcoder.listmanga.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun DatePillViewUI(date: String, isSelected: Boolean, onDateSelected: () -> Unit = {}) {
    val pilColor = remember(isSelected) {
        if (isSelected)
            Color.Black
        else
            Color.White
    }

    val fontColor = remember(isSelected) {
        if (isSelected)
            Color.White
        else
            Color.Black
    }

    Text(
        modifier = Modifier
            .clickable {
                onDateSelected()
            }
            .padding(horizontal = 8.dp, vertical = 8.dp)
            .background(
                color = pilColor,
                shape = RoundedCornerShape(50),
            )
            .border(
                width = 0.5.dp, color = Color.Gray.copy(alpha = 0.5f),
                shape = RoundedCornerShape(50)
            )
            .padding(horizontal = 16.dp, vertical = 8.dp),
        text = date,
        fontSize = 10.sp,
        color = fontColor
    )
}

@Preview
@Composable
fun DatePillViewUIPreview() {
    DatePillViewUI("20-03-2022", isSelected = true)
}