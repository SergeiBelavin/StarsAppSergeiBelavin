package com.example.retrofitoff.data.entity.constructor

import com.example.retrofitoff.data.entity.User

data class ConstructorUser(
    override val id: Long,
    override val name: String,
): User {
    constructor(user: User): this (
        id = user.id,
        name = user.name,
            )
}