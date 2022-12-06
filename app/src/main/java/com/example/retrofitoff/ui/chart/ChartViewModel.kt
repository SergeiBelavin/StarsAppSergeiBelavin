package com.example.retrofitoff.ui.chart

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.retrofitoff.data.entity.StarGroup
import com.example.retrofitoff.data.entity.constructor.ConstructorStar
import com.example.retrofitoff.data.repository.Repository

import kotlinx.coroutines.launch


class ChartViewModel(
    private val repository: Repository,


    ) : ViewModel() {

    val chartResponse = MutableLiveData<List<StarGroup>>()

    fun getReposStars(userName: String, repoName: String, groupType: EnumRange.Companion.GroupType) {
        viewModelScope.launch {
            try {
                val response: List<StarGroup> =
                    repository.getStarRepo(userName, repoName, groupType)
                chartResponse.value = response

            } catch (e: Exception) {
                Log.d("Error ", "Exception: $e")
            }
        }
    }
}