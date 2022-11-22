package com.example.retrofitoff.data.repository

import android.util.Log
import com.example.retrofitoff.data.api.providers.RepoProvider
import com.example.retrofitoff.data.api.providers.StarProvider
import com.example.retrofitoff.data.api.providers.StarSearch

import com.example.retrofitoff.data.entity.RepoUser


import com.example.retrofitoff.data.entity.StarGroup
import com.example.retrofitoff.model.RepoUserItem
import com.example.retrofitoff.model.StarGroupItem
import java.util.*
import kotlin.collections.ArrayList


open class Repository() {

    private val MAX_PAGE_SIZE = 100
    private val MIN_PAGE_SIZE = 0
    private val starProvider = RepoProvider()

    suspend fun getListRepository(userName: String): List<RepoUser> {
        val allName = ArrayList<String>()
        val responseList = ArrayList<RepoUserItem>()
        var pageNumberUser = 1
        return try {

            do {
                val response: List<RepoUserItem> =
                    RetrofitInstance.api.getRepoList(userName, pageNumberUser)

                response.forEach {
                    allName.add(it.name)
                    Log.d("NAME_ALL_REPO", "$allName")
                    if (allName.size == MAX_PAGE_SIZE) allName.clear()
                }
                responseList.addAll(response)
                pageNumberUser++
                Log.d("PAGE_NUM_REPO", "$pageNumberUser")


            } while( allName.size == MIN_PAGE_SIZE)
            responseList

        } catch (e: Exception) {
            Log.d("SIZE_REPO_ERROR", "$e")
            return responseList
        }
    }
    suspend fun getStarRepo(userName: String, repoUserEntity: RepoUser, repoName: String): List<StarGroup> {
        val sortDate = ArrayList<Long>()
        val allDate = ArrayList<Long>()
        val starsList = mutableListOf<StarGroup>()
        val starsListGroup = mutableListOf<StarGroup>()
        var pageNumberStar = 1

        return try {
            do {
                val response: List<StarGroupItem> =
                    RetrofitInstance.api.getRepoStars(userName, repoName, pageNumberStar)

                Log.d("RESPONSE", "$response")

                val calendar = Calendar.getInstance()
                calendar.add(Calendar.DAY_OF_YEAR, -14)
                val daysAgoUnix = calendar.timeInMillis

                Log.d("DAYS_CALENDAR", "$daysAgoUnix")

                response.forEach {
                    val dateUnix = it.starredAt.time
                    allDate.add(dateUnix)
                    if (allDate.size == MAX_PAGE_SIZE) {
                        Log.d("ALL_DATE_SIZE", allDate.size.toString())
                        allDate.clear()
                    }

                    val groupBy = response.groupBy {
                        it.user
                    }
                    Log.d("GROUP_BE_RESPONSE", groupBy.toString())

                    if (dateUnix < daysAgoUnix) {
                        // dateLong.add(dateUnix)
                        Log.d("STARRED_AT_SORT", it.starredAt.toString())
                        sortDate.add(dateUnix)
                        Log.d("SORT_DATE_LIST", sortDate.toString())
                    }

                }
                val stars = starProvider.getRepoList(userName, repoUserEntity, pageNumberStar)
                starsList.addAll(stars)
                Log.d("PAGE_NUMBER", "$pageNumberStar")
                pageNumberStar++

            } while (allDate.size == MIN_PAGE_SIZE)
            val starGroup = starsList.groupBy {
                it.starredAt
            }

            Log.d("STAR_LIST_GROUP", "$starGroup")
            Log.d("STAR_LIST_PROVIDER", "$starsList")
            starsList

        } catch (e: Exception) {
            Log.d("SIZE_STAR_ERROR", "$e")
            return starsList
        }
    }
}
