package com.example.retrofitoff.model

import android.media.Image
import java.io.Serializable

interface User: Serializable {
    val id: Long
    val name: String
    val avatar: String?
}