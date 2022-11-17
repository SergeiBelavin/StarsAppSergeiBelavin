package com.example.retrofitoff

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.retrofitoff.data.repository.Repository
import com.example.retrofitoff.mode.StatisticsStarsItem

import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList


class ChartView(
    private val repository: Repository,

    ) : ViewModel() {


    //val chartResponse: MutableLiveData<List<StatisticStarsItem>> = MutableLiveData()

    private val chartResponse = MutableLiveData<List<Long>>()
    val chartResponseEmitter: LiveData<List<Long>> = chartResponse


    fun getReposStars(userName: String, repoName: String,) {

        viewModelScope.launch {
            try {
                val response: List<StatisticsStarsItem> =
                    repository.statisticsRepository(userName, repoName,)

                 Log.d("ResponseCV", "$response")

                val dateLong = ArrayList<Long>()

                val calendar = Calendar.getInstance()
                calendar.add(Calendar.DAY_OF_YEAR, -1)
                val daysAgoUnix = calendar.timeInMillis
                Log.d("DaysAgoCV", "$daysAgoUnix")


                response.forEach {
                    val dateUnix = it.starred_at.time
                    Log.d("dateUnixCV", "$dateUnix")
                    if (dateUnix < daysAgoUnix) {
                        dateLong.add(dateUnix)
                    }
                }


                chartResponse.value = dateLong



            } catch (e: Exception) {
                Log.d("ErrorGetReposStat", "Exception: $e")
            }
        }
    }
}