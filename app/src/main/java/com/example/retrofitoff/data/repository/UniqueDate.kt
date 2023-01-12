package com.example.retrofitoff.data.repository

import android.os.Build.VERSION_CODES.M
import android.util.Log
import java.util.*
import kotlin.collections.ArrayList
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.milliseconds

class UniqueDate {

    fun getUniqueArrayList(range: Int, date: Int): ArrayList<Long> {
        val uniqDaysList = ArrayList<Long>()
        var weekRange = date*7

        if(date == 0) {
            weekRange = 7
        }


        Log.d("LIST_WEEK_DATE++", "$weekRange")

        when (range) {

            14 -> {
                uniqDaysList.clear()
                val calWeek = Calendar.getInstance()
                val day = calWeek.time.date - weekRange
                calWeek.set(Calendar.DATE, day)
                calWeek.set(Calendar.HOUR, -12)
                calWeek.set(Calendar.MINUTE, 0)
                calWeek.set(Calendar.SECOND, 0)
                calWeek.set(Calendar.MILLISECOND, 0)

                Log.d("LIST_WEEK_CALENDAR_SET","${calWeek.time.date}")
                Log.d("LIST_WEEK_CALENDAR_SET","${calWeek.time}")
                Log.d("LIST_WEEK_CALENDAR_SET","${calWeek.timeInMillis}")
                var num = 0
                for (i in 0..6) {
                    calWeek.add(Calendar.DAY_OF_YEAR, +1)
                    val dateUnix = calWeek.timeInMillis
                    uniqDaysList.add(dateUnix)
                }
                Log.d("LIST_WEEK_DAY_UNIQ", "${uniqDaysList}")
            }
            30 -> {

                val calMonth = Calendar.getInstance()
                calMonth.set(Calendar.MONTH, -date)
                calMonth.set(Calendar.HOUR, 0)
                calMonth.set(Calendar.MINUTE, 0)
                calMonth.set(Calendar.SECOND, 0)
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
                calYear.set(Calendar.YEAR, -date)
                calYear.set(Calendar.HOUR, 0)
                calYear.set(Calendar.MINUTE, 0)
                calYear.set(Calendar.SECOND, 0)

                val yearSize = calYear.getActualMaximum(Calendar.DAY_OF_YEAR)
                uniqDaysList.clear()

                for (i in 0 until yearSize) {
                    uniqDaysList.add(calYear.timeInMillis.toString().replaceRange(9..12, "0000").toLong())
                }

                Log.d("TEST_YEAR", "${calYear.time}")

            }
        }
        return uniqDaysList
    }

}