package com.example.retrofitoff.model

import com.example.retrofitoff.data.entity.RepoUser
import com.squareup.moshi.Json

data class RepoUserItem(
    @Json(name = "name")
    override val name: String,

    @Json(name = "owner")
    override val owner: Owner?,
): RepoUser