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

    val chartResponse = MutableLiveData<List<List<StarGroup>>>()

    fun getReposStars(userName: String, repoName: String, groupType: Repository.GroupType) {
        viewModelScope.launch {
            try {
                val response: List<List<StarGroup>> =
                    repository.getStarRepo(userName, repoName, groupType)
                chartResponse.value = response

                Log.d("Response ", "Exception: ${response[4]}")

            } catch (e: Exception) {
                Log.d("Error ", "Exception: $e")
            }
        }
    }
}