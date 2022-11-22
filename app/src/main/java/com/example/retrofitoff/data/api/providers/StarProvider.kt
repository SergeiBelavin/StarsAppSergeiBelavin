package com.example.retrofitoff.data.api.providers

import com.example.retrofitoff.data.entity.RepoUser
import com.example.retrofitoff.data.entity.StarGroup

interface StarProvider {
    suspend fun getRepoList(userName: String, repoUserEntity: RepoUser, page: Int): List<StarGroup>?
}