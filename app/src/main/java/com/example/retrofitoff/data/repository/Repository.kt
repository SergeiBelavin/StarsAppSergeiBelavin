package com.example.retrofitoff.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

import com.example.retrofitoff.data.entity.RepoUser


import com.example.retrofitoff.data.entity.StarGroup
import com.example.retrofitoff.model.RepoUserItem
import com.example.retrofitoff.model.StarGroupItem
import java.io.Serializable
import java.util.*
import kotlin.collections.ArrayList


open class Repository() {


    suspend fun getListRepository(userName: String): List<RepoUser> {
        return RetrofitInstance.api.getRepoList(userName)

    }

    private val chartResponse = MutableLiveData<List<StarGroup>>()
    val chartResponseEmitter: LiveData<List<StarGroup>> = chartResponse

    suspend fun getStarRepo(userName: String, repoName: String): List<StarGroup> {
        val dateLong = ArrayList<Long>()
        val starsList = mutableListOf<StarGroup>()
        var pageNumber = 1
        val maxPageSize = 30
        return try {
            do {
                val response: List<StarGroupItem> =
                    RetrofitInstance.api.getRepoStat(userName, repoName, pageNumber)

                Log.d("Rep", "$response")

                val calendar = Calendar.getInstance()
                calendar.add(Calendar.DAY_OF_YEAR, -14)
                val daysAgoUnix = calendar.timeInMillis

                Log.d("DaysTimeCalendar", "$daysAgoUnix")

                response.forEach {
                    val dateUnix = it.starredAt.time
                    Log.d("DaysRepo", "$dateUnix")
                    if (dateUnix < daysAgoUnix) {
                        // dateLong.add(dateUnix)
                        Log.d("repositoryDO", it.starredAt.toString())
                        dateLong.add(dateUnix)
                        Log.d("dateLong", dateLong.toString())
                    }

                    Log.d("repositoryPosle", it.starredAt.toString())
                }
                starsList.addAll(response)
                Log.d("repositoryPosle", starsList.size.toString())
                Log.d("pageList1", "$pageNumber")
                Log.d("pageList2", "${response.size}")
                pageNumber++
            } while (response.size == maxPageSize)
            Log.d("pageStarList", "MaxPage: ${starsList.size}")
            starsList

        } catch (e: Exception) {
            Log.d("sizeErrorInPage", "$e")
            return starsList
        }
    }
}
