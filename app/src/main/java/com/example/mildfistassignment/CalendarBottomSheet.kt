package com.example.mildfistassignment

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mildfistassignment.ui.theme.Black
import com.example.mildfistassignment.ui.theme.Gray
import com.example.mildfistassignment.ui.theme.LightGray
import com.example.mildfistassignment.ui.theme.White
import com.holix.android.bottomsheetdialog.compose.BottomSheetDialog
import com.holix.android.bottomsheetdialog.compose.BottomSheetDialogProperties

@Composable
fun CalendarBottomSheet(
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier
) {
    BottomSheetDialog(
        onDismissRequest = onDismissRequest,
        properties = BottomSheetDialogProperties(

        )
    ) {
        Column(
            modifier = modifier
                .background(
                    shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
                    color = White
                )
                .padding(vertical = 24.dp, horizontal = 12.dp)
        ) {
            Box(
                modifier = modifier
                    .fillMaxWidth()
//                    .padding()
            ) {
                Text(
                    modifier = modifier
                        .align(Alignment.Center),
                    text = "캘린더",
                    fontWeight = FontWeight.Bold,
                    color = Black
                )
                Box(
                    modifier = modifier
                        .border(
                            shape = RoundedCornerShape(20.dp),
                            width = 1.dp,
                            color = Gray
                        )
                        .padding(vertical = 4.dp, horizontal = 10.dp)
                        .align(Alignment.CenterEnd)
                ) {
                    Text(
                        modifier = modifier
                            .align(Alignment.Center),
                        text = "오늘로",
                        fontSize = 12.sp,
                        color = Gray
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CalendarBottomSheetPreview() {
    CalendarBottomSheet(onDismissRequest = {})
}