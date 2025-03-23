package com.unhiredcoder.listmanga.ui.composables

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun DateSeparatorUI(modifier: Modifier = Modifier, date: String, isFilterActive: Boolean) {
    AnimatedVisibility(
        !isFilterActive,
        enter = expandVertically(),
        exit = shrinkVertically()
    ) {
        Box(
            modifier =
            modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Spacer(
                Modifier
                    .fillMaxWidth()
                    .height(0.5.dp)
                    .background(
                        Color.Gray.copy(alpha = 0.5f), shape = RoundedCornerShape(50)
                    )
            )

            DatePillViewUI(date = date)
        }
    }
}


@Preview
@Composable
fun DateSeparatorUIPreview() {
    DateSeparatorUI(date = "10-20-2010", isFilterActive = true)
}