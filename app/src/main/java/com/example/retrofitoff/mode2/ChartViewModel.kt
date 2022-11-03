package com.example.retrofitoff.mode2

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.retrofitoff.repository.Repository
import kotlinx.coroutines.launch
import retrofit2.Response


class ChartViewModel(
    private val repository: Repository
): ViewModel() {

    
    val myResponse2: MutableLiveData<Response<StatiStarsUsersClass>> = MutableLiveData()
    
    fun getPosStat(userName: String, repoName:String) {
        viewModelScope.launch {
            val response2: Response<StatiStarsUsersClass> = repository.getRepoStat(userName, repoName)
            myResponse2.value = response2
        }
    }
}