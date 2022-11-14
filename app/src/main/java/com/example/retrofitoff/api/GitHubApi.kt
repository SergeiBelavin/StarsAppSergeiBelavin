package com.example.retrofitoff.api


import com.example.retrofitoff.mode2.RepositoriesUserItem
import com.example.retrofitoff.mode2.StatisticStarsItem
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path


interface GitHubApi {
      @GET("/users/{user}/repos?per_page=100")
      suspend fun getRepoList(
        @Path("user") userName: String,

        ): List<RepositoriesUserItem>


    @Headers("Accept: application/vnd.github.star+json")
    @GET("/repos/{user}/{repo}/stargazers")
    suspend fun getRepoStat(
        @Path("user") userName: String,
        @Path("repo") repoName: String,
    ): List<StatisticStarsItem>

}


