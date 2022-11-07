package com.example.retrofitoff

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.retrofitoff.mode2.StatisticStars
import com.example.retrofitoff.mode2.StatisticStarsItem
import com.example.retrofitoff.repository.Repository
import kotlinx.coroutines.launch
import retrofit2.Response


class ChartView(
    private val repository: Repository
): ViewModel() {

    
    val myResponse2: MutableLiveData<Response<StatisticStars>> = MutableLiveData()
    
    fun getReposStars (userName: String, repoName:String) {
        viewModelScope.launch {
            try {
                val response2: Response<StatisticStars> =
                    repository.getRepoStat(userName, repoName)
                myResponse2.value = response2

            } catch (e: Exception) {
                Log.d("ErrorGetReposStat", "Expection: " + "${e}")
            }
        }
    }
}