package com.example.bangwool.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "http://3.37.207.13:8080/"

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun createApiService(): RetrofitInterface {
        return retrofit.create(RetrofitInterface::class.java)
    }

    fun createLoginApiService(): RetrofitLoginInterface {
        return retrofit.create(RetrofitLoginInterface::class.java)
    }
}
