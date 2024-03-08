package com.example.mildfistassignment

import androidx.lifecycle.ViewModel
import com.example.mildfistassignment.model.CalendarDataSource
import com.example.mildfistassignment.model.CalendarUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class CalendarViewModel @Inject constructor(
    val dataSource: CalendarDataSource,
//    val calendarUiModel: CalendarUiModel
): ViewModel() {
    private val _calendarUiModel = MutableStateFlow(dataSource.getData(lastSelectedDate = dataSource.today))
    val calendarUiModel get() = _calendarUiModel.asStateFlow()


}