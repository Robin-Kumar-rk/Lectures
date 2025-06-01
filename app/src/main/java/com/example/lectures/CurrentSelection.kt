package com.example.lectures

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import kotlin.to
import kotlin.toString

fun getDefaultSelectedOption(): String {
    val calendar = Calendar.getInstance()
    val currentHour = calendar.get(Calendar.HOUR_OF_DAY)
    val currDay = getCurrentDay()
    return (if (currentHour >= 17
        || currDay == "SATURDAY" ||
        currDay == "SUNDAY") nextDay[currDay] else currDay).toString()
}

fun getCurrentDay(): String {
    val dayFormat = SimpleDateFormat("EEEE", Locale.getDefault())
    val day = dayFormat.format(Date())
    return day.uppercase()
}

val nextDay = mapOf(
    "MONDAY" to "TUESDAY",
    "TUESDAY" to "WEDNESDAY",
    "WEDNESDAY" to "THURSDAY",
    "THURSDAY" to "FRIDAY",
    "FRIDAY" to "MONDAY",
    "SATURDAY" to "MONDAY",
    "SUNDAY" to "MONDAY"
)