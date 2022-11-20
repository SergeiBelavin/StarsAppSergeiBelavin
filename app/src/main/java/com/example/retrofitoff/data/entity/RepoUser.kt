package com.example.retrofitoff.data.entity

import com.example.retrofitoff.model.Owner
import java.io.Serializable

interface RepoUser: Serializable {
    val name: String
    val owner: Owner?
}