package com.example.retrofitoff.data.repository

import com.example.retrofitoff.ui.main.EnumRange
import java.util.*
import kotlin.collections.ArrayList

object RequiredDates {

    fun getUniqueArrayList(range: EnumRange.Companion.GroupType, date: Int): ArrayList<Long> {
        val uniqDaysList = ArrayList<Long>()
        var weekRange = date * 7

        if (date == 0) {
            weekRange = 7
        }

        when (range) {

            EnumRange.Companion.GroupType.WEEK -> {
                uniqDaysList.clear()
                val calWeek = Calendar.getInstance()
                val day = calWeek.time.date - weekRange
                resetMinutesHoursSeconds(calWeek, day, range, date)

                for (i in 0..6) {
                    calWeek.add(Calendar.DAY_OF_YEAR, +1)
                    val dateUnix = calWeek.timeInMillis
                    uniqDaysList.add(dateUnix)
                }
            }

            EnumRange.Companion.GroupType.MONTH -> {

                val calMonth = Calendar.getInstance()
                resetMinutesHoursSeconds(calMonth, 0, range, date)
                uniqDaysList.clear()
                val dateTimeNow = calMonth.time
                val dateTimeNowMonth = dateTimeNow.month
                var monthDaySize = 0

                if (dateTimeNow.year % 4 == 0) {
                    monthDaySize = when (dateTimeNowMonth) {
                        2 -> 29
                        4 -> 30
                        6 -> 30
                        9 -> 30
                        11 -> 30
                        else -> 31
                    }
                }
                if (dateTimeNow.year % 4 != 0) {
                    monthDaySize = when (dateTimeNowMonth) {
                        2 -> 28
                        4 -> 30
                        6 -> 30
                        9 -> 30
                        11 -> 30
                        else -> 31
                    }
                }

                for (i in 0 until monthDaySize) {
                    calMonth.add(Calendar.DAY_OF_YEAR, +1)
                    uniqDaysList.add(
                        calMonth.timeInMillis.toString().replaceRange(9..12, "0000").toLong()
                    )
                }
            }

            EnumRange.Companion.GroupType.YEAR -> {
                val calYear = Calendar.getInstance()
                resetMinutesHoursSeconds(calYear, 0, range, date)
                val yearSize = calYear.getActualMaximum(Calendar.DAY_OF_YEAR)
                uniqDaysList.clear()

                for (i in 0 until yearSize) {
                    uniqDaysList.add(
                        calYear.timeInMillis.toString().replaceRange(9..12, "0000").toLong()
                    )
                }
            }
        }
        return uniqDaysList
    }

    private fun resetMinutesHoursSeconds(
        calendar: Calendar,
        day: Int,
        range: EnumRange.Companion.GroupType,
        date: Int
    ): Calendar {

        when (range) {
            EnumRange.Companion.GroupType.WEEK -> calendar.set(Calendar.DATE, day)
            EnumRange.Companion.GroupType.MONTH -> calendar.set(Calendar.MONTH, -date)
            EnumRange.Companion.GroupType.YEAR -> calendar.set(Calendar.YEAR, -date)
        }

        calendar.set(Calendar.HOUR, -12)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)

        return calendar
    }

}