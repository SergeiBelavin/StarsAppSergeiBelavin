package com.example.retrofitoff.data.entity.constructor

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.retrofitoff.data.entity.RepoUser
import com.example.retrofitoff.data.entity.StarGroup
import java.util.*
@Entity(tableName = "stars")
data class ConstructorStar (
    @PrimaryKey
    @ColumnInfo(name = "starred_at")
    override val starredAt: Date,


    @ColumnInfo(name = "user")
    override val user: ConstructorUser,

    @ColumnInfo(name = "uniqueDate")
    val uniqueDate: Int,

    @ColumnInfo(name = "repo")
    val repo: ConstructorRepo


    ): StarGroup {
    constructor(star: StarGroup, repo: RepoUser) : this(
        starredAt = star.starredAt,
        user = ConstructorUser(star.user),
        uniqueDate = 0,
        repo = ConstructorRepo(repo)
    )
    }