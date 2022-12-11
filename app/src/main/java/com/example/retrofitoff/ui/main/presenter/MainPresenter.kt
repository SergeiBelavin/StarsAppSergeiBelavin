package com.example.retrofitoff.ui.main.presenter

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.retrofitoff.data.RemoteMainProvider
import com.example.retrofitoff.data.repository.Repository
import com.example.retrofitoff.model.RepoUser
import com.example.retrofitoff.ui.main.presenter.IMainPresenter
import com.example.retrofitoff.ui.main.view.IMainView
import com.example.retrofitoff.ui.main.view.MainViewModel
import kotlinx.coroutines.launch
import java.io.IOException

open class MainPresenter(var iMainView: IMainView): IMainPresenter {
    private lateinit var viewModel: MainViewModel
    val myResponse: MutableLiveData<List<RepoUser>> = MutableLiveData()
    private val remoteMain = RemoteMainProvider()

    override fun onFiendRepoUser(name: String) {

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