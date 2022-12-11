package com.example.retrofitoff.data

import com.example.retrofitoff.data.repository.Repository
import com.example.retrofitoff.model.RepoUser

class RemoteMainProvider {
    private val repo = Repository()

    suspend fun getuserRepo(userName: String): List<RepoUser> {
        return repo.getListRepository(userName)
    }
}