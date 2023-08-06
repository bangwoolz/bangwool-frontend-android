package com.example.bangwool.retrofit

import com.example.bangwool.BuildConfig
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val BASE_URL = BuildConfig.BASE_URL

object RetrofitUtil {

    private var loginInstance: RetrofitLoginInterface? = null
    private var accessTokenString: String? = null


    private fun getLoginOkHttpClient(): OkHttpClient {
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


    fun getLoginRetrofit(): RetrofitLoginInterface {
        if (loginInstance == null) {
            val retrofit = Retrofit.Builder() //객체를 생성해 줍니다.
                .baseUrl(BASE_URL) //통신할 서버 주소를 설정합니다.
                .addConverterFactory(GsonConverterFactory.create())
                .client(getLoginOkHttpClient())
                .build()
            loginInstance = retrofit.create(RetrofitLoginInterface::class.java)
        }
        return loginInstance!!
    }


    fun getRetrofit(): RetrofitInterface {
        val gson: Gson = GsonBuilder()
            .setLenient()
            .create()
        var retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(getOkHttpClient())
            .build()

        return retrofit.create(RetrofitInterface::class.java)
    }

    fun getOkHttpClient(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG) {
            interceptor.level = HttpLoggingInterceptor.Level.BODY
        } else {
            interceptor.level = HttpLoggingInterceptor.Level.NONE
        }

        return OkHttpClient.Builder().addInterceptor(interceptor).addInterceptor(
            AddedTokenRequest(
                accessTokenString!!
            )
        )
            .build()
    }

    fun setAccessToken(str: String) {
        accessTokenString = str
    }

    class AddedTokenRequest(val token: String) : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {

            val tokenRequest = chain.request().newBuilder()
                .addHeader("Authorization", "Bearer " + token)
                .addHeader("Content-Type", "application/json").build()

            return chain.proceed(tokenRequest)
        }
    }
}