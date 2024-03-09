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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.mildfistassignment.component.DateListItem
import com.example.mildfistassignment.component.HorizontalCalendar
import com.example.mildfistassignment.component.MainTopBar
import com.example.mildfistassignment.ui.theme.White
import com.example.mildfistassignment.util.getWeeksOfMonth
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.time.LocalDate

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CalendarScreen(
    navController: NavController = rememberNavController(),
    modifier: Modifier = Modifier,
    viewModel: CalendarViewModel = hiltViewModel()
) {
    val selectedDate by viewModel.selectedDate.collectAsState()
    val totalWeeks = getWeeksOfMonth(selectedDate.year, selectedDate.monthValue, selectedDate.month.maxLength())
    val selectedWeeks = getWeeksOfMonth(selectedDate.year, selectedDate.monthValue, selectedDate.dayOfMonth)

    val pagerState = rememberPagerState(pageCount = {totalWeeks}, initialPage = selectedWeeks-1)

    var onClickedTodayButton by remember { mutableStateOf(false) }
    var isExpanded by rememberSaveable { mutableStateOf(false) }
    var showCalendarBottomSheet by remember { mutableStateOf(false) }

    Scaffold(
        modifier = modifier
            .fillMaxSize(),
        topBar = {
            MainTopBar(
                title = selectedDate.monthValue.toString().padStart(2,'0') + "월",
                titleIcon = true,
                enableExpandButton = true,
                onClickExpandButton = {
                    isExpanded = !isExpanded
                },
                onClickTitle = {
                    showCalendarBottomSheet = true
                },
                enableBackButton = false
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
            shape = RectangleShape
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
                    viewModel = viewModel,
                    isExpanded = isExpanded,
                    onClickDate = { clickedDate ->
                        viewModel.updateSelectedDate(clickedDate)
                    },
                    onClickedTodayButton = {
                        viewModel.initDateToToday()
                        onClickedTodayButton = true
                    },
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.size(24.dp))

                if (isExpanded) {
                    DateList(
                        navigateToCalendarDetailScreen = {
                            navController.navigate(Destination.CALENDAR_DETAIL.name)
                        }
                    )
                }
            }
        }
    }

    if (showCalendarBottomSheet) {
        CalendarBottomSheet(
            onDismissRequest = {showCalendarBottomSheet = false},
            onClickedTodayButton = {
                viewModel.initDateToToday()
                onClickedTodayButton = true
            },
            viewModel = viewModel
        )
    }

    LaunchedEffect(key1 = selectedDate) {
        pagerState.animateScrollToPage(
            page = getWeeksOfMonth(selectedDate.year, selectedDate.monthValue, selectedDate.dayOfMonth)-1,
            animationSpec = spring(stiffness = 1000f)
        )
    }

    LaunchedEffect(key1 = onClickedTodayButton) {
        if (onClickedTodayButton) {
            pagerState.animateScrollToPage(
                page = getWeeksOfMonth(selectedDate.year, selectedDate.monthValue, selectedDate.dayOfMonth) -1,
                animationSpec = spring(stiffness = 1000f)
            )
            onClickedTodayButton = false
        }
    }
}

@Composable
fun DateList(
    navigateToCalendarDetailScreen: () -> Unit,
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
            DateListItem(
                time = time,
                onClickDate = navigateToCalendarDetailScreen
            )
        }
    }

    LaunchedEffect(key1 = true) {
        scrollState.scrollToItem(index = 23)
    }
}

@Preview(showBackground = true)
@Composable
fun CalendarScreenPreview() {
//    CalendarScreen()
}