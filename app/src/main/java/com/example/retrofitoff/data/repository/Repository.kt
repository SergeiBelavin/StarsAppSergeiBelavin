import com.example.retrofitoff.data.repository.RetrofitInstance
import com.example.retrofitoff.data.repository.UniqueDate

import android.util.Log
import androidx.lifecycle.viewmodel.viewModelFactory


import com.example.retrofitoff.model.RepoUser


import com.example.retrofitoff.model.StarGroup
import com.example.retrofitoff.model.User
import com.example.retrofitoff.data.ui.main.EnumRange
import java.util.*
import kotlin.collections.ArrayList


open class Repository() {

    private val MAX_PAGE_SIZE = 100
    private val MIN_PAGE_SIZE = 0

    private val listResponse = ArrayList<StarGroup>()
    private var stopPaging = 0
    private var pageNumberStar = 1
    private var starsList = ArrayList<StarGroup>()

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
    ): List<StarGroup> {

        val daysResponseInt =
            UniqueDate().getUniqueArrayList(EnumRange.groupsType(groupType), dateSelected)

        val lastData = daysResponseInt[daysResponseInt.size - 1]

        Log.d("DATE_LIST_LASTDATE1", "$lastData")
        Log.d("DATE_LIST_LASTDATE2", "$daysResponseInt")

        try {

            do {
                dataVerification(userName, repoName, groupType, dateSelected)
                return listResponse

            } while (stopPaging == 0 || starsList.size == MIN_PAGE_SIZE)
            return listResponse

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
            processingResponse(response, groupType, dateSelected)

        } else {
            processingResponse(response, groupType, dateSelected)
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

        val lastIndex = rangeList[rangeList.size - 1]

        val firstIndex = rangeList[0]


        Log.d("START_processing", "processingStart")
        val dateToInt = list[0].starredAt.time

        if(dateToInt < lastIndex) {
            Log.d("START_processing", "stop")
            stopPaging = 1
        }

        list.forEach {

            val dateToInt = it.starredAt
            dateToInt.hours = dateToInt.hours - dateToInt.hours
            dateToInt.minutes = dateToInt.minutes - dateToInt.minutes
            dateToInt.seconds = dateToInt.seconds - dateToInt.seconds
            val starredAtUnix = dateToInt.time

            Log.d("REPO_DATE_STARRED_AT", "$starredAtUnix")
            Log.d("REPO_DATE_RANGE_LIST", "$rangeList")
            Log.d("REPO_DATE_LAST_IND", "$lastIndex")
            Log.d("REPO_DATE_FIRST_IND", "$firstIndex")


            if (starredAtUnix in firstIndex..lastIndex) {
                Log.d("REPO_DATE", "math $starredAtUnix date $lastIndex - $firstIndex")
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

    suspend fun getChartDate(
        userName: String,
        repoName: String,
        groupType: EnumRange.Companion.GroupType,
        dateSelected: Int,
    ): List<Int> {

        val dateToGroup = getStarRepo(userName, repoName, groupType, dateSelected)

        val rangeList = UniqueDate().getUniqueArrayList(EnumRange.groupsType(groupType), dateSelected)

        Log.d("MyLogChartGood", "$rangeList")
        Log.d("MyLogChartGood", "${dateToGroup.size}")

        val chartIntDate = ArrayList<Int>()
        chartIntDate.clear()

        val chartListDate = ArrayList<ArrayList<Int>>()
        chartListDate.clear()

        for(i in 0 until rangeList.size) {
            chartListDate.add(ArrayList())
        }

        for (element in 0 until dateToGroup.size) {

            for (i in 0 until rangeList.size) {

                if (rangeList[i] == dateToGroup[element].starredAt.time) {
                    chartListDate[i].add(1)
                }
            }

        }

        for (i in 0 until chartListDate.size) {
            chartIntDate.add(chartListDate[i].size)
        }

        Log.d("UNIQ_DATE_TEST", "$chartListDate")
        Log.d("UNIQ_DATE_INT", "$chartIntDate")

        return chartIntDate
    }

}