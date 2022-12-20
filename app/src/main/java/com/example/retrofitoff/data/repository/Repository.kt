package com.example.retrofitoff.data.repository

import android.util.Log


import com.example.retrofitoff.model.RepoUser


import com.example.retrofitoff.model.StarGroup
import com.example.retrofitoff.model.User
import com.example.retrofitoff.data.entity.constructor.ConstructorRepo
import com.example.retrofitoff.data.ui.main.EnumRange
import java.util.*
import kotlin.collections.ArrayList


open class Repository() {

    private val MAX_PAGE_SIZE = 100
    private val MIN_PAGE_SIZE = 0

    private val listResponse = ArrayList<StarGroup>()
    private val listResponseGrouping = ArrayList<StarGroup>()
    private var stopPaging = 0

      suspend fun getListRepository(userName: String): List<RepoUser> {
        val nameOnTheSheet = ArrayList<String>()
        val responseList = ArrayList<RepoUser>()
        val responseListSize = ArrayList<ConstructorRepo>()
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
        dateSelected: Int
    ): List<StarGroup> {

        val daysResponseInt = UniqueDate().getUniqueArrayList(EnumRange.groupsType(groupType), dateSelected)

        val lastData = daysResponseInt[daysResponseInt.size - 1]

        var pageNumberStar = 1

        Log.d("DATE_LIST_LASTDATE1", "$lastData")
        Log.d("DATE_LIST_LASTDATE2", "$daysResponseInt")

        return try {

            do {
                val starsList = ArrayList<StarGroup>()
                val response: List<StarGroup> =
                    RetrofitInstance.api.getRepoStars(userName, repoName, pageNumberStar)

                starsList.addAll(response)

                Log.d("REPO_SIZE", "${starsList}")
                Log.d("REPO_SIZE_SIZE", "${starsList.size}")

                if (starsList.size == MAX_PAGE_SIZE) {
                    pageNumberStar++
                    processingResponse(response, groupType, dateSelected)
                } else {
                    stopPaging = 1
                    processingResponse(response, groupType, dateSelected)
                    return listResponseGrouping
                }

                Log.d("REPO_PAGE", "$pageNumberStar")


            } while (starsList.size == MIN_PAGE_SIZE || stopPaging == 1)

            listResponseGrouping

        } finally {

        }
    }

    private fun processingResponse(
        list: List<StarGroup>,
        groupType: EnumRange.Companion.GroupType,
        dateSelected: Int
    ) {
        val daysResponseInt = UniqueDate().getUniqueArrayList(EnumRange.groupsType(groupType),dateSelected)

        val lastData = daysResponseInt[daysResponseInt.size - 1]

        list.forEach {

            val dateToInt = UniqueDate().getUniqueDate(it.starredAt)
            Log.d("LOGGGG", "${lastData}")
            if (dateToInt >= lastData) {

                val starGroup = object : StarGroup {
                    override val starredAt: Date
                        get() = it.starredAt
                    override val user: User
                        get() = it.user
                    override val uniqueDate: Int?
                        get() = dateToInt
                    var barChartList: Map<Int?, List<StarGroup>>? = null
                        get() = null
                }

                listResponse.add(starGroup)
                Log.d("LOGGGG1", "${starGroup.starredAt}")

                if(listResponse.isNotEmpty()) {

                    val barChartList = listResponse.groupBy {
                        starGroup.uniqueDate
                    }

                    val starListGroup = object : StarGroup {
                        override val starredAt: Date
                            get() = it.starredAt
                        override val user: User
                            get() = it.user
                        override val uniqueDate: Int?
                            get() = dateToInt
                        var barChartList: Map<Int?, List<StarGroup>>? = null
                            get() = barChartList
                    }

                    listResponseGrouping.add(starListGroup)
                }

                Log.d("GROUP_LIST1", "${starGroup.barChartList}")
                Log.d("GROUP_LIST2", "${starGroup}")
                Log.d("GROUP_LIST3", "${listResponseGrouping.size}")

//gulihua10010

            } else {
                stopPaging = 1
            }
        }
    }

}
