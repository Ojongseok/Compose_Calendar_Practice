package com.example.mildfistassignment

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.mildfistassignment.component.DateListItem
import com.example.mildfistassignment.component.HorizontalCalendar
import com.example.mildfistassignment.component.MainTopBar
import com.example.mildfistassignment.model.CalendarDataSource
import com.example.mildfistassignment.ui.theme.White
import kotlinx.coroutines.flow.collectLatest
import java.util.Calendar


@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun CalendarScreen(
    navController: NavController = rememberNavController(),
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

    var onClickedTodayButton by remember { mutableStateOf(false) }

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
                HorizontalCalendar(
                    pagerState = pagerState,
                    totalWeeks = totalWeeks,
                    calendarUiModel = calendarUiModel,
                    isExpanded = isExpanded,
                    onClickDate = { clickedDate ->
                        calendarUiModel = calendarUiModel.copy(
                            selectedDate = clickedDate,
                            visibleDates = calendarUiModel.visibleDates.map {
                                it.copy(
                                    isSelected = it.date.isEqual(clickedDate.date)
                                )
                            }
                        )
                    },
                    onClickedTodayButton = {
                        calendarUiModel = dataSource.getData(lastSelectedDate = dataSource.today)
                        onClickedTodayButton = true
                    },
                    modifier = Modifier.weight(1f)
                )

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
            if (!onClickedTodayButton) {
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
            }
            prevPage = it
        }
    }

    LaunchedEffect(key1 = onClickedTodayButton) {
        if (onClickedTodayButton) {
            pagerState.animateScrollToPage(
                page = selectedWeeks-1,
                animationSpec = spring(stiffness = 1000f)
            )
            onClickedTodayButton = false
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
            DateListItem(time)
        }
    }

    LaunchedEffect(key1 = true) {
        scrollState.scrollToItem(index = 23)
    }
}

fun getWeeksOfMonth(year: Int, month: Int, day: Int): Int {
    val calendar = Calendar.getInstance()
    calendar.set(year, month-1, day)
    return calendar.get(Calendar.WEEK_OF_MONTH)
}

@Preview(showBackground = true)
@Composable
fun CalendarScreenPreview() {
    CalendarScreen()
}