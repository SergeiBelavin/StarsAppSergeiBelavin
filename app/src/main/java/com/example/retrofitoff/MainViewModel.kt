package com.example.retrofitoff

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.retrofitoff.mode2.RepositoriesUserItemClass




import com.example.retrofitoff.repository.Repository
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response

class MainViewModel(
    private val repository: Repository): ViewModel() {


    val myResponse: MutableLiveData<Call<List<RepositoriesUserItemClass?>?>> = MutableLiveData()

    fun getPost(userName: String) {
        viewModelScope.launch {
            val response: Call<List<RepositoriesUserItemClass?>?> = repository.getPost(userName)
            myResponse.value = response
    }
}
    }


