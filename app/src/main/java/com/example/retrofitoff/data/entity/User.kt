package com.example.retrofitoff.data.entity

import java.io.Serializable

interface User: Serializable {
    val avatarUrl: String
    val id: Int
    val name: String
    val login: String
}