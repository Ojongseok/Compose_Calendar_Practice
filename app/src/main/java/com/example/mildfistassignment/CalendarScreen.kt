package com.example.mildfistassignment

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mildfistassignment.ui.theme.White
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarScreen(
    modifier: Modifier = Modifier
) {
    var isExpanded by remember { mutableStateOf(false) }
    val scrollState = rememberLazyListState()

    val dateList = listOf(
        Date("월","1"),
        Date("화","2"),
        Date("수","3"),
        Date("목","4"),
        Date("금","5"),
        Date("토","6"),
        Date("일","7"),
    )

    Scaffold(
        modifier = modifier
            .fillMaxSize(),
        topBar = {
            MainTopBar(
                onClickExpandButton = {
                    isExpanded = !isExpanded
                }
            )
        }
    ) { paddingValues ->
        ElevatedCard(
            modifier = modifier
                .fillMaxWidth()
                .shadow(
                    elevation = 10.dp,
                    shape = RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp)
                )
                .padding(paddingValues),
            colors = CardDefaults.elevatedCardColors(containerColor = White),
        ) {
            Column(
                modifier = modifier
                    .padding(20.dp)
                    .animateContentSize(
                        animationSpec = spring(
                            dampingRatio = Spring.DampingRatioNoBouncy,
                            stiffness = Spring.StiffnessLow
                        )
                    )
            ) {
                LazyRow(
                    modifier = modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    if (isExpanded) {
                        item {
                            CalendarHorizontalToday(
                                modifier = modifier.weight(1f)
                            )
                        }
                    }

                    items(count = 7) {
                        CalendarDateItem(
                            modifier = modifier
                                .weight(1f)
                        )
                    }
                }
                Spacer(modifier = Modifier.size(24.dp))

                if (isExpanded) {
                    LazyColumn(
                        modifier = modifier
                            .fillMaxWidth()
                            .height(IntrinsicSize.Min)
                            .height(480.dp),
                        state = scrollState
                    ) {
                        items((0..23).toList()) { time ->
                            CalendarTimeItem(time)
                        }
                    }
                }
            }
        }
    }

    LaunchedEffect(key1 = isExpanded) {
        scrollState.scrollToItem(index = 23)
    }
}

data class Date(
    val day: String,
    val date: String
)

@Preview(showBackground = true)
@Composable
fun CalendarScreenPreview() {
    CalendarScreen()
}