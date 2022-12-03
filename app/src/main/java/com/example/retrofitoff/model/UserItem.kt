package com.example.retrofitoff.model

import android.media.Image
import com.example.retrofitoff.data.entity.User
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