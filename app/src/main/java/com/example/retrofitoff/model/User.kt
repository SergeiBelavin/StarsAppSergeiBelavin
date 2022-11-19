package com.example.retrofitoff.model

import com.squareup.moshi.Json

data class User(
    @Json(name = "avatar_url")
    val avatarUrl: String,

    @Json(name = "id")
    val id: Int,

    @Json(name = "login")
    val login: String,
)