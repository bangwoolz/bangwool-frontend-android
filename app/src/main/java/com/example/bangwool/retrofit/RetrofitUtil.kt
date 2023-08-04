package com.example.bangwool.retrofit

import com.example.bangwool.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val BASE_URL = BuildConfig.BASE_URL

object RetrofitUtil {

    private var instance: RetrofitInterface? = null


    private fun getHttpClient(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()

        if (BuildConfig.DEBUG) {
            interceptor.level = HttpLoggingInterceptor.Level.BODY
        } else {
            interceptor.level = HttpLoggingInterceptor.Level.NONE
        }

        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()
    }


    fun getRetrofit(): RetrofitInterface {
        if (instance == null) {
            val retrofit = Retrofit.Builder() //객체를 생성해 줍니다.
                .baseUrl(BASE_URL) //통신할 서버 주소를 설정합니다.
                .addConverterFactory(GsonConverterFactory.create())
                .client(getHttpClient())
                .build()
            instance = retrofit.create(RetrofitInterface::class.java)
        }
        return instance!!
    }
}