package com.example.retrofitoff

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.retrofitoff.data.entity.StarGroup
import com.example.retrofitoff.data.repository.Repository
import com.example.retrofitoff.model.StarGroupItem

import kotlinx.coroutines.launch
import java.io.Serializable
import java.util.*
import kotlin.collections.ArrayList


class ChartView(
    private val repository: Repository,

    ) : ViewModel() {


    //val chartResponse: MutableLiveData<List<StatisticStarsItem>> = MutableLiveData()

    val chartResponse = MutableLiveData<Serializable>()

    fun getReposStars(userName: String, repoName: String,) {

        viewModelScope.launch {
            try {
                val response: Serializable =
                    repository.getStarRepo(userName, repoName,)
                chartResponse.value = response

                Log.d("WOWOWOWOW", "Exception: $response")

            } catch (e: Exception) {
                Log.d("ErrorGetReposStat", "Exception: $e")
            }
        }
    }
}