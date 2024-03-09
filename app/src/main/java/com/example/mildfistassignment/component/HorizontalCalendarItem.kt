package com.example.mildfistassignment.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mildfistassignment.ui.theme.Gray
import com.example.mildfistassignment.ui.theme.LightGray
import com.example.mildfistassignment.ui.theme.Orange
import com.example.mildfistassignment.ui.theme.White
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale

@Composable
fun HorizontalCalendarItem(
    date: LocalDate,
    selectedDate: LocalDate,
    enableSelectedMonth: Int,
    onClickDate: (LocalDate) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = date.dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.KOREAN),
            color = if (date.isEqual(selectedDate)) Orange else Gray
        )
        Spacer(modifier = Modifier.size(8.dp))

        Box(
            modifier = Modifier
                .size(32.dp)
                .background(
                    color = if (date.isEqual(selectedDate)) Orange else Color.Transparent,
                    shape = CircleShape
                )
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = {
                        if (date.monthValue == enableSelectedMonth) {
                            onClickDate(date)
                        }
                    }
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = date.dayOfMonth.toString(),
                color = if (date.isEqual(selectedDate)) White else {
                    if (date.monthValue == enableSelectedMonth) Gray else LightGray
                },
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HorizontalCalendarItemPreview() {
//    HorizontalCalendarItem()
}