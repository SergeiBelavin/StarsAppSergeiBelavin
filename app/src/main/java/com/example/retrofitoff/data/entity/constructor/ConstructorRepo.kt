package com.example.retrofitoff.data.entity.constructor

import com.example.retrofitoff.data.entity.RepoUser

data class ConstructorRepo(
    override val id: Long,

    override val name: String,

    override val neededForChart: Int?,

    override val user: ConstructorUser
): RepoUser {
    constructor(repo: RepoUser) : this(
        id = repo.id,
        name = repo.name,
        neededForChart = repo.neededForChart,
        user = ConstructorUser(repo.user)
    )
}
