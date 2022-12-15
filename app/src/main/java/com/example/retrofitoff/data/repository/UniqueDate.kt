package com.example.retrofitoff.data.repository

import android.util.Log
import java.text.SimpleDateFormat
import java.time.DayOfWeek
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList
import kotlin.time.Duration.Companion.days
import kotlin.time.days

class UniqueDate() {

    fun getUniqueArrayList(range: Int, date: Int): ArrayList<Long> {
        val uniqDaysList = ArrayList<Long>()
        var dateFor = date
        val formatDate = SimpleDateFormat("yyyy-MM-dd")
        when (range) {

            14 -> {

                for (i in 0..6) {
                    val calendarWeek = Calendar.getInstance()
                    calendarWeek.add(Calendar.DAY_OF_YEAR, - dateFor)
                    dateFor++
                    val dateTimeNow = calendarWeek.timeInMillis
                    uniqDaysList.add(dateTimeNow)
                }

            }
            30 -> {

                val calendarMonth = Calendar.getInstance()
                calendarMonth.get(Calendar.MONTH - dateFor)
                val dateTimeNow = calendarMonth.time
                val dateTimeNowMonth = dateTimeNow.month
                var monthDaySize = 0

                if(dateTimeNow.year % 4 == 0) {
                    monthDaySize = when (dateTimeNowMonth) {
                        2 -> 29
                        4 -> 30
                        6 -> 30
                        9 -> 30
                        11 -> 30
                        else -> 31
                    }
                }
                if(dateTimeNow.year % 4 != 0) {
                    monthDaySize = when (dateTimeNowMonth) {
                        2 -> 28
                        4 -> 30
                        6 -> 30
                        9 -> 30
                        11 -> 30
                        else -> 31
                    }
                }

                var firstDayMonth = monthDaySize - dateTimeNow.date


            }
            60 -> {
                val calendarMonth = Calendar.getInstance()
                calendarMonth.get(Calendar.DAY_OF_YEAR)
                val dateTimeNow = calendarMonth.time
                for (i in 0 until dateTimeNow.month) {
                    val calendar = Calendar.getInstance()
                    calendar.add(Calendar.YEAR, -i)
                    Log.d("DATE_YEAR", "${calendar}")
                    val dayAgo = calendar.time
                    //uniqDaysList.add(getUniqueDate(dayAgo))
                    calendar.clear()
                }

            }
        }
        Log.d("DATE_DAYS_LIST", "${uniqDaysList}")
        return uniqDaysList

    }

    fun getUniqueDate(date: Date): Int {
        return date.date + 31 * date.month * date.year * 1000
    }

}