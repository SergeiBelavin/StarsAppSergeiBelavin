package com.example.retrofitoff.data.entity


import java.io.Serializable

interface RepoUser: Serializable {
    val id: Long
    val name: String
    val allStarsCount: Int?
    val user: User
}