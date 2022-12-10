package com.example.retrofitoff.data.entity

import com.example.retrofitoff.model.StarGroup
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.util.Date

@JsonClass(generateAdapter = true)
data class StarGroupItem(
    @Json(name = "starred_at")
    override val starredAt: Date,

    @Json(name = "user")
    override val user: UserItem,

    override val uniqueDate: Int?,

    ): StarGroup