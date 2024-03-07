package com.example.mildfistassignment.model

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

data class CalendarUiModel(
    val selectedDate: Date, // 사용자가 선택한 날짜. 기본적으로 Today 입니다.
    val visibleDates: List<Date> // 화면에 표시되는 날짜
) {

    val startDate: Date = visibleDates.first() // 표시되는 날짜 중 첫 번째 날짜
    val endDate: Date = visibleDates.last() // 표시되는 날짜 중 마지막 날짜

    data class Date(
        val date: LocalDate,
        val isSelected: Boolean,
        val isToday: Boolean
    ) {
        /** 날짜 형식을 "월", "화"로 변환 **/
        val day: String = date.format(DateTimeFormatter.ofPattern( "E").withLocale(Locale.KOREAN))
    }
}