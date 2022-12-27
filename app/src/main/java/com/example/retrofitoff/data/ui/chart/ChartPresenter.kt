package com.example.retrofitoff.data.ui.chart

import Repository
import android.icu.util.Calendar
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.viewModelScope
import com.example.retrofitoff.data.repository.DateConverter
import com.example.retrofitoff.data.repository.UniqueDate
import com.example.retrofitoff.data.ui.main.EnumRange
import com.example.retrofitoff.model.StarGroup
import com.example.retrofitoff.model.StarGroupForChart
import kotlinx.coroutines.launch
import kotlinx.serialization.json.JsonNull.boolean
import moxy.InjectViewState
import moxy.MvpPresenter
import java.io.IOException
import java.util.Date

@InjectViewState
class ChartPresenter(private val chartRepo: Repository) : MvpPresenter<ChartView>() {
    var numDate = 0
    val responseList = ArrayList<Int>()
    var group = 0

    fun clickBackOrNext(
        num: Int,
        groupDate: EnumRange.Companion.GroupType,
        minusOrPlus: Boolean,
        firstQuest: Boolean,
    ): String? {

        if (firstQuest) {
            val calendar = java.util.Calendar.getInstance()
            return DateConverter.convertDateToTimestamp(calendar.time)
        }

        if (minusOrPlus) {
            numDate--
        } else {
            numDate++
        }
        Log.d("NUM_CHART", "$numDate")

        if (numDate < 0) {
            viewState.showError("Мы не можем смотреть в будущее :(")
            numDate = 0
            return ""
        }


        val calendarNumDate = java.util.Calendar.getInstance()

        when {
            numDate < 0 -> viewState.showError("Мы не можем смотреть в будущее :)")
        }

        when (groupDate) {

            EnumRange.Companion.GroupType.WEEK -> {
                group = 14
                calendarNumDate.add(java.util.Calendar.DAY_OF_YEAR, -numDate * 7)
                return DateConverter.convertDateToTimestamp(calendarNumDate.time)
            }

            EnumRange.Companion.GroupType.MONTH -> {
                group = 30
                calendarNumDate.add(java.util.Calendar.MONTH, -numDate)
                return DateConverter.convertDateToTimestamp(calendarNumDate.time)
            }

            EnumRange.Companion.GroupType.YEAR -> {
                group = 60
                calendarNumDate.add(java.util.Calendar.YEAR, -numDate)
                return DateConverter.convertDateToTimestamp(calendarNumDate.time)
            }
        }
    }

    suspend fun getReposStars(
        userName: String,
        repoName: String,
        groupType: EnumRange.Companion.GroupType,
    ): List<Int> {
        responseList.clear()
        try {
            val response: List<Int> =
                chartRepo.getChartDate(userName, repoName, groupType, numDate)

            responseList.addAll(response)
            Log.d("RESPONCE_LIST", "Exception: $responseList")

            return responseList

        } catch (e: IOException) {
            Log.d("EXCEPTION_CHART_VIEW", "Exception: $e")
            Log.d("EXCEPTION_CHART_VIEW", "Exception: ${e.message}")
            Log.d("EXCEPTION_CHART_VIEW", "Exception: ${e.localizedMessage?.hashCode()}")

            if (e.localizedMessage?.hashCode() == 964672022) {
                viewState.showError("Отсутствует подключение к интернету")
                viewState.startSending(true)
            }
            return responseList

        } catch (e: Exception) {
            Log.d("EXCEPTION_CHART_VIEW", "Exception: $e")
            Log.d("EXCEPTION_CHART_VIEW", "Exception: ${e.message}")
            Log.d("EXCEPTION_CHART_VIEW", "Exception: ${e.localizedMessage?.hashCode()}")

            if (e.localizedMessage?.hashCode() == -1358142879) {
                viewState.showError("Лимит запросов закончился")
                viewState.startSending(true)
            }
            return responseList
        }


    }

    fun dayForTheSchedule() {
        UniqueDate().getUniqueArrayList(group, numDate)

    }

}