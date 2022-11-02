package com.example.retrofitoff.mode2

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.retrofitoff.repository.Repository
import kotlinx.coroutines.launch
import retrofit2.Response


class CharViewModel(
    private val repository: Repository
): ViewModel() {

    
    val myResponse2: MutableLiveData<Response<StatiStarsUsers>> = MutableLiveData()
    
    fun getPosStat(userName: String, repoName:String) {
        viewModelScope.launch {
            val response2: Response<StatiStarsUsers> = repository.getRepoStat(userName, repoName)
            myResponse2.value = response2
        }
    }
}