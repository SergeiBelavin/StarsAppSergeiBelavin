package com.example.retrofitoff.data.ui.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.retrofitoff.model.RepoUser

import com.example.retrofitoff.data.repository.Repository
import kotlinx.coroutines.launch

import java.io.IOException

class MainViewModel(
    private val repository: Repository,
) : ViewModel() {

    val myResponse: MutableLiveData<List<RepoUser>> = MutableLiveData()
    val error: MutableLiveData<String> = MutableLiveData()

    fun repoList(userName: String) {

        viewModelScope.launch {
            try {
                val response: List<RepoUser> = repository.getListRepository(userName)
                myResponse.value = response

            } catch (e: IOException) {
                Log.d("MAIN_V_ERROR_GET_REPO", "Exception: $e")
                //Log.d("ErrorGetRepoList3", "Exception: ${e.message}")
                //Log.d("ErrorGetRepoList4", "Exception: ${e.localizedMessage?.hashCode()}")
                if (e.localizedMessage?.hashCode() == 964672022) {
                    error.value = "Отсутствует подключение к интернету"
                }

            } catch (e: Exception) {
                Log.d("MAIN_V_ERROR_GET_REPO", "Exception: $e")

                if (e.localizedMessage?.hashCode() == -1358142848) {
                    error.value = "Пользователь не найден"
                }

                if (e.localizedMessage?.hashCode() == -1358142879) {
                    error.value = "Лимит запросов закончился"
                }
            }
        }
    }
}



