package com.example.retrofitoff.mvp.presenters

import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter

import com.example.retrofitoff.data.repository.Repository
import com.example.retrofitoff.model.RepoUser
import com.example.retrofitoff.mvp.RepoAdapter
import com.example.retrofitoff.mvp.views.MainView
import java.io.IOException

@InjectViewState
class MainPresenter(private val mainRepository: Repository) : MvpPresenter<MainView>(), RepoAdapter.Listener {
    private val TAG = MainPresenter::class.java.simpleName



    private var repoList = emptyList<RepoUser>()


    fun getRepoListForRcView(userName: String) {
        getRepoList(userName)
    }

     fun getRepoList(userName: String): List<RepoUser> {
        viewState.startSending()
        try {
            val response: List<RepoUser> = mainRepository.getListRepository(userName)
            repoList = response
            viewState.endSending()
            return repoList

        } catch (e: IOException) {
            Log.d("${TAG}_ERROR_GET_REPO", "Exception: $e")
            if (e.localizedMessage?.hashCode() == 964672022) {
                viewState.showError("Отсутствует подключение к интернету")

            }
            viewState.endSending()
            return repoList

        } catch (e: Exception) {
            Log.d("${TAG}_ERROR_GET_REPO", "Exception: $e")

            if (e.localizedMessage?.hashCode() == -1358142848) {
                viewState.showError("Пользователь не найден")
            }

            if (e.localizedMessage?.hashCode() == -1358142879) {
                viewState.showError("Лимит запросов закончился")
            }
            viewState.endSending()
            return repoList
        }
    }

    override fun onClickAdapter(list: RepoUser) {
        TODO("Not yet implemented")
    }
}
