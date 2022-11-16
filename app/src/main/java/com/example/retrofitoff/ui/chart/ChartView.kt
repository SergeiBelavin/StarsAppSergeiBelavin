package com.example.retrofitoff

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.retrofitoff.data.repository.Repository
import com.example.retrofitoff.mode.StatisticStarsItem

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


    fun getReposStars(userName: String, repoName: String,) {

        viewModelScope.launch {
            try {
                val response: List<StatisticStarsItem> =
                    repository.getRepoStat(userName, repoName,)

                 Log.d("ResponseCV", "$response")

                val dateLong = ArrayList<Long>()

                val calendar = Calendar.getInstance()
                calendar.add(Calendar.DAY_OF_YEAR, -60) //14 это рендж
                val daysAgoUnix = calendar.timeInMillis
                Log.d("DaysAgoCV", "$daysAgoUnix")
                Log.d("resp.starred_at[0]", response[0].starred_at.toString())

                response.forEach {
                    val dateUnix = it.starred_at.time
                    Log.d("dateUnixCV", "$dateUnix")
                    if (dateUnix > daysAgoUnix) {
                        dateLong.add(dateUnix)
                    }
                }

                Log.d("dateLongCV", "$dateLong")
                chartResponse.value = dateLong


            } catch (e: Exception) {
                Log.d("ErrorGetReposStat", "Exception: $e")
            }
        }
    }
}