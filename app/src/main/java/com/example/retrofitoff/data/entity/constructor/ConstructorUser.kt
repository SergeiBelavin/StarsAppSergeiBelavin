package com.example.retrofitoff.data.entity.constructor

import com.example.retrofitoff.model.User
//@Entity(tableName = "users")
data class ConstructorUser(
    //@PrimaryKey
    //@ColumnInfo(name = "id")
    override val id: Long,

    //@ColumnInfo(name = "name")
    override val name: String,

    //@ColumnInfo(name = "avatar_url")
    override val avatar: String?,
): User {
    constructor(user: User): this (
        id = user.id,
        name = user.name,
        avatar = user.avatar
            )
}