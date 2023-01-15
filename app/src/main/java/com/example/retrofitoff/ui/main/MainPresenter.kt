package com.example.retrofitoff.ui.main

import Repository
import android.util.Log
import com.example.retrofitoff.model.RepoUser
import moxy.InjectViewState
import moxy.MvpPresenter
import retrofit2.http.HTTP
import java.net.UnknownHostException

@InjectViewState
class MainPresenter(private val mainRepository: Repository) : MvpPresenter<MainView>(),
    RepoAdapter.Listener {

    private val TAG = MainPresenter::class.java.simpleName
    private var repoList = emptyList<RepoUser>()


    suspend fun getRepoList(userName: String): List<RepoUser> {

        try {

            viewState.unlockedClick(false)
            val response: List<RepoUser> = mainRepository.getListRepository(userName)

            repoList = response
            Log.d("BBBBBB", "Ebbb")
            viewState.unlockedClick(true)
            return repoList

        }
        catch (e: UnknownHostException) {
            Log.d("${TAG}_Unknown", "Exception: ${e}")
            viewState.showError("Нет подключения к интернету")
        }
        catch (e: retrofit2.HttpException) {
            Log.d("${TAG}_Unknown", "Exception: ${e}")
            Log.d("${TAG}_Unknown", "Exception: ${e.code()}")

            when(e.code()) {
                404 -> viewState.showError("Пользователь не найден")
                403 -> viewState.showError("Лимит запросов закончился")
            }
        }

        finally {

        }

        return repoList

    }

    override fun onClickAdapter(list: RepoUser) {

    }
}
