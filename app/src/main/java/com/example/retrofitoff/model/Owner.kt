package com.example.retrofitoff.model

import com.squareup.moshi.Json

data class Owner(
    @Json(name = "login")
    val login: String,
)