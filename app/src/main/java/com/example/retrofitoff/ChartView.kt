package com.example.retrofitoff

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.retrofitoff.mode2.StatisticsStars
import com.example.retrofitoff.repository.Repository
import kotlinx.coroutines.launch
import retrofit2.Response


class ChartView(
    private val repository: Repository
): ViewModel() {

    
    val myResponse2: MutableLiveData<Response<StatisticsStars>> = MutableLiveData()
    
    fun ReposStars(userName: String, repoName:String) {
        viewModelScope.launch {
            try {
                val response2: Response<StatisticsStars> =
                    repository.getRepoStat(userName, repoName)
                myResponse2.value = response2
            } catch (e: Exception) {
                Log.d("ErrorGetReposStat", "Expection: " + "${e}")
            }
        }
    }
}