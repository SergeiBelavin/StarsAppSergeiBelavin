import com.example.retrofitoff.data.repository.RetrofitInstance
import com.example.retrofitoff.data.repository.UniqueDate

import android.net.wifi.WifiManager.LocalOnlyHotspotCallback
import android.util.Log


import com.example.retrofitoff.model.RepoUser


import com.example.retrofitoff.model.StarGroup
import com.example.retrofitoff.model.User
import com.example.retrofitoff.data.ui.main.EnumRange
import java.util.*
import kotlin.collections.ArrayList
import kotlin.time.Duration.Companion.milliseconds


open class Repository() {

    private val MAX_PAGE_SIZE = 100
    private val MIN_PAGE_SIZE = 0

    private val listResponse = ArrayList<StarGroup>()
    private val listResponseGrouping = ArrayList<StarGroup>()
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

        return try {

            do {
                dataVerification(userName, repoName, groupType, dateSelected)

            } while (stopPaging == 0 || starsList.size == MIN_PAGE_SIZE)
            listResponseGrouping

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
            stopPaging = 1
            processingResponse(response, groupType, dateSelected)
        }

        Log.d("REPO_PAGE", "$pageNumberStar")
    }

    private fun processingResponse(
        list: List<StarGroup>,
        groupType: EnumRange.Companion.GroupType,
        dateSelected: Int,
    ) {
        val daysResponseInt =
            UniqueDate().getUniqueArrayList(EnumRange.groupsType(groupType), dateSelected)
        val lastData = daysResponseInt[daysResponseInt.size - 1]
        val calendar = Calendar.getInstance()
        var chartList = ArrayList<Int>()

        list.forEach {

            val dateToInt = UniqueDate().getUniqueDate(it.starredAt)
            val dateToIntUnix = it.starredAt.time


            var unixDateLast: Long = 0

            if (EnumRange.groupsType(groupType) == 14) {
                calendar.clear()
                calendar.add(Calendar.DAY_OF_YEAR, -dateSelected * 7)
                unixDateLast = calendar.timeInMillis
                Log.d("GROUP_WEEK", "$dateToIntUnix")
            }
            if (EnumRange.groupsType(groupType) == 30) {
                calendar.clear()
                calendar.add(Calendar.MONTH, -dateSelected)
                unixDateLast = calendar.timeInMillis
                Log.d("GROUP_MONTH", "$dateToIntUnix")
            }
            if (EnumRange.groupsType(groupType) == 60) {
                calendar.clear()
                calendar.add(Calendar.YEAR, -dateSelected)
                Log.d("GROUP_YEAR", "$dateToIntUnix")
                unixDateLast = calendar.timeInMillis
            }

            if (dateToIntUnix >= unixDateLast) {
                Log.d("REPO_DATE_NOW", "$dateToIntUnix")
                Log.d("REPO_DATE_LOST", "$unixDateLast")
                Log.d("REPO_DATE_CALENDAR", "${calendar.time}")

                val starGroup = object : StarGroup {
                    override val starredAt: Date
                        get() = it.starredAt
                    override val user: User
                        get() = it.user
                    override val uniqueDate: Int?
                        get() = dateToInt
                }

                listResponse.add(starGroup)
//gulihua10010

            } else {
                stopPaging = 1
            }
            Log.d("TEST_NEW_REP", "${listResponse[0]}")
            Log.d("TEST_NEW_REP", "${listResponse}")
        }
    }

}