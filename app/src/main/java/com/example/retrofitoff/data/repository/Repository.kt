package com.example.retrofitoff.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.retrofitoff.mode.RepositoriesUserItem

import com.example.retrofitoff.mode.StatisticStarsItem
import java.util.*
import kotlin.collections.ArrayList


class Repository() {

    suspend fun getRepoList(userName: String): List<RepositoriesUserItem> {
        return RetrofitInstance.api.getRepoList(userName)
    }

    suspend fun getRepoStat(userName: String, repoName: String): List<StatisticStarsItem> {
        return RetrofitInstance.api.getRepoStat(userName, repoName)
    }
// Надо чарт активити привести в изначальное состояние где листы ловили статистику, а не лонг. Затем передавать данные в репозиторий и обрабатывать их
}
