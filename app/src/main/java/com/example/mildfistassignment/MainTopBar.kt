package com.example.mildfistassignment

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mildfistassignment.ui.theme.Gray
import com.example.mildfistassignment.ui.theme.White

@Composable
fun MainTopBar(
    modifier: Modifier = Modifier,
    onClickExpandButton: () -> Unit = {},
    enableBackButton: Boolean = false,
    onClickBackButton: () -> Unit = {}
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(color = White)
            .padding(all = 12.dp)
    ) {
        if (enableBackButton) {
            Icon(
                modifier = modifier
                    .align(Alignment.CenterStart)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null,
                        onClick = onClickBackButton
                    ),
                imageVector = Icons.Default.KeyboardArrowLeft,
                contentDescription = null
            )
        }
        Row(
            modifier = modifier
                .align(Alignment.Center),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "02월",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
            Icon(
                modifier = modifier
                    .size(20.dp),
                imageVector = Icons.Default.KeyboardArrowDown,
                contentDescription = null,
                tint = Gray
            )
        }
        Text(
            modifier = modifier
                .align(Alignment.CenterEnd)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = onClickExpandButton
                ),
            text = "주간 일정 잡기",
            color = Gray,
            fontSize = 12.sp
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MainTopBarPreview() {
    Column {
        MainTopBar(enableBackButton = false)
        Spacer(modifier = Modifier.size(12.dp))
        MainTopBar(enableBackButton = true)
    }
}