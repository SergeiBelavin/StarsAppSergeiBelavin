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
import com.omega_r.libs.omegatypes.Text
import moxy.InjectViewState
import moxy.MvpPresenter
import java.net.UnknownHostException

@InjectViewState
class ChartPresenter(private val chartRepo: Repository) : MvpPresenter<ChartView>() {
    var numDate = 0
    var group = EnumRange.Companion.GroupType.WEEK
    var logChartPresenter = Text.from(R.string.log_chart_activity)
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
        Log.d("$logChartPresenter" + "_NUM_CHART", "$numDate")

        if (numDate < 0) {
            val errorText = Text.from(R.string.error_view_in_the_future)
            viewState.showError(errorText)
            numDate = 0
            return ""
        }


        val calendarNumDate = java.util.Calendar.getInstance()

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
            Log.d("$logChartPresenter" + "RESPONSE_LIST", "Exception: $response")
            Log.d("$logChartPresenter" + "RESPONSE_LIST", "Exception: $responseList")

            return responseList

        }
        catch (e: UnknownHostException) {

            val errorTextNoInternetConnection = Text.from(R.string.error_no_internet_connection)
            viewState.showError(errorTextNoInternetConnection)
        }
        catch (e: retrofit2.HttpException) {
            val errorNoInternet = Text.from(R.string.error_no_internet_connection)
            val errorRequestLimitHasEnded = Text.from(R.string.error_the_request_limit_has_ended)
            when(e.code()) {
                404 -> viewState.showError(errorNoInternet)
                403 -> viewState.showError(errorRequestLimitHasEnded)
            }

            Log.d("$logChartPresenter" + "_ER", "ExceptionRetrof2: $e")
        }

        return responseList

    }

    fun dayForTheSchedule() {
        RequiredDates.getUniqueArrayList(group, numDate)
    }

}