package com.example.retrofitoff.ui.main.presenter

import android.content.Context

interface IMainPresenter {
    suspend fun onFiendRepoUser(name: String)
    fun getStarRepo(name: String, repo: String)
    fun onShowProgress()
    fun onHideProgress()

}