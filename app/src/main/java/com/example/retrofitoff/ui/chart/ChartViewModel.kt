package com.example.retrofitoff

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.retrofitoff.data.entity.RepoUser
import com.example.retrofitoff.data.entity.StarGroup
import com.example.retrofitoff.data.repository.Repository
import com.example.retrofitoff.model.StarGroupItem

import kotlinx.coroutines.launch
import okhttp3.internal.userAgent
import java.io.Serializable


class ChartViewModel(
    private val repository: Repository,


    ) : ViewModel() {

    //val chartResponse: MutableLiveData<List<StatisticStarsItem>> = MutableLiveData()

    val chartResponse = MutableLiveData<List<StarGroup>>()

    fun getReposStars(userName: String, repoName: String) {
        viewModelScope.launch {
            try {
                val response: List<StarGroup> =
                    repository.getStarRepo(userName, repoName)
                chartResponse.value = response
                Log.d("Response ", "Exception: $response")

            } catch (e: Exception) {
                Log.d("Error ", "Exception: $e")
            }
        }
    }
}