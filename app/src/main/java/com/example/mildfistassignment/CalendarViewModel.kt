package com.example.mildfistassignment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class CalendarViewModel @Inject constructor(
): ViewModel() {
    private val _selectedDate = MutableStateFlow<LocalDate>(LocalDate.now())
    val selectedDate get() = _selectedDate.asStateFlow()

    fun updateSelectedDate(date: LocalDate) {
        viewModelScope.launch {
            _selectedDate.value = date
        }
    }

    fun initDateToToday() {
        viewModelScope.launch {
            _selectedDate.value = LocalDate.now()
        }
    }

}