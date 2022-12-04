package com.example.retrofitoff.data.entity


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Users")
data class EntityUser(
    @PrimaryKey
    override val id: Long,

    override val name: String,
    @ColumnInfo(name = "starred_at")
    override val avatar: String?

    ): User {
        constructor(user: User): this(
            id = user.id,
            name = user.name,
            avatar = user.avatar
        )

}