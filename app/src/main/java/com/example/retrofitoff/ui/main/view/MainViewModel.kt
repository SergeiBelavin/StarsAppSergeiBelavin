package com.example.retrofitoff.ui.main.view

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.retrofitoff.model.RepoUser

import com.example.retrofitoff.data.repository.Repository
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import java.io.IOException

class MainViewModel(
    private val repository: Repository,
) : ViewModel() {


    val myResponse: MutableLiveData<List<RepoUser>> = MutableLiveData()

    private val client = OkHttpClient()

     fun repoList(userName: String,) {

        viewModelScope.launch {
            try {
                val response: List<RepoUser> = repository.getListRepository(userName,)
                myResponse.value = response


            } catch (e: Exception) {
                Log.d("ErrorGetRepoList", "Exception: $e")
            }
            catch (e: IOException ) {
                Log.d("ErrorGetRepoList", "Exception: $e")
            }
        }
    }
}



