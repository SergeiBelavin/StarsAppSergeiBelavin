package com.example.retrofitoff.ui.main

import android.content.Context
import android.os.Build.VERSION_CODES.M
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.retrofitoff.data.entity.RepoUser

import com.example.retrofitoff.data.repository.Repository
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import java.io.IOException

class MainViewModel(
    private val repository: Repository,
) : ViewModel() {


    val myResponse: MutableLiveData<List<RepoUser>> = MutableLiveData()
    val error: MutableLiveData<String> = MutableLiveData()


    private val client = OkHttpClient()

     fun repoList(userName: String,) {

        viewModelScope.launch {
            try {
                val response: List<RepoUser> = repository.getListRepository(userName,)
                myResponse.value = response


            } catch (e: Exception) {
                Log.d("ErrorGetRepoList", "Exception: $e")
                val userNotFound = "retrofit2.HttpException: HTTP 404"
                    error.value = "Пользователь не найден"
            }
            catch (e: IOException ) {
                Log.d("OOO", "Exception: $e")
            }
        }
    }
}



