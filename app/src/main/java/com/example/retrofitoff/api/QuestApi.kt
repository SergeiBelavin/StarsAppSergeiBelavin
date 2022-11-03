package com.example.retrofitoff.api
import com.example.retrofitoff.mode2.RepositoriesUserItemClass


import com.example.retrofitoff.mode2.StatiStarsUsersClass
import retrofit2.Call

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path


interface getRepoList {
    @GET("/users/{user}/repos?per_page=100")
     fun getRepo(@Path("user") userName: String): Call<List<RepositoriesUserItemClass?>?>

    //https://api.github.com/repos/digoal/blog/stargazers?per_page=100
    //Кто поставил звезды

    //https://api.github.com/repos/digoal/blog/subscribers?per_page=100
    //Сабы
    //https://api.github.com/repos/robb-oat/server/commits?per_page=100
///repos/{owner}/{repo}/traffic/clones
    ///repos/robb-oat/stats/traffic/clones
}
interface getRepoStars {
   // /repos/repos/robb-oat/stats/commit_activity
   // https://api.github.com/repos/digoal/blog/stargazers
   @Headers("Accept: application/vnd.github.star+json")

   @GET("/repos/{user}/{repo}/stargazers")
    //("application/vnd.github.preview")
    suspend fun getRepoStat(
        @Path("user") userName: String,
        @Path("repo") repoName: String
    ): Response<StatiStarsUsersClass>
}
