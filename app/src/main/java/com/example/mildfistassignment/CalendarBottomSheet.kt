package com.example.mildfistassignment

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mildfistassignment.ui.theme.Black
import com.example.mildfistassignment.ui.theme.Gray
import com.example.mildfistassignment.ui.theme.Orange
import com.example.mildfistassignment.ui.theme.White
import com.example.mildfistassignment.util.toCalendarTitle
import com.holix.android.bottomsheetdialog.compose.BottomSheetDialog
import java.time.LocalDate

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CalendarBottomSheet(
    onDismissRequest: () -> Unit,
    onClickedTodayButton: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: CalendarViewModel = hiltViewModel()
) {
    val selectedDate by viewModel.selectedDate.collectAsState()

    val initialPage = (selectedDate.year - CALENDAR_RANGE.firstYear) * 12 + selectedDate.monthValue - 1
    val pageCount = (CALENDAR_RANGE.lastYear - CALENDAR_RANGE.firstYear) * 12

    val pagerState = rememberPagerState(pageCount = {pageCount}, initialPage = initialPage)

    var currentMonth by remember { mutableStateOf(selectedDate) }
    var currentPage by remember { mutableIntStateOf(initialPage) }

    LaunchedEffect(key1 = pagerState.currentPage) {
        val addMonth = (pagerState.currentPage - currentPage).toLong()
        currentMonth = currentMonth.plusMonths(addMonth)
        currentPage = pagerState.currentPage
    }

    BottomSheetDialog(
        onDismissRequest = onDismissRequest
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
                title = toCalendarTitle(currentMonth.year, currentMonth.monthValue),
                onClickTodayButton = {
                    onClickedTodayButton()
                    onDismissRequest()
                }
            )
            CalendarInBottomSheet(
                pagerState = pagerState,
                selectedDate = selectedDate,
                onSelectedDate = {
                    viewModel.updateSelectedDate(it)
                    onDismissRequest()
                }
            )
        }
    }
}

@Composable
fun CalendarHeader(
    title: String,
    onClickTodayButton: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column {
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
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CalendarInBottomSheet(
    pagerState: PagerState,
    selectedDate: LocalDate,
    onSelectedDate: (LocalDate) -> Unit,
    modifier: Modifier = Modifier
) {

    HorizontalPager(
        state = pagerState
    ) { page ->
        val date = LocalDate.of(
            CALENDAR_RANGE.firstYear + page / 12,
            page % 12 + 1,
            1
        )

        CalendarMonth(
            selectedDate = selectedDate,
            currentDate = date,
            onSelectedDate = {
                onSelectedDate(it)
            }
        )
    }
}

@Composable
fun CalendarMonth(
    selectedDate: LocalDate,
    currentDate: LocalDate,
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
                    Box(modifier = Modifier.size(32.dp))
                }
            }

            items(items = days) { day ->
                val date = currentDate.withDayOfMonth(day)

                CalendarDay(
                    date = date,
                    isSelected = date.isEqual(selectedDate),
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
    date: LocalDate,
    isSelected: Boolean,
    onSelectedDate: (LocalDate) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = Modifier.padding(4.dp),
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

object CALENDAR_RANGE {
    const val firstYear = 2000
    const val lastYear = 2030
}

@Preview(showBackground = true)
@Composable
fun CalendarBottomSheetHeaderPreview() {
    CalendarHeader(
        title = "2024년 03월",
        onClickTodayButton = {}
    )
}

@Preview(showBackground = true)
@Composable
fun CalendarBottomSheetBodyPreview() {
    CalendarMonth(
        selectedDate = LocalDate.now(),
        currentDate = LocalDate.now(),
        onSelectedDate = {}
    )
}