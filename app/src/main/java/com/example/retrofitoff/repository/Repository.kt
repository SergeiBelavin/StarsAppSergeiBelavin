package com.example.retrofitoff.repository

import com.example.retrofitoff.RetrofitInstance
import com.example.retrofitoff.mode2.RepoStatistic
import com.example.retrofitoff.modelReposUser.PostRepos
import com.example.retrofitoff.modelStatStars.StatStars
import retrofit2.Response


class Repository {
    suspend fun getPost(userName: String): Response<PostRepos> {
        return RetrofitInstance.api.getPost(userName)

    }
    suspend fun getRepoStat(userName: String, repoName: String): Response<StatStars> {
        return RetrofitInstance.api2.getRepoStat(userName, repoName)

    }

}
