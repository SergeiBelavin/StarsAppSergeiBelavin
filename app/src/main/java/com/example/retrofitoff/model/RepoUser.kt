package com.example.retrofitoff.model


import com.example.retrofitoff.model.User
import java.io.Serializable

interface RepoUser: Serializable {
    val id: Long
    val name: String
    val neededForChart: Int?
    val user: User
}