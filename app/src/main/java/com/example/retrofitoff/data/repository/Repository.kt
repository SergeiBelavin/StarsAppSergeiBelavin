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
                    val constructorRepo = ConstructorRepo(it.id, it.name, it.allStarsCount, ConstructorUser(it.id, it.name))
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

    suspend fun getStarRepo(userName: String, repoName: String): List<StarGroup> {
        val pageList = ArrayList<ConstructorStar>()
        val groupPageList = ArrayList<StarGroup>()
        val starsList = ArrayList<StarGroup>()
        val starsListResponse = ArrayList<StarGroup>()
        val starsListGroup = ArrayList<StarGroup>()
        val starsListSorting = ArrayList<ConstructorStar>()
        var pageNumberStar = 1

        return try {
            do {
                val response: List<StarGroup> =
                    RetrofitInstance.api.getRepoStars(userName, repoName, pageNumberStar)

                Log.d("RESPONSE", "$response")

                val calendar = Calendar.getInstance()
                val daysAgo = ArrayList<Long>()
                val daysAgo2 = ArrayList<Date>()
                val daysResponse = ArrayList<Date>()
                val daysResponseLong = ArrayList<Long>()
                val daysResponseLong2 = ArrayList<Int>()

                calendar.add(Calendar.DAY_OF_YEAR, -14)
                val daysAgoUnix = calendar.timeInMillis

                var uniqueDatAgoI = 0

                Log.d("DAYS_CALENDAR", "$daysAgoUnix")
                val calendar2 = Calendar.getInstance()

                for (i in 0..13) {

                    calendar2.add(Calendar.DAY_OF_YEAR, -i)
                    val dayAgo = calendar.time
                    Log.d("DAYSIT????", "$dayAgo")
                    uniqueDatAgoI = dayAgo.date+31*dayAgo.month/100
                    daysResponseLong.add(uniqueDatAgoI.toLong())
                    Log.d("DAYS????", "$uniqueDatAgoI")
                    Log.d("DAYSLIST????", "$daysResponseLong")
                    calendar2.clear()
                }

                var uniqueDaysResponse = 0
                var num = 0
                response.forEach {
                    if (it.starredAt.time == daysResponseLong[num]) {
                        val grItem = response.groupBy {
                            it.user.name
                        }
                        starsListSorting.add()
                    }
                        num++
                }
                num = 0

                val groupBy = response.groupBy {
                     it.user.name
                    }



                response.forEach {
                    val date = Date(it.starredAt.time)
                    Log.d("DAYSDATE!!!!", "$date")
                    uniqueDaysResponse = date.date+31*date.month/100
                    daysResponseLong2.add(uniqueDaysResponse)
                    Log.d("DAYS!!!!", "$uniqueDaysResponse")
                }


                Log.d("DAYS_AGO", "")
                Log.d("GROUP_BY1", "$groupBy")
                Log.d("GROUP_BY1", "$groupBy")
                Log.d("GROUP1", "$starsListGroup")
                Log.d("GROUP2", "$starsListResponse")

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
