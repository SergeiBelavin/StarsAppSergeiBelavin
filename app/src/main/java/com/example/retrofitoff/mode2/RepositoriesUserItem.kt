package com.example.retrofitoff.mode2

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
 data class RepositoriesUserItem(
    val name: String?,
    val owner: Owner?,
)