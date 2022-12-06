package com.example.retrofitoff.data.entity.constructor

import com.example.retrofitoff.data.entity.RepoUser
import com.example.retrofitoff.data.entity.StarGroup
import java.util.*

data class ConstructorStar (

    override val starredAt: Date,

    //@Embedded(prefix = "user_")
    override val user: ConstructorUser,

    //@ColumnInfo(name = "uniqueDate")
    override val uniqueDate: Int?,

    //@Embedded(prefix = "user_")



    ): StarGroup {
    constructor(star: StarGroup, repo: RepoUser) : this(
        starredAt = star.starredAt,
        user = ConstructorUser(star.user),
        uniqueDate = 0,
    )
    }