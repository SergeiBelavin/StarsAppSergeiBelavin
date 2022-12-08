package com.example.retrofitoff.data.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData


import com.example.retrofitoff.data.entity.RepoUser


import com.example.retrofitoff.data.entity.StarGroup
import com.example.retrofitoff.data.entity.User
import com.example.retrofitoff.data.entity.constructor.ConstructorRepo
import com.example.retrofitoff.data.entity.constructor.ConstructorStar
import com.example.retrofitoff.data.entity.constructor.ConstructorUser
import com.example.retrofitoff.ui.chart.EnumRange
import java.io.IOException
import java.util.*
import kotlin.collections.ArrayList


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
            Log.d("NotInter", "$e")
            error.value = "Отсутствует соединение с интернетом"
            responseList
        } catch (e: Exception) {
            Log.d("UserNitFound", "$e")
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
        val lastData = daysResponseInt.lastIndex
        var pageNumberStar = 1

        return try {
            do {
                val response: List<StarGroup> =
                    RetrofitInstance.api.getRepoStars(userName, repoName, pageNumberStar)
                starsList.addAll(response)

                if (starsList.size == MIN_PAGE_SIZE) return listResponse
                if (starsList.size == MAX_PAGE_SIZE) starsList.clear()
                if(UniqueDate().getUniqueDate(response[0].starredAt) < lastData) {
                    return listResponse
                } else {
                    pageNumberStar++
                    processingResponse(response, groupType)
                }

            } while (starsList.size == MIN_PAGE_SIZE || stopPaging == 1)

            starsList.clear()
            listResponse

        } catch (e: Exception) {
            Log.d("NotInter", "$e")
            error.value = "Отсутствует соединение с интернетом"
            listResponse
        } catch (e: IOException) {
            Log.d("NotInter", "$e")
            error.value = "Отсутствует соединение с интернетом"
            listResponse
        }
        listResponse.clear()
        starsList.clear()
    }
    private fun processingResponse(
        list: List<StarGroup>,
        groupType: EnumRange.Companion.GroupType,
    ) {
        val daysResponseInt = UniqueDate().getUniqueArrayList(EnumRange.groupsType(groupType))
        val lastData = daysResponseInt.lastIndex

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
