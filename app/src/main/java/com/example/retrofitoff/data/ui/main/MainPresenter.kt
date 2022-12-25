package com.example.retrofitoff.data.ui.main

import Repository
import android.net.http.HttpResponseCache
import android.util.Log
import coil.network.HttpException
import com.example.retrofitoff.model.RepoUser
import moxy.InjectViewState
import moxy.MvpPresenter
import okio.IOException
import java.net.HttpRetryException
import java.net.HttpURLConnection
import java.net.UnknownHostException

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
            viewState.startSending(true)
            return repoList


        } catch (e: Exception) {
            Log.d("${TAG}_ERROR_GET_REPO", "Exception: $e")
            Log.d("${TAG}_ERROR_GET_REPO", "Exception: ${e.message}")
            //if (e.localizedMessage?.hashCode() == 964672022) {
            //    viewState.startSending(true)
                viewState.showError("Отсутствует подключение к интернету")
            viewState.startSending(true)
            //}

            return repoList

        } catch (e: IOException) {
            Log.d("${TAG}_ERROR_GET_REPO", "IOException: $e")

            if (e.hashCode() == -1358142848) {
                viewState.startSending(true)
                viewState.showError("Пользователь не найден")
            }

            if (e.hashCode() == 403) {
                viewState.startSending(true)
                viewState.showError("Лимит запросов закончился")
            }
            return repoList
        } catch (e: HttpException) {
            Log.d("${TAG}_ERROR_GET_REPO", "HTTP: $e")
            e.response.code
            Log.d("${TAG}_ERROR_GET_REPO", "${e.response.code}")
            viewState.startSending(true)
            viewState.showError("<:")
        }
         return repoList

    }

    override fun onClickAdapter(list: RepoUser) {

    }
}
