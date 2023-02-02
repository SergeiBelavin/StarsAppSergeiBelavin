package com.example.retrofitoff.model

import com.omega_r.libs.omegatypes.Image
import java.io.Serializable

interface User: Serializable {
    val id: Long
    val name: String
    val avatar: Image?
}