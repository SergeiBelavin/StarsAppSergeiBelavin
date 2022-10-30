package com.example.retrofitoff

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.retrofitoff.mode2.RepoStatistic

import com.example.retrofitoff.modelReposUser.PostRepos
import com.example.retrofitoff.modelStatStars.StatStars


import com.example.retrofitoff.repository.Repository
import kotlinx.coroutines.launch
import retrofit2.Response

class MainViewModel(
    private val repository: Repository): ViewModel() {


    val myResponse: MutableLiveData<Response<PostRepos>> = MutableLiveData()
    val myResponse2: MutableLiveData<Response<StatStars>> = MutableLiveData()

    fun getPost(userName: String) {
        viewModelScope.launch {
            val response: Response<PostRepos> = repository.getPost(userName)
            myResponse.value = response
        }
    }
    fun getPosStat(userName: String, repoName:String) {
        viewModelScope.launch {
            val response2: Response<StatStars> = repository.getRepoStat(userName, repoName)
            myResponse2.value = response2
        }
    }
}


