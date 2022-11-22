package com.example.retrofitoff.data.api


import com.example.retrofitoff.model.RepoUserItem
import com.example.retrofitoff.model.StarGroupItem
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query


interface GitHubApi {
    @GET("/users/{user}/repos?per_page=100")
    suspend fun getRepoList(
        @Path("user") userName: String,
        @Query("page") page: Int,
        ): List<RepoUserItem>


    @Headers("Accept: application/vnd.github.star+json")
    @GET("/repos/{user}/{repo}/stargazers?per_page=100")
    suspend fun getRepoStars(
        @Path("user") userName: String,
        @Path("repo") repoName: String,
        @Query("page") page: Int,
    ): List<StarGroupItem>
}


