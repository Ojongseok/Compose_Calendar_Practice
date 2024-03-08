package com.example.mildfistassignment

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.mildfistassignment.component.MainTopBar
import com.example.mildfistassignment.ui.theme.Black
import com.example.mildfistassignment.ui.theme.LightGray
import com.example.mildfistassignment.ui.theme.White

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateScreen(
    navController: NavController = rememberNavController(),
    date: String,
    time: Int,
    modifier: Modifier = Modifier,
    viewModel: CalendarViewModel = hiltViewModel()
) {
//    Log.d("taag", viewModel.calendarUiModel.selectedDate.date.dayOfMonth.toString())
    Scaffold(
        modifier = modifier
            .fillMaxSize(),
        topBar = {
            MainTopBar(
                title = "일정",
                titleIcon = false,
                enableExpandButton = false,
                enableBackButton = true,
                onClickBackButton = { navController.popBackStack() }
            )
        }
    ) { paddingValues ->
        Surface(
            modifier = modifier
                .padding(paddingValues)
        ) {
            Divider(thickness = 1.dp, color = LightGray)
            Column(
                modifier = modifier.padding(20.dp)
            ) {
                ElevatedCard(
                    modifier = modifier
                        .fillMaxWidth()
                        .shadow(
                            elevation = 10.dp,
                            shape = RoundedCornerShape(16.dp)
                        ),
                    colors = CardDefaults.elevatedCardColors(containerColor = White),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Column(
                        modifier = modifier
                            .padding(horizontal = 16.dp)
                    ) {
                        Row(
                            modifier = modifier
                                .fillMaxWidth()
                                .padding(vertical = 20.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "일자",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                color = Black
                            )
                            Text(
                                text = date,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = Black
                            )
                        }
                        Divider(thickness = 1.dp, color = LightGray)

                        Row(
                            modifier = modifier
                                .fillMaxWidth()
                                .padding(vertical = 20.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "시작 시간",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                color = Black
                            )
                            Text(
                                text = "${time.toString().padStart(2,'0')}:00",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = Black
                            )
                        }
                        Divider(thickness = 1.dp, color = LightGray)

                        Row(
                            modifier = modifier
                                .fillMaxWidth()
                                .padding(vertical = 20.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "종료 시간",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                color = Black
                            )
                            Text(
                                text = "${(time+1).toString().padStart(2,'0')}:00",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = Black
                            )
                        }
                    }
                }
            }
        }
    }
}