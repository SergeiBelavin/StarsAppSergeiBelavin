package com.example.retrofitoff.data.ui.chart

import android.icu.util.Calendar
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.retrofitoff.data.repository.DateConverter
import com.example.retrofitoff.data.repository.Repository
import com.example.retrofitoff.data.ui.main.EnumRange
import moxy.InjectViewState
import moxy.MvpPresenter
import java.util.Date

@InjectViewState
class ChartPresenter(private val chartRepo: Repository) : MvpPresenter<ChartView>() {

    fun clickBackOrNext(
        num: Int,
        groupDate: EnumRange.Companion.GroupType,
    ): String? {

        val calendarNumDate = java.util.Calendar.getInstance()

        when {
            num < 0 -> viewState.showError("Мы не можем смотреть в будущее :)")
        }

        when (groupDate) {

            EnumRange.Companion.GroupType.WEEK -> {
                calendarNumDate.add(java.util.Calendar.DAY_OF_YEAR, -num * 7)
                return DateConverter.convertDateToTimestamp(calendarNumDate.time)
            }

            EnumRange.Companion.GroupType.MONTH -> {
                calendarNumDate.add(java.util.Calendar.MONTH, -num)
                return DateConverter.convertDateToTimestamp(calendarNumDate.time)
            }

            EnumRange.Companion.GroupType.YEAR -> {
                calendarNumDate.add(java.util.Calendar.YEAR, -num)
                return DateConverter.convertDateToTimestamp(calendarNumDate.time)
            }
        }


    }
}