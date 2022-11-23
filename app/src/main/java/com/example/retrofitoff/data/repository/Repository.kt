package com.example.retrofitoff.data.repository

import android.util.Log

import com.example.retrofitoff.data.entity.RepoUser


import com.example.retrofitoff.data.entity.StarGroup
import com.example.retrofitoff.data.entity.constructor.ConstructorRepo
import java.util.*
import kotlin.collections.ArrayList


open class Repository() {

    private val MAX_PAGE_SIZE = 100
    private val MIN_PAGE_SIZE = 0


    suspend fun getListRepository(userName: String): List<RepoUser> {
        val allName = ArrayList<String>()
        val responseList = ArrayList<RepoUser>()
        var pageNumberUser = 1
        val constructorRepoList = ArrayList<ConstructorRepo>()
        return try {

            do {
                val response: List<RepoUser> =
                    RetrofitInstance.api.getRepoList(userName, pageNumberUser)

                response.forEach {
                    allName.add(it.name)
                    Log.d("NAME_ALL_REPO", "$allName")
                    if (allName.size == MAX_PAGE_SIZE) allName.clear()
                }
                val group = response.groupBy {
                    it.name
                }

                Log.d("GROUPBY", "$group")

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

    suspend fun getStarRepo(userName: String, repoName: String): List<StarGroup> {
        val allDate = ArrayList<Long>()
        val pageList = ArrayList<Map.Entry<Date, List<StarGroup>>>()
        val allDateSort = ArrayList<Map.Entry<Date, List<StarGroup>>>()
        val starsList = mutableListOf<StarGroup>()
        var pageNumberStar = 1

        return try {
            do {
                val response: List<StarGroup> =
                    RetrofitInstance.api.getRepoStars(userName, repoName, pageNumberStar)

                Log.d("RESPONSE", "$response")

                val calendar = Calendar.getInstance()
                calendar.add(Calendar.DAY_OF_YEAR, -14)
                val daysAgoUnix = calendar.timeInMillis

                Log.d("DAYS_CALENDAR", "$daysAgoUnix")

                val groupBy = response.groupBy {
                    it.starredAt
                }
                groupBy.forEach {
                    val dateGrop = it.key.time
                    Log.d("GROUP_DAY_RESPONSE", "$dateGrop")
                    if (it.key.time > daysAgoUnix) pageList.add(it)
                    Log.d("GROUP_DAY_RESPONSE_IT", "$it")
                }
                Log.d("PAGE_LIST_SIZE", "${pageList.size}")
                if (pageList.size == MAX_PAGE_SIZE) {
                    pageList.clear()
                }
                starsList.addAll(response)
                Log.d("PAGE_NUMBER", "$pageNumberStar")
                pageNumberStar++

            } while (pageList.size == MIN_PAGE_SIZE)
            starsList

        } catch (e: Exception) {
            Log.d("SIZE_STAR_ERROR", "$e")
            return starsList
        }
    }
}
