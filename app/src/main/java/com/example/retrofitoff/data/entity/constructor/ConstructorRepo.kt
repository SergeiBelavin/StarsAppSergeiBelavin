package com.example.retrofitoff.data.entity.constructor

import com.example.retrofitoff.data.entity.RepoUser

//@Entity(tableName = "repo")
data class ConstructorRepo(
    //@PrimaryKey
    override val id: Long,

    //@ColumnInfo(name = "name")
    override val name: String,

    //@ColumnInfo(name = "neededForChart")
    override var neededForChart: Int?,

    //@Embedded(prefix = "user_")
    override val user: ConstructorUser
): RepoUser {
    constructor(repo: RepoUser) : this(
        id = repo.id,
        name = repo.name,
        neededForChart = repo.neededForChart,
        user = ConstructorUser(repo.user)
    )
}
