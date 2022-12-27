package com.example.retrofitoff.data.repository

import android.util.Log
import java.util.*
import kotlin.collections.ArrayList
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.milliseconds

class UniqueDate {

    fun getUniqueArrayList(range: Int, date: Int): ArrayList<Long> {
        val uniqDaysList = ArrayList<Long>()
        val weekRange = date*7
        Log.d("NUM_WEEK_@!@!@!@", "${range}")
        when (range) {

            14 -> {
                uniqDaysList.clear()
                val calWeek = Calendar.getInstance()
                calWeek.add(Calendar.DAY_OF_YEAR, -weekRange)
                calWeek.add(Calendar.HOUR, -calWeek.time.hours)
                calWeek.add(Calendar.MINUTE, -calWeek.time.minutes)
                calWeek.add(Calendar.SECOND, -calWeek.time.seconds)
                calWeek.add(Calendar.MILLISECOND, -Calendar.MILLISECOND)
                calWeek.add(Calendar.DAY_OF_YEAR, -calWeek.time.day+1)

                Log.d("TEST_WEEK3", "${calWeek.time}")

                for (i in 0..6) {
                    calWeek.add(Calendar.DAY_OF_YEAR, +1)
                    val dateUnix = calWeek.timeInMillis.toString().replaceRange(9..12, "0000").toLong()
                    uniqDaysList.add(dateUnix)
                }
                Log.d("TEST_WEEK5", "${uniqDaysList}")

            }
            30 -> {

                val calMonth = Calendar.getInstance()

                calMonth.add(Calendar.MONTH, -date)
                calMonth.add(Calendar.HOUR, -calMonth.time.hours)
                calMonth.add(Calendar.MINUTE, -calMonth.time.minutes)
                calMonth.add(Calendar.SECOND, -calMonth.time.seconds)
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
                    calMonth.add(Calendar.HOUR, -calMonth.time.hours)
                    calMonth.add(Calendar.MINUTE, -calMonth.time.minutes)
                    calMonth.add(Calendar.SECOND, -calMonth.time.seconds)
                    uniqDaysList.add(calMonth.timeInMillis.toString().replaceRange(9..12, "0000").toLong())
                }
                Log.d("TEST_MONTH", "${calMonth.time}")
                Log.d("TEST_MONTH", "${uniqDaysList}")

            }

            60 -> {
                val calYear = Calendar.getInstance()
                calYear.add(Calendar.YEAR, -date)
                calYear.add(Calendar.HOUR, -calYear.time.hours)
                calYear.add(Calendar.MINUTE, -calYear.time.minutes)
                calYear.add(Calendar.SECOND, -calYear.time.seconds)
                var yearSize = 0
                uniqDaysList.clear()

                if (calYear.time.year % 4 == 0) {
                    yearSize = 366
                } else {
                    yearSize = 365
                }

                for (i in 0 until yearSize) {
                    uniqDaysList.add(calYear.timeInMillis.toString().replaceRange(9..12, "0000").toLong())
                }

                Log.d("TEST_YEAR", "${calYear.time}")

            }
        }
        return uniqDaysList

    }

    fun getUniqueDate(date: Date): Int {
        return date.date + 31 * date.month * date.year
    }

}