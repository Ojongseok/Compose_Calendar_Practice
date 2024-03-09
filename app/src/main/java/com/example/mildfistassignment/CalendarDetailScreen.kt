package com.example.mildfistassignment

import androidx.compose.animation.core.spring
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.mildfistassignment.component.DateDetailListItem
import com.example.mildfistassignment.component.HorizontalCalendar
import com.example.mildfistassignment.component.MainTopBar
import com.example.mildfistassignment.util.getWeeksOfMonth
import com.example.mildfistassignment.util.toDateString
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CalendarDetailScreen(
    navController: NavController = rememberNavController(),
    modifier: Modifier = Modifier,
    viewModel: CalendarViewModel = hiltViewModel()
) {
    val selectedDate by viewModel.selectedDate.collectAsState()
    val totalWeeks = getWeeksOfMonth(selectedDate.year, selectedDate.monthValue, selectedDate.month.maxLength())
    val selectedWeeks = getWeeksOfMonth(selectedDate.year, selectedDate.monthValue, selectedDate.dayOfMonth)

    val pagerState = rememberPagerState(pageCount = {totalWeeks}, initialPage = selectedWeeks-1)

    var showCalendarBottomSheet by remember { mutableStateOf(false) }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            MainTopBar(
                title = selectedDate.monthValue.toString().padStart(2,'0') + "월",
                titleIcon = true,
                enableBackButton = true,
                onClickBackButton = {
                    navController.popBackStack()
                },
                onClickTitle = {
                    showCalendarBottomSheet = true
                },
                enableExpandButton = false
            )
        }
    ) { paddingValues ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            HorizontalCalendar(
                pagerState = pagerState,
                viewModel = viewModel,
                isExpanded = false,
                onClickDate = { clickedDate ->
                    viewModel.updateSelectedDate(clickedDate)
                },
                onClickedTodayButton = {
                    viewModel.initDateToToday()
                },
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.size(24.dp))

            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .background(color = Color(0x1AB9BCBE))
            )

            DateDetailList(
                navController = navController,
                selectedDate = selectedDate
            )
        }
    }

    if (showCalendarBottomSheet) {
        CalendarBottomSheet(
            onDismissRequest = {showCalendarBottomSheet = false},
            viewModel = viewModel
        )
    }

    LaunchedEffect(key1 = selectedDate) {
        pagerState.animateScrollToPage(
            page = getWeeksOfMonth(selectedDate.year, selectedDate.monthValue, selectedDate.dayOfMonth)-1,
            animationSpec = spring(stiffness = 1000f)
        )
    }
}

@Composable
fun DateDetailList(
    navController: NavController,
    selectedDate: LocalDate,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberLazyListState()

    LazyColumn(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        state = scrollState
    ) {
        items((0..23).toList()) { time ->
            DateDetailListItem(
                time = time,
                onClickDate = {
                    val date = toDateString(
                        month = selectedDate.monthValue,
                        day = selectedDate.dayOfMonth,
                        dayOfWeeks = selectedDate.dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.KOREAN)
                    )
                    navController.navigate("${Destination.DATE.name}/${date}/${time}")
                }
            )
        }
    }

    LaunchedEffect(key1 = true) {
        scrollState.scrollToItem(index = 23)
    }
}

@Preview(showBackground = true)
@Composable
fun CalendarDetailScreenPreview() {
    CalendarDetailScreen()
}