package com.example.retrofitoff.data.entity

import android.media.Image
import java.io.Serializable

interface User: Serializable {
    val id: Long
    val name: String
    val avatar: String?
}