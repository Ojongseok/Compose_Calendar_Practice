package com.example.mildfistassignment.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.mildfistassignment.CalendarViewModel
import com.example.mildfistassignment.util.getWeeksOfMonth
import java.time.LocalDate

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HorizontalCalendar(
    pagerState: PagerState,
    viewModel: CalendarViewModel,
    isExpanded: Boolean,
    onClickDate: (LocalDate) -> Unit,
    onClickedTodayButton: () -> Unit,
    modifier: Modifier = Modifier
) {
    val selectedDate by viewModel.selectedDate.collectAsState()    // 선택된 날짜
    val totalWeeks = getWeeksOfMonth(selectedDate.year, selectedDate.monthValue, selectedDate.month.maxLength())   // 선택된 달이 몇 주인지
    val weekList = List<MutableList<LocalDate>>(totalWeeks) { mutableListOf() }    // 1주, 2주, 3주, 4주, 5주, 6주를 보관하는 리스트

    // 1일에서 마지막 일까지 n-1주차에 맞게 weekList에 저장
    for (i in 1..selectedDate.month.maxLength()) {
        val week = getWeeksOfMonth(selectedDate.year, selectedDate.monthValue, i)
        weekList[week-1].add(LocalDate.of(selectedDate.year, selectedDate.monthValue, i))
    }

    // 첫째주, 마지막주 비어있는 칸 채우기
    while(weekList[0].size != 7) {
        weekList[0].add(0, weekList[0][0].minusDays(1))
    }
    while(weekList[totalWeeks-1].size != 7) {
        weekList[totalWeeks-1].add(weekList[totalWeeks-1][weekList[totalWeeks-1].size-1].plusDays(1))
    }

    Box {
        HorizontalPager(
            state = pagerState,
        ) { page ->
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly,
                userScrollEnabled = false
            ) {
                if (isExpanded) {
                    item {
                        TodayButton(
                            modifier = modifier,
                            onClickTodayButton = onClickedTodayButton
                        )
                    }
                }

                items(items = weekList[page]) { date ->
                    HorizontalCalendarItem(
                        date = date,
                        selectedDate = selectedDate,
                        enableSelectedMonth = selectedDate.monthValue,
                        onClickDate = onClickDate
                    )
                }
            }
        }
    }
}
