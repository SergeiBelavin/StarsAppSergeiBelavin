package com.example.retrofitoff.data.repository

import android.util.Log
import java.time.DayOfWeek
import java.util.*
import kotlin.collections.ArrayList
import kotlin.time.Duration.Companion.days
import kotlin.time.days

class UniqueDate() {

    fun getUniqueArrayList(range: Int): ArrayList<Int> {
        val uniqDaysList = ArrayList<Int>()

        when (range) {
            14 -> {
                val calendarWeek = Calendar.getInstance()
                calendarWeek.get(Calendar.DAY_OF_YEAR)
                val dateTimeNow = calendarWeek.firstDayOfWeek
                val dayNum = dateTimeNow
                var dayWeek = 0


                when (dayNum) {
                    1 -> dayWeek = 7
                    2 -> dayWeek = 1
                    3 -> dayWeek = 2
                    4 -> dayWeek = 3
                    5 -> dayWeek = 4
                    6 -> dayWeek = 5
                    7 -> dayWeek = 6
                }
                Log.d("DATE_TIME_NOW", "${dayNum}")

                for (i in 0 until dayWeek) {
                    val calendar = Calendar.getInstance()
                    calendar.add(Calendar.DAY_OF_YEAR, -i)
                    val dayAgo = calendar.time

                    uniqDaysList.add(getUniqueDate(dayAgo))
                    calendar.clear()
                }
            }
            30 -> {
                val calendarMonth = Calendar.getInstance()
                calendarMonth.get(Calendar.DAY_OF_MONTH)
                val dateTimeNow = calendarMonth.time
                val dateDay = dateTimeNow.date
                Log.d("DATE_DAY_MONTH", "${dateDay}")

                for (i in 0 until dateDay) {
                    val calendar = Calendar.getInstance()
                    calendar.add(Calendar.DAY_OF_YEAR, -i)
                    val dayAgo = calendar.time
                    uniqDaysList.add(getUniqueDate(dayAgo))
                    calendar.clear()
                }
                calendarMonth.clear()
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
                    uniqDaysList.add(getUniqueDate(dayAgo))
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