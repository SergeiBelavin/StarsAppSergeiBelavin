package com.example.retrofitoff.ui.chart

import android.util.Log
import java.util.*
import kotlin.collections.ArrayList

class ChartSorting {
    fun sortingDays(range: Int,
                    dateResponseList: List<Long>
    ) {

        var daysNumber = 60
        var daysList = ArrayList<Long>()

        Log.d("sortList", "$dateResponseList")

        var sectorsNumber = 0
        val calendar = Calendar.getInstance()

        for (i in 0 until daysNumber) {
            calendar.add(Calendar.DAY_OF_YEAR, -i)
            val daysUnix = calendar.timeInMillis
            daysList.add(daysUnix)
        }
        Log.d("Unix", "$daysList")

        when (daysNumber) {
            60 -> {

                sectorsNumber = 10

                val sector1 = ArrayList<List<Long>>()
                val sector2 = ArrayList<List<Long>>()
                val sector3 = ArrayList<List<Long>>()
                val sector4 = ArrayList<List<Long>>()
                val sector5 = ArrayList<List<Long>>()
                val sector6 = ArrayList<List<Long>>()
                val sector7 = ArrayList<List<Long>>()
                val sector8 = ArrayList<List<Long>>()
                val sector9 = ArrayList<List<Long>>()
                val sector10 = ArrayList<List<Long>>()

                for (i in 0 until 6) {sector1.add(listOf(daysList[i]))}
                Log.d("ggg", "$sector1")

                for (i in 6 until 12) {sector2.add(listOf(daysList[i]))}
                Log.d("ggg1", "$sector2")

                for (i in 12 until 18) {sector3.add(listOf(daysList[i]))}
                Log.d("ggg3", "$sector3")

                for (i in 18 until 24) {sector4.add(listOf(daysList[i]))}
                Log.d("ggg4", "$sector4")

                for (i in 24 until 30) {sector5.add(listOf(daysList[i]))}
                Log.d("ggg5", "$sector5")

                for (i in 30 until 36) {sector6.add(listOf(daysList[i]))}
                Log.d("ggg6", "$sector6")

                for (i in 36 until 42) {sector7.add(listOf(daysList[i]))}
                Log.d("ggg7", "$sector7")

                for (i in 42 until 48) {sector8.add(listOf(daysList[i]))}
                Log.d("ggg8", "$sector8")

                for (i in 48 until 54) {sector9.add(listOf(daysList[i]))}
                Log.d("ggg9", "$sector9")

                for (i in 54 until 60) {sector10.add(listOf(daysList[i]))}
                Log.d("ggg10", "$sector10")

                for (i in 0 until sector1.size) {

                }

                for (i in 0 until sector2.size) {

                }

                for (i in 0 until sector3.size) {

                }

                for (i in 0 until sector4.size) {

                }

                for (i in 0 until sector5.size) {

                }

                for (i in 0 until sector6.size) {

                }

                for (i in 0 until sector7.size) {

                }

                for (i in 0 until sector8.size) {

                }

                for (i in 0 until sector9.size) {

                }

                for (i in 0 until sector10.size) {

                }

            }

            30 -> {
                sectorsNumber = 6
                var sector1 = ArrayList<List<Long>>()
                var sector2 = ArrayList<List<Long>>()
                var sector3 = ArrayList<List<Long>>()
                var sector4 = ArrayList<List<Long>>()
                var sector5 = ArrayList<List<Long>>()
                var sector6 = ArrayList<List<Long>>()
            }

            14 -> {
                sectorsNumber = 14
                var sector1 = ArrayList<List<Long>>()
                var sector2 = ArrayList<List<Long>>()
                var sector3 = ArrayList<List<Long>>()
                var sector4 = ArrayList<List<Long>>()
                var sector5 = ArrayList<List<Long>>()
                var sector6 = ArrayList<List<Long>>()
                var sector7 = ArrayList<List<Long>>()
                var sector8 = ArrayList<List<Long>>()
                var sector9 = ArrayList<List<Long>>()
                var sector10 = ArrayList<List<Long>>()
                var sector11 = ArrayList<List<Long>>()
                var sector12 = ArrayList<List<Long>>()
                var sector13 = ArrayList<List<Long>>()
                var sector14 = ArrayList<List<Long>>()
            }
        }
    }
}
