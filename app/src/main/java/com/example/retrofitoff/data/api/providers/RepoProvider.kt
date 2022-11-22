package com.example.retrofitoff.data.api.providers

import com.example.retrofitoff.data.entity.RepoUser
import com.example.retrofitoff.data.entity.StarGroup

class RepoProvider: StarProvider {
    private val getRepoList = SearchProvider().providerSearchStars()
    override suspend fun getRepoList(
        userName: String,
        repoUserEntity: RepoUser,
        page: Int,
    ): List<StarGroup> {
        return getRepoList.getStarList(userName, repoUserEntity.name, page)
    }
}