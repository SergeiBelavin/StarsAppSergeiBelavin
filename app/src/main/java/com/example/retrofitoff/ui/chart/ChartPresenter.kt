package com.example.retrofitoff.ui.chart

import Repository
import android.provider.Settings.Secure.getString

import android.util.Log
import com.example.retrofitoff.R
import com.example.retrofitoff.data.entity.ChartListItem
import com.example.retrofitoff.data.repository.DateConverter
import com.example.retrofitoff.data.repository.RequiredDates
import com.example.retrofitoff.model.ChartList
import com.example.retrofitoff.ui.main.EnumRange
import moxy.InjectViewState
import moxy.MvpPresenter
import java.net.UnknownHostException

@InjectViewState
class ChartPresenter(private val chartRepo: Repository) : MvpPresenter<ChartView>() {
    var numDate = 0
    var group = EnumRange.Companion.GroupType.WEEK

    fun clickBackOrNext(
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
                group = EnumRange.Companion.GroupType.WEEK
                calendarNumDate.add(java.util.Calendar.DAY_OF_YEAR, -numDate * 7)
                return DateConverter.convertDateToTimestamp(calendarNumDate.time)
            }

            EnumRange.Companion.GroupType.MONTH -> {
                group = EnumRange.Companion.GroupType.MONTH
                calendarNumDate.add(java.util.Calendar.MONTH, -numDate)
                return DateConverter.convertDateToTimestamp(calendarNumDate.time)
            }

            EnumRange.Companion.GroupType.YEAR -> {
                group = EnumRange.Companion.GroupType.YEAR
                calendarNumDate.add(java.util.Calendar.YEAR, -numDate)
                return DateConverter.convertDateToTimestamp(calendarNumDate.time)
            }
        }
    }

    suspend fun getReposStars(
        userName: String,
        repoName: String,
        groupType: EnumRange.Companion.GroupType,
    ): ArrayList<ChartListItem> {
        val responseList = ArrayList<ChartListItem>()
        try {
            val response: ArrayList<ChartListItem> =
                chartRepo.getStarRepo(userName, repoName, groupType, numDate)

            responseList.addAll(response)
            Log.d("RESPONSE_LIST", "Exception: $response")
            Log.d("RESPONSE_LIST", "Exception: $responseList")

            return responseList

        }
        catch (e: UnknownHostException) {
            Log.d("CHART_PRESENTER_ER", "UnknownException: $e")
            viewState.showError("Нет подключения к интернету")
        }
        catch (e: retrofit2.HttpException) {
            when(e.code()) {
                404 -> viewState.showError("Пользователь не найден")
                403 -> viewState.showError("Лимит запросов закончился")
            }

            Log.d("CHART_PRESENTER_ER", "ExceptionRetrof2: $e")
        }

        return responseList

    }

    fun dayForTheSchedule() {
        RequiredDates.getUniqueArrayList(group, numDate)
    }

}