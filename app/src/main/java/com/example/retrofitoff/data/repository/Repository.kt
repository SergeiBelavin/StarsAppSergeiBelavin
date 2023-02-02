import com.example.retrofitoff.data.repository.RetrofitInstance
import com.example.retrofitoff.data.repository.RequiredDates

import android.util.Log
import com.example.retrofitoff.data.entity.ChartListItem
import com.example.retrofitoff.model.ChartList


import com.example.retrofitoff.model.RepoUser


import com.example.retrofitoff.model.StarGroup
import com.example.retrofitoff.ui.main.EnumRange
import com.omega_r.libs.omegatypes.Image
import kotlin.collections.ArrayList

object Repository {

    private val MAX_PAGE_SIZE = 100
    private val MIN_PAGE_SIZE = 0
    private var stopPaging = 0
    private var pageNumberStar = 1
    private var starsList = ArrayList<StarGroup>()
    private var dateToChart = ArrayList<ChartListItem>()

    suspend fun getListRepository(userName: String): List<RepoUser> {
        val nameOnTheSheet = ArrayList<String>()
        val responseList = ArrayList<RepoUser>()
        val responseListSize = ArrayList<RepoUser>()
        var pageNumberUser = 1

        do {
            val response: List<RepoUser> =
                RetrofitInstance.api.getRepoList(userName, pageNumberUser)

            response.forEach {
                nameOnTheSheet.add(it.name)
            }

            if (responseListSize.size == MAX_PAGE_SIZE) nameOnTheSheet.clear()
            responseList.addAll(response)
            pageNumberUser++

        } while (nameOnTheSheet.size == MIN_PAGE_SIZE)

        return responseList
    }

    suspend fun getStarRepo(
        userName: String,
        repoName: String,
        groupType: EnumRange.Companion.GroupType,
        dateSelected: Int,
    ): ArrayList<ChartListItem> {
        starsList.clear()
        dateToChart.clear()
        pageNumberStar = 1
        stopPaging = 0

        do {
            dataVerification(userName, repoName, groupType, dateSelected)
            return dateToChart

        } while (stopPaging == 0 || starsList.size == MIN_PAGE_SIZE)

        return dateToChart

        //hwchase17
        //chat-langchain
    }

    private suspend fun dataVerification(
        userName: String, repoName: String, groupType: EnumRange.Companion.GroupType,
        dateSelected: Int) {

        getResponse(userName, repoName, pageNumberStar)

        //val response: List<StarGroup> = RetrofitInstance.api.getRepoStars(userName, repoName, pageNumberStar)

        starsList.addAll(getResponse(userName, repoName, pageNumberStar))

        if (starsList.size == MAX_PAGE_SIZE) {
            pageNumberStar++
            starsList.clear()
            dateToChart = getChartDate(getResponse(userName, repoName, pageNumberStar), groupType, dateSelected)

        } else {
            dateToChart = getChartDate(getResponse(userName, repoName, pageNumberStar), groupType, dateSelected)
            stopPaging = 1
        }
    }

    suspend fun getResponse(userName: String, repoName: String, pageNumberStar: Int): List<StarGroup> {
        return RetrofitInstance.api.getRepoStars(userName, repoName, pageNumberStar)
    }
    //gulihua10010

    fun getChartDate(
        listResponse: List<StarGroup>,
        groupType: EnumRange.Companion.GroupType,
        dateSelected: Int,
    ): ArrayList<ChartListItem> {
        val rangeList =
            RequiredDates.getUniqueArrayList(groupType, dateSelected)
        val chartIntDate = ArrayList<Int>()
        val chartListDate = ArrayList<ArrayList<Int>>()
        val avatarList = ArrayList<Image?>()
        val nameUser = ArrayList<String?>()

        val chartRepoList = ArrayList<ChartListItem>()

        for (i in 0 until listResponse.size) {
            listResponse[i].starredAt.hours = 0
            listResponse[i].starredAt.minutes = 0
            listResponse[i].starredAt.seconds = 0
        }

        for (i in 0 until rangeList.size) {
            chartListDate.add(ArrayList())
        }

        for (element in 0 until listResponse.size) {

            for (i in 0 until rangeList.size) {
                if (rangeList[i] == listResponse[element].starredAt.time) {
                    chartListDate[i].add(1)
                    avatarList.add(listResponse[element].user.avatar)
                    nameUser.add(listResponse[element].user.name)

                } else {
                    Log.d("CIKLE", "neeed ${rangeList[i]},")
                }
            }
        }

        for (i in 0 until chartListDate.size) {

            chartIntDate.add(chartListDate[i].size)
        }
        val chartDateList = ChartListItem(chartIntDate, avatarList, nameUser)

        chartRepoList.add(chartDateList)

        return chartRepoList
    }

}