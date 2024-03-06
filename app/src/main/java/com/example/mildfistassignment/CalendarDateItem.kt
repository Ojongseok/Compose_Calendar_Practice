package com.example.mildfistassignment

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mildfistassignment.ui.theme.Orange

@Composable
fun CalendarDateItem(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "일"
        )
        Spacer(modifier = Modifier.size(8.dp))

        Box(
            modifier = Modifier
                .background(
                    color = Orange,
                    shape = CircleShape
                )
                .padding(8.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "25"
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CalendarDateItemPreview() {
    CalendarDateItem()
}