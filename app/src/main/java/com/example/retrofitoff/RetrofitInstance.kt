package com.example.retrofitoff


import com.example.retrofitoff.api.RepoApi
import com.example.retrofitoff.api.SimpleApi

import com.example.retrofitoff.util.Constants.Companion.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object RetrofitInstance {

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.github.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    val api: SimpleApi by lazy {
        retrofit.create(SimpleApi::class.java)
    }
    val api2: RepoApi by lazy {
        retrofit.create(RepoApi::class.java)
    }
}