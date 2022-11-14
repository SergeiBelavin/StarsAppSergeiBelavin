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

    private val chartResponse = MutableLiveData<List<Long>>()
    val chartResponseEmitter: LiveData<List<Long>> = chartResponse

    fun getReposStars(userName: String, repoName: String,) {

        viewModelScope.launch {
            try {
                //Log.d("ResponseCV2", "blya")

                val response: List<StatisticStarsItem> =
                    repository.getRepoStat(userName, repoName,)

                Log.d("ResponseCV3", "blya")

            } catch (e: Exception) {
                Log.d("ErrorGetReposStat", "Expection: " + "${e}")
            }
        }
    }
}