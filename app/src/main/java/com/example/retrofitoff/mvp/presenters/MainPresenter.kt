package com.example.retrofitoff.mvp.presenters

import android.util.Log
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.viewmodel.CreationExtras
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.example.retrofitoff.data.repository.Repository
import com.example.retrofitoff.model.RepoUser
import com.example.retrofitoff.mvp.RepoAdapter
import com.example.retrofitoff.mvp.activity.ChartActivity
import com.example.retrofitoff.mvp.views.MainViewModel
import com.example.retrofitoff.mvp.views.MainView
import java.io.IOException

@InjectViewState
class MainPresenter(private val mainRepository: Repository) : MvpPresenter<MainView>() {
    private val TAG = MainPresenter::class.java.simpleName
    private var repoList = emptyList<RepoUser>()

     fun getRepoList(userName: String): List<RepoUser> {

        try {
            val response: List<RepoUser> = mainRepository.getListRepository(userName)
            repoList = response
            return repoList
            Log.d("${TAG}_ERROR_GET_REPO", "Exception: wwww")

        } catch (e: IOException) {
            Log.d("${TAG}_ERROR_GET_REPO", "Exception: $e")
            //Log.d("ErrorGetRepoList3", "Exception: ${e.message}")
            //Log.d("ErrorGetRepoList4", "Exception: ${e.localizedMessage?.hashCode()}")
            if (e.localizedMessage?.hashCode() == 964672022) {
                viewState.showError("Отсутствует подключение к интернету")
            }

            return repoList

        } catch (e: Exception) {
            Log.d("${TAG}_ERROR_GET_REPO", "Exception: $e")

            if (e.localizedMessage?.hashCode() == -1358142848) {
                viewState.showError("Пользователь не найден")
            }

            if (e.localizedMessage?.hashCode() == -1358142879) {
                viewState.showError("Лимит запросов закончился")
            }
            return repoList
        }
    }
}
