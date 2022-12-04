package com.example.retrofitoff.data.repository

import java.util.*

class uniqueDate() {

    companion object {

        fun getUniqueArrayList(range: Int): ArrayList<Int> {
            val uniqDaysList = ArrayList<Int>()

            for (i in 0 until range) {
                val calendar = Calendar.getInstance()
                calendar.add(Calendar.DAY_OF_YEAR, -i)
                val dayAgo = calendar.time
                uniqDaysList.add(getUniqueDate(dayAgo))
                calendar.clear()
            }

            return uniqDaysList
        }

        fun getUniqueDate(date: Date): Int {
            return date.date + 31 * date.month * date.year * 1000
        }

    }

}