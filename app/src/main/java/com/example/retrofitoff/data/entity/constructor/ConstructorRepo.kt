package com.example.retrofitoff.data.entity.constructor

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.retrofitoff.data.entity.RepoUser

@Entity(tableName = "Repo")
data class ConstructorRepo(
    @PrimaryKey(autoGenerate = true)
    override val id: Long,

    @ColumnInfo(name = "name")
    override val name: String,

    @ColumnInfo(name = "neededForChart")
    override var neededForChart: Int?,

    @Embedded(prefix = "user_")
    override val user: ConstructorUser
): RepoUser {
    constructor(repo: RepoUser) : this(
        id = repo.id,
        name = repo.name,
        neededForChart = repo.neededForChart,
        user = ConstructorUser(repo.user)
    )
}
