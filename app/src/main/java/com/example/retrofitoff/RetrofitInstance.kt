package com.example.retrofitoff


import com.example.retrofitoff.api.getRepoStars
import com.example.retrofitoff.api.APIInterface
import com.example.retrofitoff.util.Constants.Companion.BASE_URL

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

object RetrofitInstance {

    private val retrofit by lazy {

        val httploggingInterceptor = HttpLoggingInterceptor()
        httploggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(httploggingInterceptor)
            .build()


        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()


    }


    val api: APIInterface by lazy {
        retrofit.create(APIInterface::class.java)
    }
    val api2: getRepoStars by lazy {
        retrofit.create(getRepoStars::class.java)
    }
}
