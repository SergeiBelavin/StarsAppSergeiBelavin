package com.example.retrofitoff.data.entity.constructor

import android.media.Image
import com.example.retrofitoff.data.entity.User

data class ConstructorUser(
    override val id: Long,
    override val name: String,
    override val avatar: String?,
): User {
    constructor(user: User): this (
        id = user.id,
        name = user.name,
        avatar = user.avatar
            )
}