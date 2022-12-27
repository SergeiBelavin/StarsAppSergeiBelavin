package com.example.retrofitoff.data.ui.main

import Repository
import android.net.http.HttpResponseCache
import android.util.Log
import coil.network.HttpException
import com.example.retrofitoff.model.RepoUser
import com.google.gson.Gson
import moxy.InjectViewState
import moxy.MvpPresenter
import okhttp3.MediaType
import okhttp3.ResponseBody
import okio.IOException
import okio.utf8Size
import java.net.HttpRetryException
import java.net.HttpURLConnection
import java.net.UnknownHostException

@InjectViewState
class MainPresenter(private val mainRepository: Repository) : MvpPresenter<MainView>(),
    RepoAdapter.Listener {
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

        } catch (e: HttpException) {
            Log.d("${TAG}_ERROR_GET_REPO", "HttpException: ${e.localizedMessage?.hashCode()}")
            if (e.response.code == 403) {
                viewState.showError("wwww")
            }
            viewState.startSending(true)
            viewState.showError("re")

            return repoList
        } catch (e: Exception) {
            Log.d("${TAG}_ERROR_GET_REPO", "Exception: ${e.localizedMessage?.hashCode()}")

            if (e.localizedMessage?.hashCode() == -1358142879) {
                viewState.showError("Лимит запросов закончился")
                viewState.startSending(true)
            }
            if (e.localizedMessage?.hashCode() == 964672022) {
                viewState.showError("Отсутствует подключение к интернету")
                viewState.startSending(true)
            }

            return repoList

        } catch (e: IOException) {
            Log.d("${TAG}_ERROR_GET_REPO", "IOException: $e")

            if (e.hashCode() == -1358142848) {
                viewState.startSending(true)
                viewState.showError("Пользователь не найден")
            }
            return repoList
        }
        return repoList

    }

    override fun onClickAdapter(list: RepoUser) {

    }
}
