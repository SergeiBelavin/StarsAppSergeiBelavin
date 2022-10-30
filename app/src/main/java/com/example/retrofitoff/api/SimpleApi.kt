package com.example.retrofitoff.api
import com.example.retrofitoff.mode2.RepoStatistic
import com.example.retrofitoff.modelReposUser.PostRepos
import com.example.retrofitoff.modelStatStars.StatStars
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path


interface SimpleApi {
    @GET("/users/{user}/repos?per_page=100")
    suspend fun getPost(
    @Path("user") userName: String
    ): Response<PostRepos>

    //https://api.github.com/repos/digoal/blog/stargazers?per_page=100
    //Кто поставил звезды

    //https://api.github.com/repos/digoal/blog/subscribers?per_page=100
    //Сабы


}
interface RepoApi {
    @GET("/repos/{user}/{repo}/stats/commit_activity")
    suspend fun getRepoStat(
        @Path("user") userName: String,
        @Path("repo") repoName: String
    ): Response<StatStars>
}
