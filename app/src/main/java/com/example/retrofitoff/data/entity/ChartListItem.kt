package com.example.retrofitoff.data.entity

import com.example.retrofitoff.model.ChartList
import com.omega_r.libs.omegatypes.Image
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class ChartListItem(
    @Json(name = "starredAt")
    override var starredAt: List<Int>,

    @Json(name = "avatarUrl")
    override var avatarUrl: List<Image?>,

    @Json(name = "nameUser")
    override var name: List<String?>
): ChartList

{
}