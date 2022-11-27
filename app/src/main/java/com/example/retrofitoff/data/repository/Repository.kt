package com.example.retrofitoff.data.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData

import com.example.retrofitoff.data.entity.RepoUser


import com.example.retrofitoff.data.entity.StarGroup
import com.example.retrofitoff.data.entity.constructor.ConstrGroup
import com.example.retrofitoff.data.entity.constructor.ConstructorRepo
import com.example.retrofitoff.data.entity.constructor.ConstructorStar
import com.example.retrofitoff.data.entity.constructor.ConstructorUser
import java.util.*
import kotlin.collections.ArrayList


open class Repository() {

    private val MAX_PAGE_SIZE = 100
    private val MIN_PAGE_SIZE = 0

    var calendarLostMut = MutableLiveData<List<StarGroup>>()
    var calendarLost = listOf(
        ArrayList<StarGroup>(),
        ArrayList<StarGroup>(),
        ArrayList<StarGroup>(),
        ArrayList<StarGroup>(),
        ArrayList<StarGroup>(),
        ArrayList<StarGroup>(),
        ArrayList<StarGroup>(),
        ArrayList<StarGroup>(),
        ArrayList<StarGroup>(),
        ArrayList<StarGroup>(),
        ArrayList<StarGroup>(),
        ArrayList<StarGroup>(),
        ArrayList<StarGroup>(),
        ArrayList<StarGroup>(),
    )


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
                        it.allStarsCount,
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

    suspend fun getStarRepo(userName: String, repoName: String): List<List<StarGroup>> {
        val pageList = ArrayList<ConstructorStar>()
        val starsList = ArrayList<StarGroup>()
        var pageNumberStar = 1

        return try {
            do {
                val response: List<StarGroup> =
                    RetrofitInstance.api.getRepoStars(userName, repoName, pageNumberStar)

                Log.d("RESPONSE", "$response")

                val calendar = Calendar.getInstance()
                val daysResponseLong = ArrayList<Long>()
                val daysResponseLong2 = ArrayList<Long>()

                calendar.add(Calendar.DAY_OF_YEAR, -14)
                val daysAgoUnix = calendar.timeInMillis



                Log.d("DAYS_CALENDAR", "$daysAgoUnix")

                var uniqueDatAgoI = 0
                for (i in 0..13) {
                    val calendar2 = Calendar.getInstance()
                    calendar2.add(Calendar.DAY_OF_YEAR, -i)
                    val dayAgo = calendar2.time
                    Log.d("DAYSIT????", "$dayAgo")
                    uniqueDatAgoI = dayAgo.date + 31 * dayAgo.month * dayAgo.year * 1000
                    daysResponseLong.add(uniqueDatAgoI.toLong())
                    Log.d("DAYS????", "$uniqueDatAgoI")
                    Log.d("CALENDAR", "$daysResponseLong")
                    calendar2.clear()
                }

                var uniqueDaysResponse = 0

                val lll = ArrayList<ConstrGroup>()
                var num = 0


                val dateGroupLost = ArrayList<Map<Date, List<StarGroup>>>()

                response.forEach { it ->

                    val date = Date(it.starredAt.time)
                    Log.d("RESPONSE_STAREDAT_TIME", "$date")
                    uniqueDaysResponse = date.date + 31 * date.month * date.year * 1000
                    uniqueDaysResponse.toLong()
                    Log.d("RESPONSE_UNIQUE_DAYS", "$uniqueDaysResponse")

                    response.forEach {

                        if (num < 13) {
                            if (uniqueDaysResponse.toLong() == daysResponseLong[num]) {
                                daysResponseLong2.add(uniqueDaysResponse.toLong())
                                calendarLost[num].add(it)
                            }
                            Log.d("RESPONSE_LIST_SORTED", "$calendarLost")
                            num++
                        } else
                            num = 0
                    }
                }

                //for (i in 0..13) {
                //     calendarLost.groupBy {
                //         it[i].starredAt
                //     }
                //}

                Log.d("GROUPCALENDAR", "$calendarLost")
                Log.d("PAGE_LIST_SIZE", "${pageList.size}")

                if (pageList.size == MAX_PAGE_SIZE) {
                    pageList.clear()
                }
                starsList.addAll(response)
                Log.d("PAGE_NUMBER", "$pageNumberStar")
                Log.d("STARS_LIST_SIZE", "$starsList")
                pageNumberStar++

            } while (starsList.size == MIN_PAGE_SIZE)
            calendarLost
        } catch (e: Exception) {
            Log.d("SIZE_STAR_ERROR", "$e")
            return calendarLost
        }
    }
}
