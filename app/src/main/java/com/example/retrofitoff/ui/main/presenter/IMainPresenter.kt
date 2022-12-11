package com.example.retrofitoff.ui.main.presenter

interface IMainPresenter {
    fun onFiendRepoUser(name: String)
    fun getStarRepo(name: String, repo: String)
    fun onShowProgress()
    fun onHideProgress()
}