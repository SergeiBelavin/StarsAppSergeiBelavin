package com.example.retrofitoff.repository

import com.example.retrofitoff.RetrofitInstance
import com.example.retrofitoff.mode2.RepositoriesUser
import com.example.retrofitoff.mode2.RepositoriesUserItem

import com.example.retrofitoff.mode2.StatisticsStars
import retrofit2.Call
import retrofit2.Response


class Repository {
    suspend fun getRepoList(userName: String): List<RepositoriesUserItem> {
        return RetrofitInstance.api.getRepoList(userName)

    }
    suspend fun getRepoStat(userName: String, repoName: String): Response<StatisticsStars> {
        return RetrofitInstance.api.getRepoStat(userName, repoName)

    }

}
