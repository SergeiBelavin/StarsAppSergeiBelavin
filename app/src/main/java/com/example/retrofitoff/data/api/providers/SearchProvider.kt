package com.example.retrofitoff.data.api.providers

import com.example.retrofitoff.data.api.GitHubApi

class SearchProvider {
    lateinit var gitHubApi: GitHubApi

    fun providerSearchStars(): StarSearch {
        return StarSearch(gitHubApi)
    }

}