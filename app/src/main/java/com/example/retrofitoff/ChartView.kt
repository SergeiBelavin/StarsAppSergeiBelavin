package com.example.retrofitoff

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.retrofitoff.mode2.StatisticStarsItem
import com.example.retrofitoff.repository.Repository
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class ChartView(
    private val repository: Repository,
) : ViewModel() {


    //val chartResponse: MutableLiveData<List<StatisticStarsItem>> = MutableLiveData()

    private val chartResponse = MutableLiveData<List<Long>>()
    val chartResponseEmitter: LiveData<List<Long>> = chartResponse

    fun getReposStars(userName: String, repoName: String) {
        viewModelScope.launch {
            try {
                val response: List<StatisticStarsItem> =
                    repository.getRepoStat(userName, repoName)

                Log.d("MyLog2", "$response")

                val dateLong = ArrayList<Long>()
                    //   val dateGroup = ArryList<String>()

                response.forEach {
                    val dateUnix = SimpleDateFormat("yyyy-MM-dd").parse(it.starred_at).time
                    dateLong.add(dateUnix)
                }
                Log.d("LogGetRepos", "$dateLong")
                chartResponse.value = dateLong

                dateLong.groupBy {
                    it
                }
                Log.d("group", "$dateLong")



            } catch (e: Exception) {
                Log.d("ErrorGetReposStat", "Expection: " + "${e}")
            }
        }
    }
}