package com.example.retrofitoff.data.entity

import com.example.retrofitoff.model.RepoUser
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RepoUserItem(
    @Json(name = "id")
    override val id: Long,

    @Json(name = "name")
    override val name: String,

    @Json(name = "stargazers_count")
    override val neededForChart: Int?,

    @Json(name = "owner")
    override val user: UserItem,

    ): RepoUser