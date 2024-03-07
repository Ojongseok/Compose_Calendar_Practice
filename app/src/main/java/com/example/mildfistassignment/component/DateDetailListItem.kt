package com.example.mildfistassignment.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mildfistassignment.ui.theme.Gray
import com.example.mildfistassignment.ui.theme.LightGray

@Composable
fun DateDetailListItem(
    time: Int,
    onClickDate: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onClickDate
            )
    ) {
        Text(
            modifier = modifier
                .padding(4.dp),
            text = time.toString().padStart(2,'0')+":00",
            fontSize = 12.sp,
            color = Gray
        )
        Column {
            repeat(2) {
                Divider(thickness = 1.dp, color = LightGray)
                Row(
                    modifier = modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    repeat(2) {
                        Box(
                            modifier = modifier
                                .size(width = 1.dp, height = 32.dp)
                                .background(color = LightGray)
                        )
                    }
                }
            }
            if (time == 23) {
                Divider(thickness = 1.dp, color = LightGray)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DateDetailListItemPreview() {
    DateDetailListItem(
        time = 1,
        onClickDate = {}
    )
}