package com.example.mildfistassignment

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mildfistassignment.model.CalendarUiModel
import com.example.mildfistassignment.ui.theme.Black
import com.example.mildfistassignment.ui.theme.Gray
import com.example.mildfistassignment.ui.theme.LightGray
import com.example.mildfistassignment.ui.theme.Orange
import com.example.mildfistassignment.ui.theme.White
import com.example.mildfistassignment.util.toCalendarTitle
import com.holix.android.bottomsheetdialog.compose.BottomSheetDialog
import com.holix.android.bottomsheetdialog.compose.BottomSheetDialogProperties
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.util.Locale

@Composable
fun CalendarBottomSheet(
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: CalendarViewModel = hiltViewModel()
) {
    val calendarUiModel by viewModel.calendarUiModel.collectAsState()

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
            CalendarHeader(
                title = toCalendarTitle(
                    year = calendarUiModel.selectedDate.date.year,
                    month = calendarUiModel.selectedDate.date.monthValue
                ),
                onClickTodayButton = {
                    viewModel.initDateToToday()
                    onDismissRequest()
                }
            )

            HorizontalCalendar(
                calendarUiModel = calendarUiModel,
                onSelectedDate = {
                    viewModel.updateSelectedDate(
                        CalendarUiModel.Date(
                            isSelected = true,
                            isToday = it.isEqual(LocalDate.now()),
                            date = it
                        )
                    )
                    onDismissRequest()
                }
            )
        }
    }
}

@Composable
fun CalendarHeader(
    modifier: Modifier = Modifier,
    title: String,
    onClickTodayButton: () -> Unit
) {
    Box(modifier = modifier.fillMaxWidth()) {
        Text(
            modifier = modifier.align(Alignment.Center),
            text = title,
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
                    .align(Alignment.Center)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null,
                        onClick = onClickTodayButton
                    ),
                text = "오늘로",
                fontSize = 12.sp,
                color = Gray
            )
        }
    }
    Spacer(modifier = Modifier.size(20.dp))

    Row(modifier) {
        val dayOfWeek = listOf("일","월","화","수","목","금","토")
        dayOfWeek.forEach { day ->
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
                    .weight(1f),
                text = day,
                textAlign = TextAlign.Center,
                color = Gray,
                fontSize = 13.sp
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HorizontalCalendar(
    calendarUiModel: CalendarUiModel,
    onSelectedDate: (LocalDate) -> Unit,
    config: HorizontalCalendarConfig = HorizontalCalendarConfig(),
    modifier: Modifier = Modifier
) {
    val initialPage = (calendarUiModel.selectedDate.date.year - config.yearRange.first) * 12 + calendarUiModel.selectedDate.date.monthValue - 1
    val pageCount = (config.yearRange.last - config.yearRange.first) * 12
    var currentMonth by remember { mutableStateOf(YearMonth.now()) }
    var currentPage by remember { mutableIntStateOf(initialPage) }

    val pagerState = rememberPagerState(pageCount = {pageCount}, initialPage = initialPage)

    LaunchedEffect(pagerState.currentPage) {
        val addMonth = (pagerState.currentPage - currentPage).toLong()
        currentMonth = currentMonth.plusMonths(addMonth)
        currentPage = pagerState.currentPage
    }

    HorizontalPager(
        state = pagerState
    ) { page ->
        val date = LocalDate.of(
            config.yearRange.first + page / 12,
            page % 12 + 1,
            1
        )
//        if (page in pagerState.currentPage - 1..pagerState.currentPage + 1) { // 페이징 성능 개선을 위한 조건문
        CalendarMonthItem(
            calendarUiModel = calendarUiModel,
            currentDate = date,
            selectedDate = calendarUiModel.selectedDate.date,
            onSelectedDate = {
                onSelectedDate(it)
            }
        )
//        }
    }
}

@Composable
fun CalendarMonthItem(
    calendarUiModel: CalendarUiModel,
    currentDate: LocalDate,
    selectedDate: LocalDate,
    onSelectedDate: (LocalDate) -> Unit,
    modifier: Modifier = Modifier
) {
    val lastDay by remember { mutableIntStateOf(currentDate.lengthOfMonth()) }
    val firstDayOfWeek by remember { mutableIntStateOf(currentDate.dayOfWeek.value) }
    val days by remember { mutableStateOf(IntRange(1, lastDay).toList()) }

    Column(modifier = modifier.fillMaxWidth()) {
        LazyVerticalGrid(
            modifier = modifier.fillMaxWidth(),
            columns = GridCells.Fixed(7),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            for (i in 1 until firstDayOfWeek + 1) { // 처음 날짜가 시작하는 요일 전까지 빈 박스 생성, 일요일부터 시작!
                item {
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                    )
                }
            }
            items(items = days) { day ->
                val date = currentDate.withDayOfMonth(day)

                CalendarDay(
                    date = date,
                    isSelected = date == selectedDate,
                    onSelectedDate = {
                        onSelectedDate(it)
                    }
                )
            }
        }
    }
}

@Composable
fun CalendarDay(
    modifier: Modifier = Modifier,
    date: LocalDate,
    isSelected: Boolean,
    onSelectedDate: (LocalDate) -> Unit
) {
    Box(
        modifier = Modifier
            .padding(4.dp),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size(32.dp)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = {
                        onSelectedDate(date)
                    }
                )
                .background(
                    color = if (isSelected) Orange else Color.Transparent,
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = date.dayOfMonth.toString(),
                color = if (isSelected) White else Black
            )
        }
    }
}

data class HorizontalCalendarConfig(
    val yearRange: IntRange = IntRange(1990, 2025),
    val locale: Locale = Locale.KOREAN
)

@Preview(showBackground = true)
@Composable
fun CalendarBottomSheetPreview() {
    CalendarBottomSheet(onDismissRequest = {})
}