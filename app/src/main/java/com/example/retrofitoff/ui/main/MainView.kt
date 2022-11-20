package com.example.retrofitoff.ui.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.retrofitoff.model.RepoUserItem


import com.example.retrofitoff.data.repository.Repository
import kotlinx.coroutines.launch

class MainView(
    private val repository: Repository,
) : ViewModel() {


    val myResponse: MutableLiveData<List<RepoUserItem>> = MutableLiveData()

    fun repoList(userName: String) {
        viewModelScope.launch {
            try {
                val response: List<RepoUserItem> = repository.getListRepository(userName)
                myResponse.value = response

            } catch (e: Exception) {
                Log.d("ErrorGetRepoList", "Expection: " + "${e}")
            }

        }
    }
}


