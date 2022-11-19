package com.example.retrofitoff.data.entity

import com.example.retrofitoff.model.Owner
import java.io.Serializable

interface RepoUser: Serializable {
    val id: Long
    val name: String
    val allStars: Int?
    val owner: Owner?
    val user: User
}