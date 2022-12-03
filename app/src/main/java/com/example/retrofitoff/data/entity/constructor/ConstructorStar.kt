package com.example.retrofitoff.data.entity.constructor

import com.example.retrofitoff.data.entity.RepoUser
import com.example.retrofitoff.data.entity.StarGroup
import java.util.*

data class ConstructorStar (

    override val starredAt: Date,

    override val user: ConstructorUser,

    val uniqueDate: Int?,

    val repo: ConstructorRepo


    ): StarGroup {
    constructor(star: StarGroup, repo: RepoUser) : this(
        starredAt = star.starredAt,
        user = ConstructorUser(star.user),
        uniqueDate = 0,
        repo = ConstructorRepo(repo)
    )
    }