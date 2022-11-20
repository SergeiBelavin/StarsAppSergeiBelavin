package com.example.retrofitoff.data.api

import com.example.retrofitoff.data.entity.RepoUser
import com.example.retrofitoff.data.entity.StarGroup
import com.example.retrofitoff.data.repository.Repository
import com.example.retrofitoff.model.RepoUserItem
import com.example.retrofitoff.model.StarGroupItem
import java.io.Serializable

private const val TMDB_STARTING_PAGE_INDEX = 1

class PageSource(
    private val gitHubApi: GitHubApi,
)  {

}