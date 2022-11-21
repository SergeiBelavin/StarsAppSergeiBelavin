package com.example.retrofitoff.model

import com.example.retrofitoff.data.entity.StarGroup
import com.example.retrofitoff.data.entity.User
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.util.Date

@JsonClass(generateAdapter = true)
data class StarGroupItem(
    @Json(name = "starred_at")
    override val starredAt: Date,

    @Json(name = "user")
    override val user: UserItem
): StarGroup