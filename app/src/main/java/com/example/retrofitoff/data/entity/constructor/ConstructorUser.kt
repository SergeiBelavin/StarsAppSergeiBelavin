package com.example.retrofitoff.data.entity.constructor

import android.media.Image
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.retrofitoff.data.entity.User
@Entity(tableName = "Users")
data class ConstructorUser(
    @PrimaryKey(autoGenerate = true)
    override val id: Long,

    @ColumnInfo(name = "name")
    override val name: String,

    @ColumnInfo(name = "avatar_url")
    override val avatar: String?,
): User {
    constructor(user: User): this (
        id = user.id,
        name = user.name,
        avatar = user.avatar
            )
}