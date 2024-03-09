package com.example.mildfistassignment.util

import java.util.Calendar

fun getWeeksOfMonth(year: Int, month: Int, day: Int): Int {
    val calendar = Calendar.getInstance()
    calendar.set(year, month-1, day)
    return calendar.get(Calendar.WEEK_OF_MONTH)
}

fun toDateString(month: Int, day: Int, dayOfWeeks: String): String {
    return "${month.toString().padStart(2,'0')}월 ${day.toString().padStart(2,'0')}일 (${dayOfWeeks})"
}

fun toCalendarTitle(year: Int, month: Int): String {
    return "${year}년 ${month.toString().padStart(2,'0')}월"
}