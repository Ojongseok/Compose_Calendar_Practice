package com.example.mildfistassignment

import android.util.Log
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mildfistassignment.model.CalendarDataSource
import com.example.mildfistassignment.ui.theme.White
import kotlinx.coroutines.flow.collectLatest
import java.time.YearMonth
import java.time.temporal.Temporal
import java.time.temporal.WeekFields
import java.util.Calendar


@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun CalendarScreen(
    modifier: Modifier = Modifier
) {
    var isExpanded by remember { mutableStateOf(false) }

    val dataSource = CalendarDataSource()
    var calendarUiModel by remember {
        mutableStateOf(dataSource.getData(lastSelectedDate = dataSource.today))
    }

    val selectedWeeks = getWeeksOfMonth(
        calendarUiModel.selectedDate.date.year,
        calendarUiModel.selectedDate.date.monthValue,
        calendarUiModel.selectedDate.date.dayOfMonth
    )
    val totalWeeks = getWeeksOfMonth(
        calendarUiModel.selectedDate.date.year,
        calendarUiModel.selectedDate.date.monthValue,
        calendarUiModel.selectedDate.date.month.maxLength()
    )
    val pagerState = rememberPagerState(initialPage = selectedWeeks-1)
    Log.d("taag selectedWeeks", selectedWeeks.toString())
    Log.d("taag totalWeeks", totalWeeks.toString())

//    val lastDay = getWeeksOfMonth(
//        calendarUiModel.selectedDate.date.year,
//        calendarUiModel.selectedDate.date.monthValue,
//        calendarUiModel.selectedDate.date.month.maxLength()
//    )
//    Log.d("taag", lastDay.toString())

    Scaffold(
        modifier = modifier
            .fillMaxSize(),
        topBar = {
            MainTopBar(
                month = calendarUiModel.selectedDate.date.monthValue.toString(),
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
                Box(
                    modifier = modifier
                        .fillMaxWidth()
                        .pointerInput(Unit) {
                            detectHorizontalDragGestures { _, dragAmount ->
                                if (dragAmount > 0) {

                                } else {

                                }
                            }
                        }
                ) {

                }
                HorizontalPager(
                    state = pagerState,
                    pageCount = totalWeeks
                ) {
                    LazyRow(
                        modifier = modifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        userScrollEnabled = false
                    ) {
                        if (isExpanded) {
                            item {
                                TodayButton(
                                    modifier = modifier.weight(1f)
                                )
                            }
                        }

                        items(calendarUiModel.visibleDates) { date ->
                            HorizontalDateItem(
                                modifier = modifier
                                    .weight(1f),
                                date = date,
                                onClickDate = { clickedDate ->
                                    calendarUiModel = calendarUiModel.copy(
                                        selectedDate = clickedDate,
                                        visibleDates = calendarUiModel.visibleDates.map {
                                            it.copy(
                                                isSelected = it.date.isEqual(clickedDate.date)
                                            )
                                        }
                                    )
                                }
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.size(24.dp))

                if (isExpanded) {
                    DateTimeList()
                }
            }
        }
    }

    LaunchedEffect(key1 = pagerState) {
        var prevPage = pagerState.initialPage
        snapshotFlow { pagerState.currentPage }.collectLatest {
            if (it < prevPage) {
                calendarUiModel = dataSource.getData(
                    startDate = calendarUiModel.startDate.date.minusDays(1),
                    lastSelectedDate = calendarUiModel.selectedDate.date
                )
            } else if (it > prevPage) {
                calendarUiModel = dataSource.getData(
                    startDate = calendarUiModel.endDate.date.plusDays(2),
                    lastSelectedDate = calendarUiModel.selectedDate.date
                )
            }
//            Log.d("taag", (calendarUiModel.visibleDates.first().date.monthValue == dataSource.today.monthValue).toString())
            // 이전을 조회할 수 있는 경우
//            if (calendarUiModel.visibleDates.first().date.monthValue == dataSource.today.monthValue) {
//                calendarUiModel = dataSource.getData(
//                    startDate = calendarUiModel.startDate.date.minusDays(1),
//                    lastSelectedDate = calendarUiModel.selectedDate.date
//                )
//            } else {
//
//            }
            prevPage = it
        }
    }

}

@Composable
fun DateTimeList(
    modifier: Modifier = Modifier
) {
    val scrollState = rememberLazyListState()

    LazyColumn(
        modifier = modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .height(480.dp),
        state = scrollState
    ) {
        items((0..23).toList()) { time ->
            DateTimeItem(time)
        }
    }

    LaunchedEffect(key1 = true) {
        scrollState.scrollToItem(index = 23)
    }
}

fun getWeeksOfMonth(
    year: Int,
    month: Int,
    day: Int
): Int {
    val calendar = Calendar.getInstance()
    calendar.set(year, month-1, day)
    return calendar.get(Calendar.WEEK_OF_MONTH)
}

@Preview(showBackground = true)
@Composable
fun CalendarScreenPreview() {
    CalendarScreen()
}