package com.example.retrofitoff

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.example.retrofitoff.mode2.ReposUser


import com.example.retrofitoff.repository.Repository
import kotlinx.coroutines.launch
import retrofit2.Response

class MainViewModel(
    private val repository: Repository): ViewModel() {


    val myResponse: MutableLiveData<Response<ReposUser>> = MutableLiveData()

    fun getPost(userName: String) {
        viewModelScope.launch {
            val response: Response<ReposUser> = repository.getPost(userName)
            myResponse.value = response
        }
    }
}


