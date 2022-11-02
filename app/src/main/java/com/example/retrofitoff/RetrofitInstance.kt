package com.example.retrofitoff


import com.example.retrofitoff.api.getRepoStars
import com.example.retrofitoff.api.getRepoList

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    private val retrofit by lazy {

        val httploggingInterceptor = HttpLoggingInterceptor()
        httploggingInterceptor.level = HttpLoggingInterceptor.Level.HEADERS

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(httploggingInterceptor)
            .build()


        Retrofit.Builder()
            .baseUrl("https://api.github.com")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()


    }


    val api: getRepoList by lazy {
        retrofit.create(getRepoList::class.java)
    }
    val api2: getRepoStars by lazy {
        retrofit.create(getRepoStars::class.java)
    }
}
