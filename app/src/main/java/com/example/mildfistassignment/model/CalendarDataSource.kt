package com.example.mildfistassignment.model

import java.time.DayOfWeek
import java.time.LocalDate
import java.time.temporal.ChronoUnit
import java.util.stream.Collectors
import java.util.stream.Stream
import javax.inject.Inject
import javax.inject.Singleton

class CalendarDataSource @Inject constructor() {
    val today: LocalDate
        get() {
            return LocalDate.now()
        }

    /** 일주일을 일요일 ~ 토요일로 설정 **/
    fun getData(
        startDate: LocalDate = today,
        lastSelectedDate: LocalDate
    ): CalendarUiModel {
        val firstDayOfWeek = startDate.with(DayOfWeek.SUNDAY).minusWeeks(1)      // 일요일 시작
        val endDayOfWeek = firstDayOfWeek.plusDays(7)
        val visibleDates = getDatesBetween(firstDayOfWeek, endDayOfWeek)
        return toUiModel(visibleDates, lastSelectedDate)
    }

    private fun getDatesBetween(
        startDate: LocalDate,
        endDate: LocalDate
    ): List<LocalDate> {
        val numOfDays = ChronoUnit.DAYS.between(startDate, endDate)
        return Stream.iterate(startDate) { date ->
            date.plusDays(1)
        }
            .limit(numOfDays)
            .collect(Collectors.toList())
    }

    private fun toUiModel(
        dateList: List<LocalDate>,
        lastSelectedDate: LocalDate
    ): CalendarUiModel {
        return CalendarUiModel(
            selectedDate = toItemUiModel(lastSelectedDate, true),
            visibleDates = dateList.map {
                toItemUiModel(it, it.isEqual(lastSelectedDate))
            },
        )
    }

    private fun toItemUiModel(date: LocalDate, isSelectedDate: Boolean) = CalendarUiModel.Date(
        isSelected = isSelectedDate,
        isToday = date.isEqual(today),
        date = date,
    )
}