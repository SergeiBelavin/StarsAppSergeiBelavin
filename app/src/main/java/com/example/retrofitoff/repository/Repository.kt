package com.example.retrofitoff.repository

import com.example.retrofitoff.RetrofitInstance
import com.example.retrofitoff.mode2.RepositoriesUserItemClass

import com.example.retrofitoff.mode2.StatiStarsUsersClass
import retrofit2.Call
import retrofit2.Response


class Repository {
    suspend fun getPost(userName: String): Call<List<RepositoriesUserItemClass>> {
        return RetrofitInstance.api.getRepo(userName)

    }
    suspend fun getRepoStat(userName: String, repoName: String): Response<StatiStarsUsersClass> {
        return RetrofitInstance.api2.getRepoStat(userName, repoName)

    }

}
