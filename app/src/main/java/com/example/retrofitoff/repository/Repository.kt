package com.example.retrofitoff.repository

import com.example.retrofitoff.RetrofitInstance
import com.example.retrofitoff.mode2.ReposUser
import com.example.retrofitoff.mode2.StatiStarsUsers
import retrofit2.Response


class Repository {
    suspend fun getPost(userName: String): Response<ReposUser> {
        return RetrofitInstance.api.getRepo(userName)

    }
    suspend fun getRepoStat(userName: String, repoName: String): Response<StatiStarsUsers> {
        return RetrofitInstance.api2.getRepoStat(userName, repoName)

    }

}
