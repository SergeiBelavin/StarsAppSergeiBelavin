package com.example.retrofitoff.data.repository

import android.util.Log

import com.example.retrofitoff.data.entity.RepoUser


import com.example.retrofitoff.data.entity.StarGroup
import com.example.retrofitoff.data.entity.constructor.ConstructorRepo
import com.example.retrofitoff.data.entity.constructor.ConstructorStar
import com.example.retrofitoff.data.entity.constructor.ConstructorUser
import com.example.retrofitoff.ui.chart.EnumRange
import kotlin.collections.ArrayList


open class Repository() {

    private val MAX_PAGE_SIZE = 100
     val MIN_PAGE_SIZE = 0


    suspend fun getListRepository(userName: String): List<RepoUser> {
        val nameOnTheSheet = ArrayList<String>()
        val responseList = ArrayList<RepoUser>()
        val responseListConv = ArrayList<ConstructorRepo>()
        var pageNumberUser = 1

        return try {

            do {
                val response: List<RepoUser> =
                    RetrofitInstance.api.getRepoList(userName, pageNumberUser)
                response.forEach {
                    nameOnTheSheet.add(it.name)
                }
                if (responseListConv.size == MAX_PAGE_SIZE) nameOnTheSheet.clear()
                responseList.addAll(response)
                pageNumberUser++
                Log.d("PAGE_NUM_REPO", "$pageNumberUser")

            } while (nameOnTheSheet.size == MIN_PAGE_SIZE)
            responseList

        } catch (e: Exception) {
            Log.d("SIZE_REPO_ERROR", "$e")
            return responseList
        }
    }

    suspend fun getStarRepo(userName: String, repoName: String, groupType: EnumRange.Companion.GroupType,): List<Map<Int, ConstructorStar>> {

        val starsList = ArrayList<StarGroup>()
        var pageNumberStar = 1
        val groupRange = EnumRange.groupsType(groupType)

        val listMap = ArrayList<Map<Int, ConstructorStar>>()
        val daysResponseInt = uniqueDate.getUniqueArrayList(EnumRange.groupsType(groupType))

        Log.d("DAYSCALENDAR1", "$daysResponseInt")

        return try {
            do {
                val response: List<StarGroup> =
                    RetrofitInstance.api.getRepoStars(userName, repoName, pageNumberStar)
                starsList.addAll(response)

                response.forEach {
                    val dateToInt = uniqueDate.getUniqueDate(it.starredAt)

                    val constRepo = ConstructorStar(it.starredAt,
                        ConstructorUser(it.user.id, it.user.name, it.user.avatar),
                        dateToInt,
                        ConstructorRepo(it.user.id, it.user.name, 0,
                            ConstructorUser(it.user.id, it.user.name, it.user.avatar,)))

                    for (i in 0 until groupRange) {
                        if (dateToInt == daysResponseInt[i]) {
                            constRepo.repo.neededForChart = 1
                        }
                    }

                    val mapResponse: Map<Int, ConstructorStar> =
                        mutableMapOf(dateToInt to constRepo)
                    listMap.add(mapResponse)
                }
                if(starsList.size == MAX_PAGE_SIZE) starsList.clear()
                pageNumberStar++


            } while (starsList.size == MIN_PAGE_SIZE)
            listMap

        } catch (e: Exception) {
            return listMap
        }
    }

    open fun groupDateChart(groupTypeDate: GroupTypeDate): Boolean {
        return groupTypeDate.needOrNot
    }
    enum class GroupTypeDate(val needOrNot: Boolean) {
        NEEDED_FOR_CHART(true),
        NOT_NEEDED_FOR_CHART(false)
    }
}
