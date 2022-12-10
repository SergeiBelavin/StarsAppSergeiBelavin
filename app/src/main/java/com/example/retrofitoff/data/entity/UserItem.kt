package com.example.retrofitoff.data.entity

import com.example.retrofitoff.model.User
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserItem(
    @Json(name = "id")
    override val id: Long,

    @Json(name = "login")
    override val name: String,

    @Json(name = "avatar_url")
    override val avatar: String?
): User