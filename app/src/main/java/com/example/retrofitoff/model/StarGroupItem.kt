package com.example.retrofitoff.model

import com.squareup.moshi.Json
import java.util.Date

data class StarGroupItem(
    @Json(name = "starred_at")
    val starredAt: Date,

    @Json(name = "user")
    val user: User,
)