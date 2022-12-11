package com.example.retrofitoff.data.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData


import com.example.retrofitoff.model.RepoUser


import com.example.retrofitoff.model.StarGroup
import com.example.retrofitoff.model.User
import com.example.retrofitoff.data.entity.constructor.ConstructorRepo
import com.example.retrofitoff.ui.chart.EnumRange
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.log


open class Repository() {

    private val MAX_PAGE_SIZE = 100
    private val MIN_PAGE_SIZE = 0
    private val starsList = ArrayList<StarGroup>()
    private val listResponse = ArrayList<StarGroup>()
    private var stopPaging = 0
    val error: MutableLiveData<String> = MutableLiveData()

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

        } catch (e: IOException) {
            Log.d("NO_INTERNET", "$e")
            error.value = "Отсутствует соединение с интернетом"
            responseList
        }
        catch (e: Exception) {
            Log.d("USER_NOT_FOUND", "$e")
            error.value = "Пользователь не найден"
            responseList
        }
    }

    suspend fun getStarRepo(
        userName: String,
        repoName: String,
        groupType: EnumRange.Companion.GroupType,
    ): List<StarGroup> {
        val daysResponseInt = UniqueDate().getUniqueArrayList(EnumRange.groupsType(groupType))
        val lastData = daysResponseInt[daysResponseInt.size-1]
        var pageNumberStar = 1
        Log.d("DATE_LIST_LASTDATE1", "$lastData")
        Log.d("DATE_LIST_LASTDATE2", "$daysResponseInt")

        return try {
            do {
                val response: List<StarGroup> =
                    RetrofitInstance.api.getRepoStars(userName, repoName, pageNumberStar)

                starsList.addAll(response)
                Log.d("DATE_LIST_LASTDATE3", "${UniqueDate().getUniqueDate(response[0].starredAt)}")
                Log.d("DATE_LIST_LASTDATE4", "${response[0].starredAt}")
                Log.d("DATE_LIST_LASTDATE5", "${starsList}")

                if (starsList.size == MIN_PAGE_SIZE){
                    stopPaging = 1
                    return listResponse
                }

                if (starsList.size == MAX_PAGE_SIZE) starsList.clear()

                //if(UniqueDate().getUniqueDate(response[0].starredAt) < lastData) {
                //    stopPaging = 1
                //    return listResponse
                //}

                    pageNumberStar++
                    processingResponse(response, groupType)

                Log.d("DATE_LIST_PROCESSING", "$listResponse")


            } while (starsList.size == MIN_PAGE_SIZE || stopPaging == 1 || UniqueDate().getUniqueDate(response[0].starredAt) < lastData)
            starsList.clear()
            listResponse

        } catch (e: Exception) {
            Log.d("NotInter", "$e")
            error.value = "Отсутствует соединение с интернетом"
            listResponse
        }
        //catch (e: IOException) {
        //    Log.d("NotInter", "$e")
        //    error.value = "Отсутствует соединение с интернетом"
        //    listResponse
        //}
        listResponse.clear()
        starsList.clear()
    }
    private fun processingResponse(
        list: List<StarGroup>,
        groupType: EnumRange.Companion.GroupType,
    ) {
        val daysResponseInt = UniqueDate().getUniqueArrayList(EnumRange.groupsType(groupType))
        val lastData = daysResponseInt[daysResponseInt.size -1]

         list.forEach {

            val dateToInt = UniqueDate().getUniqueDate(it.starredAt)

            if (dateToInt >= lastData) {
                val starGroup = object : StarGroup {
                    override val starredAt: Date
                        get() = it.starredAt
                    override val user: User
                        get() = it.user
                    override val uniqueDate: Int?
                        get() = dateToInt
                }
                listResponse.add(starGroup)
            } else {
                stopPaging = 1
            }
        }
    }
}
