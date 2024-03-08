package com.example.mildfistassignment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mildfistassignment.model.CalendarDataSource
import com.example.mildfistassignment.model.CalendarUiModel
import com.example.mildfistassignment.util.getWeeksOfMonth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CalendarViewModel @Inject constructor(
    private val dataSource: CalendarDataSource,
): ViewModel() {
    private val _calendarUiModel = MutableStateFlow(dataSource.getData(lastSelectedDate = dataSource.today))
    val calendarUiModel get() = _calendarUiModel.asStateFlow()

    val selectedWeeks = MutableStateFlow<Int>(0)
    val totalWeeks = MutableStateFlow<Int>(0)

    init {
        collectingSelectedDate()
    }

    fun updateSelectedDate(date: CalendarUiModel.Date) {
        viewModelScope.launch {
            _calendarUiModel.value = _calendarUiModel.value.copy(
                selectedDate = date,
                visibleDates = _calendarUiModel.value.visibleDates.map {
                    it.copy(
                        isSelected = it.date.isEqual(date.date)
                    )
                }
            )
        }
    }

    fun initDateToToday() {
        viewModelScope.launch {
            _calendarUiModel.value = dataSource.getData(
                lastSelectedDate = dataSource.today
            )
        }
    }

    fun swipeCalendar(swipe: Swipe) {
        when(swipe) {
            Swipe.LEFT -> {
                _calendarUiModel.value = dataSource.getData(
                    startDate = _calendarUiModel.value.startDate.date.minusDays(1),
                    lastSelectedDate = _calendarUiModel.value.selectedDate.date
                )
            }
            Swipe.RIGHT -> {
                _calendarUiModel.value = dataSource.getData(
                    startDate = _calendarUiModel.value.endDate.date.plusDays(2),
                    lastSelectedDate = _calendarUiModel.value.selectedDate.date
                )
            }
        }
    }

    private fun collectingSelectedDate() {
        viewModelScope.launch {
            _calendarUiModel.collectLatest {
                selectedWeeks.value = getWeeksOfMonth(
                    _calendarUiModel.value.selectedDate.date.year,
                    _calendarUiModel.value.selectedDate.date.monthValue,
                    _calendarUiModel.value.selectedDate.date.dayOfMonth
                )
                totalWeeks.value = getWeeksOfMonth(
                    _calendarUiModel.value.selectedDate.date.year,
                    _calendarUiModel.value.selectedDate.date.monthValue,
                    _calendarUiModel.value.selectedDate.date.month.maxLength()
                )
            }
        }
    }
}

enum class Swipe {
    LEFT, RIGHT
}