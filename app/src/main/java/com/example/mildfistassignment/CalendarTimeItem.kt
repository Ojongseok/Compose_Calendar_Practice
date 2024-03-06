package com.example.mildfistassignment

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.OnGloballyPositionedModifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mildfistassignment.ui.theme.Gray

@Composable
fun CalendarTimeItem(
    time: Int,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
          modifier = modifier
              .weight(1f)
        ) {
            Text(
                modifier = modifier
                    .align(Alignment.Center),
                text = time.toString().padStart(2,'0'),
                fontSize = 12.sp,
                color = Gray
            )
        }

        repeat(7) {
            Divider(
                modifier = modifier
                    .size(width = 1.dp, height = 48.dp)
                    .background(color = Gray)
            )
            Spacer(modifier = Modifier.weight(1f))
        }
    }

}

@Preview(showBackground = true)
@Composable
fun CalendarTimeItemPreview() {
    CalendarTimeItem(1)
}