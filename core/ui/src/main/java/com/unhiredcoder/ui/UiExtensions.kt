package com.unhiredcoder.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.ui.Modifier
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime


fun Modifier.clickableWithNoRipple(onClick: () -> Unit): Modifier {
    return this.clickable(
        indication = null,
        interactionSource = MutableInteractionSource(),
        onClick = onClick
    )
}

fun Long.toReadableDate(): String {
    if(this == 0L)
        return ""
    return let {
        val dateTime = Instant.fromEpochSeconds(this)
            .toLocalDateTime(TimeZone.currentSystemDefault())
        val daySuffix = getDaySuffix(dateTime.dayOfMonth)
        val month = dateTime.month.name.lowercase()
            .replaceFirstChar { it.uppercase() } // Capitalize month
        val year = dateTime.year
        "${dateTime.dayOfMonth}$daySuffix $month, $year"
    }
}

private fun getDaySuffix(day: Int): String {
    return when {
        day in 11..13 -> "th"
        day % 10 == 1 -> "st"
        day % 10 == 2 -> "nd"
        day % 10 == 3 -> "rd"
        else -> "th"
    }
}