package com.example.retrofitoff

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.retrofitoff.mode2.StatisticStarsItem
import com.example.retrofitoff.repository.Repository
import kotlinx.coroutines.launch
import kotlinx.serialization.json.JsonNull.long
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class ChartView(
    private val repository: Repository,

) : ViewModel() {


    //val chartResponse: MutableLiveData<List<StatisticStarsItem>> = MutableLiveData()

    private val chartResponse = MutableLiveData<List<Long>>()
    val chartResponseEmitter: LiveData<List<Long>> = chartResponse


    fun getReposStars(userName: String, repoName: String, range: Int) {

        viewModelScope.launch {
            try {
                val response: List<StatisticStarsItem> =
                    repository.getRepoStat(userName, repoName, range)

                Log.d("ResponseCV", "$response")

                val dateLong = ArrayList<Long>()

                //   val dateGroup = ArryList<String>()

                val calendar = Calendar.getInstance()
                calendar.add(Calendar.DAY_OF_YEAR, -range)
                val daysAgoUnix = calendar.timeInMillis / 1000
                Log.d("DaysAgoCV", "${daysAgoUnix}")
                Log.d("resp.starred_at[0]", response[0].starred_at)

                response.forEach {
                    val dateUnix = SimpleDateFormat("yyyy-MM-dd").parse(it.starred_at).time / 1000
                    Log.d("dateUnixCV", "$dateUnix")
                    if (dateUnix > daysAgoUnix) {
                        dateLong.add(dateUnix)
                    }
                }





                Log.d("dateLongCV", "$dateLong")

                //Log.d("LogGetRepos", "$dateLong")
                chartResponse.value = dateLong


            } catch (e: Exception) {
                Log.d("ErrorGetReposStat", "Expection: " + "${e}")
            }
        }
    }
}