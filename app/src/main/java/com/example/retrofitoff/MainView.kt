package com.example.retrofitoff

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.retrofitoff.mode2.RepositoriesUser
import com.example.retrofitoff.mode2.RepositoriesUserItem


import com.example.retrofitoff.repository.Repository
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response

class MainView(
    private val repository: Repository): ViewModel() {


    val myResponse: MutableLiveData<Call<RepositoriesUserItem>> = MutableLiveData()

    fun getRepoList(userName: String) {
        viewModelScope.launch {
            try {
                val response: Call<RepositoriesUserItem> = repository.getRepoList(userName)
                myResponse.value = response


            } catch (e: Exception) {
                Log.d("ErrorGetRepoList", "Expection: " + "${e}")
            }

    }
}
    }


