package com.example.retrofitoff.api


import com.example.retrofitoff.mode2.RepositoriesUser
import com.example.retrofitoff.mode2.RepositoriesUserItem
import com.example.retrofitoff.mode2.StatisticsStars
import com.example.retrofitoff.util.Constants.Companion.BASE_URL
import com.google.gson.GsonBuilder
import com.squareup.moshi.Moshi
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path


interface APIInterface {
    @GET("/users/{user}/repos?per_page=100")
      suspend fun getRepoList(
        @Path("user") userName: String,

        ): Call<RepositoriesUserItem>


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
       @Path("repo") repoName: String,
   ): Response<StatisticsStars>
    companion object {

        fun sort():getRepoStars {
            val gson = GsonBuilder()
                .setDateFormat("yyyy-MM-dd")
                .create()

            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit.create(getRepoStars::class.java)
        }
    }
}
