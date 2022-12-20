package com.example.retrofitoff.data.ui.main

import android.util.Log
import androidx.lifecycle.lifecycleScope

import com.example.retrofitoff.data.repository.Repository
import com.example.retrofitoff.model.RepoUser
import kotlinx.coroutines.launch
import moxy.InjectViewState
import moxy.MvpPresenter
import java.io.IOException

@InjectViewState
class MainPresenter(private val mainRepository: Repository) : MvpPresenter<MainView>(), RepoAdapter.Listener {
    private val TAG = MainPresenter::class.java.simpleName

    private var repoList = emptyList<RepoUser>()


     suspend fun getRepoList(userName: String): List<RepoUser> {


        try {
            viewState.startSending(false)
            val response: List<RepoUser> = mainRepository.getListRepository(userName)
            repoList = response
            Log.d("BBBBBB", "Ebbb")
            getListToMain(repoList)
            viewState.startSending(true)
            return repoList


        } catch (e: IOException) {
            Log.d("${TAG}_ERROR_GET_REPO", "Exception: $e")
            if (e.localizedMessage?.hashCode() == 964672022) {
                viewState.startSending(false)
                viewState.showError("Отсутствует подключение к интернету")
            }
            return repoList

        } catch (e: Exception) {
            Log.d("${TAG}_ERROR_GET_REPO", "Exception: $e")

            if (e.localizedMessage?.hashCode() == -1358142848) {
                viewState.startSending(false)
                viewState.showError("Пользователь не найден")
            }

            if (e.localizedMessage?.hashCode() == -1358142879) {
                viewState.startSending(false)
                viewState.showError("Лимит запросов закончился")
            }
            return repoList
        }

    }

    fun getListToMain(list: List<RepoUser>) {

    }

    override fun onClickAdapter(list: RepoUser) {

    }
}
