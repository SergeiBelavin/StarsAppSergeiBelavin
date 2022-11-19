package com.example.retrofitoff.data.entity

import com.example.retrofitoff.model.StarGroupItem
import java.io.Serializable
import java.util.Date

interface StarGroup {
    val starredAt: Date
    val user: List<User>
}