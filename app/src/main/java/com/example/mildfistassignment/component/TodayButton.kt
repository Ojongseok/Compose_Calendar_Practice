package com.example.mildfistassignment.component

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mildfistassignment.ui.theme.Gray

@Composable
fun TodayButton(
    modifier: Modifier = Modifier,
    onClickTodayButton: () -> Unit
) {
    Column(
        modifier = modifier
            .border(
                width = 1.dp,
                color = Gray,
                shape = RoundedCornerShape(4.dp)
            )
            .padding(4.dp)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onClickTodayButton
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "오늘",
            fontSize = 14.sp,
            color = Gray
        )
        Spacer(modifier = Modifier.size(8.dp))
        Icon(
            modifier = Modifier.size(20.dp),
            imageVector = Icons.Default.Refresh,
            contentDescription = null,
            tint = Gray
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TodayButtonPreview() {
    TodayButton(
        onClickTodayButton = {}
    )
}