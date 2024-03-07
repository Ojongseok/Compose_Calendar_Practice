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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.mildfistassignment.model.CalendarUiModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HorizontalCalendar(
    pagerState: PagerState,
    totalWeeks: Int,
    calendarUiModel: CalendarUiModel,
    isExpanded: Boolean,
    onClickDate: (CalendarUiModel.Date) -> Unit,
    onClickedTodayButton: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box {
        HorizontalPager(
            state = pagerState,
            pageCount = totalWeeks
        ) {
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

                items(calendarUiModel.visibleDates) { date ->
                    HorizontalCalendarItem(
                        modifier = modifier,
                        enableSelectedMonth = calendarUiModel.selectedDate.date.monthValue,
                        date = date,
                        onClickDate = onClickDate
                    )
                }
            }
        }
    }
}