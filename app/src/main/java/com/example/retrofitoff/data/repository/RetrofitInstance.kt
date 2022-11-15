package com.example.retrofitoff.data.repository



import com.example.retrofitoff.data.api.GitHubApi
import com.example.retrofitoff.util.Constants.Companion.BASE_URL
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object RetrofitInstance {

    private val retrofit by lazy {

        val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

        val httploggingInterceptor = HttpLoggingInterceptor()
        httploggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(httploggingInterceptor)
            .build()

        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(okHttpClient)
            .build()
    }
    val api: GitHubApi by lazy {
        retrofit.create(GitHubApi::class.java)
    }

}
