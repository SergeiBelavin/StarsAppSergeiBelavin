package com.example.retrofitoff.data.repository

import com.example.retrofitoff.mode.UserRepositoriesItem

import com.example.retrofitoff.mode.StatisticsStarsItem


class Repository() {

    suspend fun listRepository(userName: String): List<UserRepositoriesItem> {
        return RetrofitInstance.api.getRepoList(userName)
    }

    suspend fun statisticsRepository(userName: String, repoName: String): List<StatisticsStarsItem> {
        return RetrofitInstance.api.getRepoStat(userName, repoName)
    }
// Надо чарт активити привести в изначальное состояние где листы ловили статистику, а не лонг. Затем передавать данные в репозиторий и обрабатывать их
}
