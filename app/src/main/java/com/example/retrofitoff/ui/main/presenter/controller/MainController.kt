package com.example.retrofitoff.ui.main.presenter.controller

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.retrofitoff.data.repository.Repository
import com.example.retrofitoff.model.RepoUser
import kotlinx.coroutines.launch
import okhttp3.Response
import java.io.IOException

object MainController {
    private var repo = Repository()
    private val myResponse: MutableLiveData<List<RepoUser>> = MutableLiveData()

    interface ControllerDelegate {
        fun onSuccess(response: String)
        fun onFilded()
    }
    suspend fun repoList(userName: String,) {

            try {
                val response: List<RepoUser> = repo.getListRepository(userName,)
                myResponse.value = response


            } catch (e: Exception) {
                Log.d("ErrorGetRepoList", "Exception: $e")
            }
            catch (e: IOException) {
                Log.d("ErrorGetRepoList", "Exception: $e")
            }

    }
}