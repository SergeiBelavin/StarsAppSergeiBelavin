package com.example.retrofitoff.model

import com.squareup.moshi.Json

data class RepoUserItem(
    @Json(name = "name")
    val name: String?,

    @Json(name = "owner")
    val owner: Owner?,
)