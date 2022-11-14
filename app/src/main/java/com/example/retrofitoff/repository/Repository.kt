package com.example.retrofitoff.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.retrofitoff.RetrofitInstance
import com.example.retrofitoff.mode2.RepositoriesUserItem
import com.example.retrofitoff.mode2.StatisticStars

import com.example.retrofitoff.mode2.StatisticStarsItem
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class Repository {

    private val chartResponse = MutableLiveData<List<Long>>()
    val chartResponseEmitter: LiveData<List<Long>> = chartResponse

    suspend fun getRepoList(userName: String): List<RepositoriesUserItem> {
        return RetrofitInstance.api.getRepoList(userName)
    }
    suspend fun getRepoStat(userName: String, repoName: String): List<StatisticStarsItem> {

        Log.d("ResponseCV2", "blya")

        //val response: List<StatisticStarsItem> =
         //   getRepoStat(userName, repoName,)
      //  val response = getRepoStat(userName, repoName)

        //Log.d("ResponseCV", "$222")

        val dateLong = ArrayList<Long>()

        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_YEAR, -14)
        val daysAgoUnix = calendar.timeInMillis / 1000
        Log.d("DaysAgoCV", "${daysAgoUnix}")
       Log.d("resp.starred_at[0]", "${getRepoStat(userName, repoName)}")

       // response.forEach {
       //     val dateUnix = SimpleDateFormat("yyyy-MM-dd").parse(it.starred_at).time / 1000
        //    Log.d("dateUnixCV", "$dateUnix")
         //   if (dateUnix > daysAgoUnix) {
        //        dateLong.add(dateUnix)
        //    }
      //  }
        Log.d("dateLongCV", "$dateLong")
        return RetrofitInstance.api.getRepoStat(userName, repoName)
    }
// Надо чарт активити привести в изначальное состояние где листы ловили статистику, а не лонг. Затем передавать данные в репозиторий и обрабатывать их
}
