package com.example.retrofitoff.data.entity


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Repo")
data class EntityRepo(
    @PrimaryKey
    override val id: Long,
    override val name: String,
    override val neededForChart: Int?,
  //  @ColumnInfo(name = "user")
    override val user: EntityUser
): RepoUser {
    constructor(repo: RepoUser): this(
        id = repo.id,
        name = repo.name,
        neededForChart = repo.neededForChart,
        user = EntityUser(repo.user)
    )
}