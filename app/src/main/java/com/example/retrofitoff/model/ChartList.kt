package com.example.retrofitoff.model

import com.omega_r.libs.omegatypes.Image
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.io.Serializable
import java.util.*

interface ChartList: Serializable {
    var starredAt: List<Int>
    var avatarUrl: List<Image?>
    var name: List<String?>
}