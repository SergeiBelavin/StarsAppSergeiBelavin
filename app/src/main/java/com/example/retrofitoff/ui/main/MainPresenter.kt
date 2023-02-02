package com.example.retrofitoff.ui.main

import Repository
import android.util.Log
import com.example.retrofitoff.R
import com.example.retrofitoff.model.RepoUser
import com.omega_r.libs.omegatypes.Text
import moxy.InjectViewState
import moxy.MvpPresenter
import java.net.UnknownHostException

@InjectViewState
class MainPresenter(private val mainRepository: Repository) : MvpPresenter<MainView>(),
    RepoAdapter.Listener {

    private val LogMainPresenter = Text.from(R.string.log_main_presenter)
    private var repoList = emptyList<RepoUser>()


    suspend fun getRepoList(userName: String): List<RepoUser> {

        try {

            viewState.unlockedClick(false)
            val response: List<RepoUser> = mainRepository.getListRepository(userName)

            repoList = response

            viewState.unlockedClick(true)
            return repoList

        }
        catch (e: UnknownHostException) {
            Log.d("${LogMainPresenter}_Unknown", "Exception: ${e}")
            val errorNoInternet = Text.from(R.string.error_no_internet_connection)
            viewState.showError(errorNoInternet)
        }
        catch (e: retrofit2.HttpException) {
            Log.d("${LogMainPresenter}_Unknown", "Exception: ${e}")
            Log.d("${LogMainPresenter}_Unknown", "Exception: ${e.code()}")

            val errorUserNotFound = Text.from(R.string.error_user_not_found)
            val errorRequestLimitHasEnded = Text.from(R.string.error_the_request_limit_has_ended)

            when(e.code()) {
                404 -> viewState.showError(errorUserNotFound)
                403 -> viewState.showError(errorRequestLimitHasEnded)
            }
        }

        finally {

        }

        return repoList

    }

    override fun onClickAdapter(list: RepoUser) {

    }
}
