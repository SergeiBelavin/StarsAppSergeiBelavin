package com.example.retrofitoff.data.repository

import android.util.Log
import java.util.*
import kotlin.collections.ArrayList

class UniqueDate {

    fun getUniqueArrayList(range: Int, date: Int): ArrayList<Int> {
        val uniqDaysList = ArrayList<Int>()
        val weekRange = date*7
        Log.d("NUM_WEEK_@!@!@!@", "${range}")
        when (range) {

            14 -> {
                val calWeek = Calendar.getInstance()
                calWeek.add(Calendar.DAY_OF_YEAR, -weekRange)
                calWeek.add(Calendar.HOUR, -calWeek.time.hours)
                calWeek.add(Calendar.MINUTE, -calWeek.time.minutes)
                calWeek.add(Calendar.SECOND, -calWeek.time.seconds)
                Log.d("TEST_WEEK1", "${calWeek.time}")
                Log.d("TEST_WEEK2", "${weekRange}")

                calWeek.add(Calendar.DAY_OF_YEAR, -calWeek.time.day+1)
                Log.d("TEST_WEEK3", "${calWeek.time}")
                var num = 0

                for (i in 0..6) {
                    calWeek.add(Calendar.DAY_OF_YEAR, +1)
                    val dateInt = calWeek.time
                    uniqDaysList.add(getUniqueDate(dateInt))
                }
                Log.d("TEST_WEEK4", "${calWeek.time}")

            }
            30 -> {

                val calMonth = Calendar.getInstance()

                calMonth.add(Calendar.MONTH, -date)
                calMonth.add(Calendar.DAY_OF_YEAR, -weekRange)
                calMonth.add(Calendar.HOUR, -calMonth.time.hours)
                calMonth.add(Calendar.MINUTE, -calMonth.time.minutes)
                calMonth.add(Calendar.SECOND, -calMonth.time.seconds)

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
                    uniqDaysList.add(UniqueDate().getUniqueDate(calMonth.time))
                }
                Log.d("TEST_MONTH", "${calMonth.time}")

            }

            60 -> {
                val calYear = Calendar.getInstance()
                calYear.add(Calendar.YEAR, -date)
                var yearSize = 0

                if (calYear.time.year % 4 == 0) {
                    yearSize = 366
                } else {
                    yearSize = 365
                }

                for (i in 0 until yearSize) {
                    uniqDaysList.add(UniqueDate().getUniqueDate(calYear.time))
                }

                Log.d("TEST_YEAR", "${calYear.time}")

            }
        }
        Log.d("TEST_1", "$uniqDaysList")
        Log.d("TEST_2", "${uniqDaysList.size}")
        Log.d("TEST_YEAR222", "${uniqDaysList}")
        return uniqDaysList

    }

    fun getUniqueDate(date: Date): Int {
        return date.date + 31 * date.month * date.year
    }

}