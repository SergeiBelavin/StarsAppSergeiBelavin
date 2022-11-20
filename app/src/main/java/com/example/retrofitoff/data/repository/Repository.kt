package com.example.retrofitoff.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData


import com.example.retrofitoff.data.entity.StarGroup
import com.example.retrofitoff.model.RepoUserItem
import com.example.retrofitoff.model.StarGroupItem
import java.io.Serializable
import java.util.*
import kotlin.collections.ArrayList


open class Repository() {
    val dateLong = ArrayList<Serializable>()

    suspend fun getListRepository(userName: String): List<RepoUserItem> {
        return RetrofitInstance.api.getRepoList(userName)
    }

    private val chartResponse = MutableLiveData<List<StarGroup>>()
    val chartResponseEmitter: LiveData<List<StarGroup>> = chartResponse

    suspend fun getStarRepo(userName: String, repoName: String): Serializable {

        val pageNumber = 1
        val maxPageSize = 34
        return try {
            do {
                val response: List<StarGroupItem> =
                    RetrofitInstance.api.getRepoStat(userName, repoName, pageNumber)

                        Log.d("Rep", "$response")

                        val calendar = Calendar.getInstance()
                        calendar.add(Calendar.DAY_OF_YEAR, -1)
                        val daysAgoUnix = calendar.timeInMillis / 1000

                        Log.d("DaysTimeCalendar", "$daysAgoUnix")

                        response.forEach {
                            val dateUnix = it.starredAt.time / 1000
                            Log.d("DaysRepo", "$dateUnix")
                            if (dateUnix < daysAgoUnix) {
                                // dateLong.add(dateUnix)
                                Log.d("repositoryDO", it.starredAt.toString())
                                dateLong.add(dateUnix.toString())
                                Log.d("dateLong", dateLong.toString())
                            }
                            Log.d("repositoryPosle", it.starredAt.toString())
                        }

            } while (response.size < maxPageSize)
            Log.d("pageStarList", "MaxPage: $maxPageSize")
            dateLong

        } catch (e: Exception) {
            Log.d("sizeErrorInPage", "$e")
            return dateLong
        }
    }


}
