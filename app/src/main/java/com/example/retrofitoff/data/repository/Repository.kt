import com.example.retrofitoff.data.repository.RetrofitInstance
import com.example.retrofitoff.data.repository.UniqueDate

import android.util.Log
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.retrofitoff.data.entity.ChartListItem
import com.example.retrofitoff.model.ChartList


import com.example.retrofitoff.model.RepoUser


import com.example.retrofitoff.model.StarGroup
import com.example.retrofitoff.model.User
import com.example.retrofitoff.ui.main.EnumRange
import java.util.*
import kotlin.collections.ArrayList
import kotlin.time.Duration.Companion.hours


open class Repository() {

    private val MAX_PAGE_SIZE = 100
    private val MIN_PAGE_SIZE = 0
    private val listResponse = ArrayList<StarGroup>()
    private var stopPaging = 0
    private var pageNumberStar = 1
    private var starsList = ArrayList<StarGroup>()
    private var chartRepoList = ArrayList<ChartList>()

    suspend fun getListRepository(userName: String): List<RepoUser> {
        val nameOnTheSheet = ArrayList<String>()
        val responseList = ArrayList<RepoUser>()
        val responseListSize = ArrayList<RepoUser>()
        var pageNumberUser = 1

        return try {

            do {
                val response: List<RepoUser> =
                    RetrofitInstance.api.getRepoList(userName, pageNumberUser)

                response.forEach {
                    nameOnTheSheet.add(it.name)
                }

                if (responseListSize.size == MAX_PAGE_SIZE) nameOnTheSheet.clear()
                responseList.addAll(response)
                pageNumberUser++
                Log.d("PAGE_NUM_REPO", "$pageNumberUser")

            } while (nameOnTheSheet.size == MIN_PAGE_SIZE)
            responseList

        } finally {

        }
    }

    suspend fun getStarRepo(
        userName: String,
        repoName: String,
        groupType: EnumRange.Companion.GroupType,
        dateSelected: Int,
    ): ArrayList<ChartList> {

        listResponse.clear()
        starsList.clear()

        val daysResponseInt = UniqueDate().getUniqueArrayList(EnumRange.groupsType(groupType), dateSelected)

        val lastData = daysResponseInt[daysResponseInt.size - 1]

        Log.d("DATE_LIST_LASTDATE1", "$lastData")
        Log.d("DATE_LIST_LASTDATE2", "$daysResponseInt")

        try {

            do {
                dataVerification(userName, repoName, groupType, dateSelected)

                return chartRepoList

            } while (stopPaging == 0 || starsList.size == MIN_PAGE_SIZE)
            return chartRepoList

        } finally {

        }
    }

    private suspend fun dataVerification(
        userName: String, repoName: String, groupType: EnumRange.Companion.GroupType,
        dateSelected: Int,
    ) {

        val response: List<StarGroup> =
            RetrofitInstance.api.getRepoStars(userName, repoName, pageNumberStar)

        starsList.addAll(response)

        Log.d("REPO_SIZE", "${starsList}")
        Log.d("REPO_SIZE_SIZE", "${starsList.size}")

        if (starsList.size == MAX_PAGE_SIZE) {
            pageNumberStar++
            starsList.clear()
            //processingResponse(response, groupType, dateSelected)
            getChartDate(response, groupType,dateSelected)

        } else {
            //processingResponse(response, groupType, dateSelected)
            getChartDate(response, groupType,dateSelected)
            stopPaging = 1
        }

        Log.d("REPO_PAGE", "$pageNumberStar")
    }

    private fun processingResponse(
        list: List<StarGroup>,
        groupType: EnumRange.Companion.GroupType,
        dateSelected: Int,
    ) {
        val rangeList =
            UniqueDate().getUniqueArrayList(EnumRange.groupsType(groupType), dateSelected)
        Log.d("LIST_WEEK_date_selected","${dateSelected}")
        val lastIndex = rangeList[rangeList.size - 1]
        listResponse.clear()
        val firstIndex = rangeList[0]


        Log.d("START_processing", "processingStart")
        val dateToInt = list[0].starredAt.time
        Log.d("DATE_TO_INT", "$dateToInt")

        if(dateToInt > lastIndex) {
            Log.d("START_processing", "stop")
            stopPaging = 1
        }

        list.forEach {

            val dateToInt = it.starredAt
            dateToInt.hours = dateToInt.hours - dateToInt.hours
            dateToInt.minutes = dateToInt.minutes - dateToInt.minutes
            dateToInt.seconds = dateToInt.seconds - dateToInt.seconds
            val starredAtUnix = 0

            Log.d("REPO_DATE_RANGE_LIST", "$dateToInt")
            Log.d("REPO_DATE_RANGE_LIST", "$rangeList")
            Log.d("REPO_DATE_LAST_IND", "$lastIndex")
            Log.d("REPO_DATE_FIRST_IND", "$firstIndex")


            if (dateToInt.time >= firstIndex && starredAtUnix <= lastIndex) {
                Log.d("REPO_DATE_MATH", "math $dateToInt date $lastIndex - $firstIndex")
                listResponse.add(it)
            }
//gulihua10010

            val logList = ArrayList<Long?>()
            for (i in 0 until listResponse.size) {
                logList.add(listResponse[i].starredAt.time)
            }
            Log.d("TEST_NEW_REP", "${logList}")
            Log.d("TEST_NEW_REP", "${listResponse}")
        }
    }

    private fun getChartDate(
        listResponse: List<StarGroup>,
        groupType: EnumRange.Companion.GroupType,
        dateSelected: Int,
    ): ChartList {
        chartRepoList.clear()
        val rangeList = UniqueDate().getUniqueArrayList(EnumRange.groupsType(groupType), dateSelected)
        val chartIntDate = ArrayList<Int>()
        val chartListDate = ArrayList<ArrayList<Int>>()
        val avatarList = ArrayList<String?>()
        Log.d("CIKLE_size", "${rangeList.size}")
        for(i in 0 until rangeList.size) {
            chartListDate.add(ArrayList())
        }

        for (element in 0 until listResponse.size) {


            for (i in 0 until rangeList.size) {
                if (rangeList[i] == listResponse[element].starredAt.time) {
                    chartListDate[i].add(1)
                    avatarList.add(listResponse[element].user.avatar)
                    Log.d("CIKLE", "DANE")

                    Log.d("CIKLE", "${rangeList[element]}")
                } else {Log.d("CIKLE", "${rangeList[i]}, ${listResponse[element].starredAt.time}")}
            }
        }

        for (i in 0 until chartListDate.size) {
            chartIntDate.add(chartListDate[i].size)
        }
        val chartDateList = ChartListItem(chartIntDate,avatarList)

        Log.d("UNIQ_DATE_TEST", "$chartListDate")
        Log.d("UNIQ_DATE_INT", "$chartIntDate")

        chartRepoList.add(chartDateList)

        return chartDateList
    }

}