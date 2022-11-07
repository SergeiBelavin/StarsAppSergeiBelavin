package com.example.retrofitoff.api


import com.example.retrofitoff.mode2.RepositoriesUserItem
import com.example.retrofitoff.mode2.StatisticStars
import com.example.retrofitoff.mode2.StatisticStarsItem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path


interface GitHubInterface {
    @GET("/users/{user}/repos?per_page=100")
      suspend fun getRepoList(
        @Path("user") userName: String,

        ): List<RepositoriesUserItem>


    @Headers("Accept: application/vnd.github.star+json")
    @GET("/repos/{user}/{repo}/stargazers")
    suspend fun getRepoStat(
        @Path("user") userName: String,
        @Path("repo") repoName: String,
    ): Response<StatisticStars>

}

