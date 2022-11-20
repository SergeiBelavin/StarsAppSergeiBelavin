package com.example.retrofitoff.model

import com.example.retrofitoff.data.entity.StarGroup
import com.example.retrofitoff.data.entity.User
import com.squareup.moshi.Json
import java.util.Date

data class StarGroupItem(
    @Json(name = "starred_at")
    override val starredAt: Date,

    @Json(name = "user")
    override val user: List<User>,
): StarGroup