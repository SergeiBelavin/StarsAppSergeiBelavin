package com.example.retrofitoff.data.repository

import android.util.Log

import com.example.retrofitoff.data.entity.RepoUser


import com.example.retrofitoff.data.entity.StarGroup
import com.example.retrofitoff.model.RepoUserItem
import com.example.retrofitoff.model.StarGroupItem
import java.util.*
import kotlin.collections.ArrayList


open class Repository() {

    private val MAX_PAGE_SIZE = 30
    private val MIN_PAGE_SIZE = 0

    suspend fun getListRepository(userName: String): List<RepoUser> {
        val allName = ArrayList<String>()
        var pageNumberUser = 1
        return try {

            do {
                val response: List<RepoUserItem> =
                    RetrofitInstance.api.getRepoList(userName, pageNumberUser)

                response.forEach {
                    val name = it.name
                    allName.add(name)
                    if (allName.size == MAX_PAGE_SIZE) {
                        allName.clear()
                    }
                }
                pageNumberUser++

            } while( allName.size == MIN_PAGE_SIZE)
            RetrofitInstance.api.getRepoList(userName, pageNumberUser)

        } catch (e: Exception) {
            Log.d("sizeErrorInPage", "$e")
            return RetrofitInstance.api.getRepoList(userName, pageNumberUser)
        }
    }
    suspend fun getStarRepo(userName: String, repoName: String): List<StarGroup> {
        val sortDate = ArrayList<Long>()
        val allDate = ArrayList<Long>()
        val starsList = mutableListOf<StarGroup>()
        var pageNumberStar = 1

        return try {
            do {
                val response: List<StarGroupItem> =
                    RetrofitInstance.api.getRepoStat(userName, repoName, pageNumberStar)

                Log.d("RESPONSE", "$response")

                val calendar = Calendar.getInstance()
                calendar.add(Calendar.DAY_OF_YEAR, -14)
                val daysAgoUnix = calendar.timeInMillis

                Log.d("DAYS_CALENDAR", "$daysAgoUnix")

                response.forEach {
                    val dateUnix = it.starredAt.time

                    allDate.add(it.starredAt.time)
                    if (allDate.size == MAX_PAGE_SIZE) {
                        Log.d("ALL_DATE_SIZE", allDate.size.toString())
                        allDate.clear()
                    }
                    if (dateUnix < daysAgoUnix) {
                        // dateLong.add(dateUnix)
                        Log.d("STARRED_AT_SORT", it.starredAt.toString())
                        sortDate.add(dateUnix)
                        Log.d("SORT_DATE_LIST", sortDate.toString())
                    }
                }
                starsList.addAll(response)
                Log.d("PAGE_NUMBER", "$pageNumberStar")
                pageNumberStar++

            } while (allDate.size == MIN_PAGE_SIZE)
            starsList

        } catch (e: Exception) {
            Log.d("SIZE_REPO_ERROR", "$e")
            return starsList
        }
    }
}
