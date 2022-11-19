package com.example.retrofitoff.data.repository

import android.util.Log

object RequestLimit {
    private var limit = 0

    fun  hasRequest(): Boolean {
        return if (limit > 0) {
            limit--
            true
        } else {
            false
        }
    }
}