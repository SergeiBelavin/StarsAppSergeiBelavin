package com.example.retrofitoff.ui.chart

import android.nfc.Tag
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.retrofitoff.data.entity.StarGroup
import com.example.retrofitoff.data.exception.Limit
import com.example.retrofitoff.data.repository.Repository

import kotlinx.coroutines.launch
import okhttp3.Response
import java.io.IOException


class ChartViewModel(
    private val repository: Repository,


    ) : ViewModel() {

    val chartResponse = MutableLiveData<List<StarGroup>>()
    val error = MutableLiveData<String>()

    fun getReposStars(userName: String, repoName: String, groupType: EnumRange.Companion.GroupType) {
        viewModelScope.launch {
            try {
                val response: List<StarGroup> =
                    repository.getStarRepo(userName, repoName, groupType)
                chartResponse.value = response
            } catch (e: IOException) {
                Log.d("EXCEPTION_CHART_VIEW", "Exception: $e")
            }
        }
    }

}