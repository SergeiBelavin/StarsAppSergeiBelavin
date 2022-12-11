package com.example.retrofitoff.ui.main.presenter

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.example.retrofitoff.data.repository.Repository
import com.example.retrofitoff.model.RepoUser
import com.example.retrofitoff.ui.main.view.IMainView
import com.example.retrofitoff.ui.main.view.MainViewModel

open class MainPresenter(var iMainView: IMainView): IMainPresenter {
    private lateinit var viewModel: MainViewModel

    val myResponse: MutableLiveData<List<RepoUser>> = MutableLiveData()

    private val repo = Repository()

    override suspend fun onFiendRepoUser(name: String) {
        repo.getListRepository(name)

    }


    override fun getStarRepo(name: String, repo: String) {

    }

    override fun onShowProgress() {
        iMainView.onShowProgress()
    }

    override fun onHideProgress() {
        iMainView.onHideProgress()
    }
}