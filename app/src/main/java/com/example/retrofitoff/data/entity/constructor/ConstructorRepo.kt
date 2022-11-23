package com.example.retrofitoff.data.entity.constructor

import com.example.retrofitoff.data.entity.RepoUser
import com.example.retrofitoff.data.repository.Repository

data class ConstructorRepo(
    override val id: Long,

    override val name: String,

    override val allStarsCount: Int?,

    override val user: ConstructorUser
): RepoUser {
    constructor(repo: RepoUser) : this(
        id = repo.id,
        name = repo.name,
        allStarsCount = repo.allStarsCount,
        user = ConstructorUser(repo.user)
    )
}
