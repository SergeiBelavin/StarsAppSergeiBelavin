package com.example.retrofitoff.data.entity.constructor

import com.example.retrofitoff.data.entity.RepoUser
import com.example.retrofitoff.data.entity.StarGroup
import com.example.retrofitoff.data.repository.Repository
import java.util.*

data class ConstructorStar (
    override val starredAt: Date,

    override val user: ConstructorUser,

    val repo: ConstructorRepo
    ): StarGroup {
    constructor(star: StarGroup, repo: RepoUser) : this(
        starredAt = star.starredAt,
        user = ConstructorUser(star.user),
        repo = ConstructorRepo(repo)
    )
    }