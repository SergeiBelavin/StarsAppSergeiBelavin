package com.example.retrofitoff.data.api.providers

import com.example.retrofitoff.data.api.GitHubApi
import com.example.retrofitoff.model.StarGroupItem

class StarSearch(private val gitHubApi: GitHubApi) {

    suspend fun getStarList(
        userName: String,
        repoName: String,
        page: Int,
    ): List<StarGroupItem> {
        return gitHubApi.getRepoStars(
            userName,
            repoName,
            page
        )
    }
}