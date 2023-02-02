package com.example.retrofitoff.model

import java.util.*

interface StarGroupForChart: StarGroup {
    override val starredAt: Date
    override val user: User
}