package com.unhiredcoder.listmanga.ui.composables

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.unhiredcoder.listmanga.R
import com.unhiredcoder.ui.clickableWithNoRipple


@Composable
fun FilterPopupUi(
    modifier: Modifier = Modifier,
    isFilterActive: Boolean = true,
    onSortByScore: () -> Unit,
    onSortByPopularity: () -> Unit,
    onResetFilters: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box(modifier = modifier) {
        Icon(
            modifier = Modifier
                .size(20.dp)
                .border(width = 0.5.dp, color = remember(isFilterActive) {
                    if (isFilterActive) {
                        Color.Black
                    } else {
                        Color.Transparent
                    }
                }, shape = RoundedCornerShape(4.dp))
                .padding(4.dp)
                .clickableWithNoRipple {
                    expanded = true
                },
            imageVector = ImageVector.vectorResource(R.drawable.ic_sort),
            tint = Color.Black,
            contentDescription = null
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            containerColor = Color.White
        ) {
            DropdownMenuItem(
                text = {
                    Text(
                        text = "Score",
                        fontSize = 14.sp
                    )
                },
                onClick = {
                    onSortByScore()
                    expanded = false
                }
            )

            DropdownMenuItem(
                text = {
                    Text(
                        text = "Popularity",
                        fontSize = 14.sp
                    )
                },
                onClick = {
                    onSortByPopularity()
                    expanded = false
                }
            )

            Divider()

            DropdownMenuItem(
                text = { Text("Clear Filters") },
                onClick = {
                    onResetFilters()
                    expanded = false
                }
            )
        }
    }
}


@Preview
@Composable
fun FilterPopupUiPreview() {
    FilterPopupUi(
        isFilterActive = false,
        onResetFilters = {},
        onSortByScore = {},
        onSortByPopularity = {}
    )
}