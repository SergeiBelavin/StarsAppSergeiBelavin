package com.example.retrofitoff.model

import java.io.Serializable
import java.util.Date

interface StarGroup: Serializable {
    val starredAt: Date
    val user: User
    val uniqueDate: Int?
    //var barChartList: Map<Int?, List<StarGroup>>?
}