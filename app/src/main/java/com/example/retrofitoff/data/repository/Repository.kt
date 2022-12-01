package com.example.retrofitoff.data.repository

import android.util.Log

import com.example.retrofitoff.data.entity.RepoUser


import com.example.retrofitoff.data.entity.StarGroup
import com.example.retrofitoff.data.entity.constructor.ConstructorRepo
import com.example.retrofitoff.data.entity.constructor.ConstructorStar
import com.example.retrofitoff.data.entity.constructor.ConstructorUser
import java.util.*
import kotlin.collections.ArrayList


open class Repository() {

    private val MAX_PAGE_SIZE = 100
    private val MIN_PAGE_SIZE = 0


    suspend fun getListRepository(userName: String): List<RepoUser> {
        val allName = ArrayList<String>()
        val responseList = ArrayList<RepoUser>()
        val responseListConv = ArrayList<ConstructorRepo>()
        var pageNumberUser = 1

        return try {

            do {
                val response: List<RepoUser> =
                    RetrofitInstance.api.getRepoList(userName, pageNumberUser)
                response.forEach {
                    allName.add(it.name)
                    Log.d("NAME_ALL_REPO", "$allName")
                    val constructorRepo = ConstructorRepo(it.id,
                        it.name,
                        it.neededForChart,
                        ConstructorUser(it.id, it.name))
                    responseListConv.add(constructorRepo)
                    Log.d("RESPONSE_CONV", "$responseListConv")
                }
                if (responseListConv.size == MAX_PAGE_SIZE) allName.clear()
                responseList.addAll(response)
                pageNumberUser++
                Log.d("PAGE_NUM_REPO", "$pageNumberUser")

            } while (allName.size == MIN_PAGE_SIZE)
            responseList

        } catch (e: Exception) {
            Log.d("SIZE_REPO_ERROR", "$e")
            return responseList
        }
    }

    suspend fun getStarRepo(userName: String, repoName: String, groupType: GroupType,):
            List<Map<Int, ConstructorStar>> {
//
        val pageList = ArrayList<ConstructorStar>()
        val starsList = ArrayList<StarGroup>()
        var pageNumberStar = 1
        val groupRange = groupsType(groupType)
        val calendarLost = ArrayList<ArrayList<StarGroup>>()
        for (i in 0 until groupRange) {
            calendarLost.add(ArrayList<StarGroup>())
        }
        val listMap = ArrayList<Map<Int, ConstructorStar>>()

        return try {
            do {
                val response: List<StarGroup> =
                    RetrofitInstance.api.getRepoStars(userName, repoName, pageNumberStar)
                starsList.addAll(response)
                Log.d("RESPONSE", "$response")
                Log.d("GROUP_TYPE", "$groupType")

                val daysResponseLong = ArrayList<Long>()

                var uniqueDatAgoI = 0
                for (i in 0 until groupRange) {
                    val calendar = Calendar.getInstance()
                    calendar.add(Calendar.DAY_OF_YEAR, -i)
                    val dayAgo = calendar.time
                    Log.d("DAYS_AGO", "$dayAgo")
                    uniqueDatAgoI = getUniqueDate(dayAgo)
                    daysResponseLong.add(uniqueDatAgoI.toLong())
                    Log.d("UNIQUE_DATE_CALENDAR", "$uniqueDatAgoI")
                    Log.d("CALENDAR_LIST", "$daysResponseLong")
                    calendar.clear()
                }

                response.forEach {
                    val dateToInt = getUniqueDate(it.starredAt)

                    val constRepo = ConstructorStar(
                        it.starredAt,
                        ConstructorUser(
                            it.user.id,
                            it.user.name),
                        ConstructorRepo(
                            it.user.id,
                            it.user.name,
                            0,
                            ConstructorUser(
                                it.user.id,
                                it.user.name
                            )
                        )
                    )

                    for (i in 0 until groupRange) {
                        if (dateToInt == daysResponseLong[i].toInt()) {
                            constRepo.repo.neededForChart = 1
                        }
                    }

                    val mapResponse: Map<Int, ConstructorStar> =
                        mutableMapOf(dateToInt to constRepo)
                    listMap.add(mapResponse)

                    Log.d("MAP_FOR_IT_STARRED_AT", "${it.starredAt}")
                    Log.d("MAP_GET_UNIQ_INT", "${dateToInt}")
                    Log.d("MAP_2GET_UNIQ_INT", "${mapResponse}")
                    Log.d("M", "${listMap.size}")
                    Log.d("LIST_MAP", "$listMap")
                }

                pageNumberStar++
                Log.d("STARS_LIST", "${starsList.size}")
                Log.d("PAGE", "$pageNumberStar")


            } while (starsList.size == MAX_PAGE_SIZE)
            Log.d("PAGE", "$pageNumberStar")
            Log.d("STARS_LIST", "${starsList.size}")
            listMap

        } catch (e: Exception) {
            Log.d("SIZE_STAR_ERROR", "$e")
            return listMap
        }
    }
    open fun groupsType(groupType: GroupType): Int {
        return groupType.numInt
    }
    enum class GroupType(val numInt: Int) {
        FOURTEEN_DAYS(14),
        THIRTY_DAYS(30),
        SIXTY_DAYS(60),
    }

    private fun getUniqueDate(date: Date): Int {
        return date.date + 31 * date.month * date.year * 1000
    }
    open fun groupDateChart(groupTypeDate: GroupTypeDate): Boolean {
        return groupTypeDate.needOrNot
    }
    enum class GroupTypeDate(val needOrNot: Boolean) {
        NEEDED_FOR_CHART(true),
        NOT_NEEDED_FOR_CHART(false)
    }
}
