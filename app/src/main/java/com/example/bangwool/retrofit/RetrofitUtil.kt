package com.example.bangwool.retrofit

import android.util.Log
import com.example.bangwool.BuildConfig
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

val BASE_URL = BuildConfig.BASE_URL

object RetrofitUtil {

    private var loginInstance: RetrofitLoginInterface? = null
    private var instance: RetrofitInterface? = null
    private var kakaoInstance : KakaoRetrofitInterface? = null
    private var kakaoInstance2 : KakaoRetrofitInterface2? = null
    var accessTokenString = ""


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
            var gson= GsonBuilder().setLenient().create()
            val retrofit = Retrofit.Builder() //객체를 생성해 줍니다.
                .baseUrl(BASE_URL) //통신할 서버 주소를 설정합니다.
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(getLoginOkHttpClient())
                .build()
            loginInstance = retrofit.create(RetrofitLoginInterface::class.java)
        }
        return loginInstance!!
    }


    private fun getKakaoOkhttpClient(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()

        if (BuildConfig.DEBUG) {
            interceptor.level = HttpLoggingInterceptor.Level.BODY
        } else {
            interceptor.level = HttpLoggingInterceptor.Level.NONE
        }

        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .followRedirects(true)
            .followSslRedirects(true)
            .build()
    }

    fun getKakaoRetrofit(): KakaoRetrofitInterface {
        if (kakaoInstance == null) {
            var gson= GsonBuilder().setLenient().create()
            val retrofit = Retrofit.Builder() //객체를 생성해 줍니다.
                .baseUrl(BASE_URL) //통신할 서버 주소를 설정합니다.
                .addConverterFactory(ScalarsConverterFactory.create())
                .client(getKakaoOkhttpClient())
                .build()
            kakaoInstance = retrofit.create(KakaoRetrofitInterface::class.java)
        }
        return kakaoInstance!!
    }

    private fun getKakaoOkhttpClient2(): OkHttpClient {
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

    fun getKakaoRetrofit2(): KakaoRetrofitInterface2 {
        if (kakaoInstance2 == null) {
            var gson= GsonBuilder().setLenient().create()
            val retrofit = Retrofit.Builder() //객체를 생성해 줍니다.
                .baseUrl(BASE_URL) //통신할 서버 주소를 설정합니다.
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(getKakaoOkhttpClient())
                .build()
            kakaoInstance2 = retrofit.create(KakaoRetrofitInterface2::class.java)
        }
        return kakaoInstance2!!
    }


    fun getOkHttpClient(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG) {
            interceptor.level = HttpLoggingInterceptor.Level.BODY
        } else {
            interceptor.level = HttpLoggingInterceptor.Level.NONE
        }
        Log.d("qwerty123", accessTokenString)

        return OkHttpClient.Builder().addInterceptor(interceptor).addInterceptor(
            AddedTokenRequest(
                accessTokenString!!
            )
        )
            .build()
    }

    fun getRetrofit(): RetrofitInterface {
        if (instance == null) {
            var gson= GsonBuilder().setLenient().create()
            val retrofit = Retrofit.Builder() //객체를 생성해 줍니다.
                .baseUrl(BASE_URL) //통신할 서버 주소를 설정합니다.
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(getOkHttpClient())
                .build()
            instance = retrofit.create(RetrofitInterface::class.java)
        }
        return instance!!
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