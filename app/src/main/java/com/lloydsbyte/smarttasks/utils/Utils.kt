package com.lloydsbyte.smarttasks.utils

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

class Utils {

    val apiFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val userFormat = DateTimeFormatter.ofPattern("MMM dd yyyy")

    fun getTodaysDate(): String {
        return LocalDate.now().format(apiFormat)
    }

    fun convertToUserFormat(apiDate: String): String {
        return if (apiDate.isNotEmpty())LocalDate.parse(apiDate, apiFormat).format(userFormat) else "NA"
    }

    fun getDaysLeft(dueDate: String): String {
        return if (dueDate.isNotEmpty()){
            val taskDueDate = LocalDate.parse(dueDate, apiFormat)
            ChronoUnit.DAYS.between(LocalDate.now(), taskDueDate).toString()
        } else "NA"
    }

    fun getNextDay(dayApi: String): String {
        val day = LocalDate.parse(dayApi, apiFormat)
        val nextDay = day.plusDays(1)
        return nextDay.format(apiFormat)
    }

    fun getPreviousDay(dayApi: String): String {
        val day = LocalDate.parse(dayApi, apiFormat)
        val previousDay = day.minusDays(1)
        return previousDay.format(apiFormat)
    }
}