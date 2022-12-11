package com.example.retrofitoff.ui.main.view

import com.example.retrofitoff.model.RepoUser

interface IMainView {
    fun onFiendRepoUser(name: String)
    fun getStarRepo(name: String, repo: String)
    fun onShowProgress()
    fun onHideProgress()
}