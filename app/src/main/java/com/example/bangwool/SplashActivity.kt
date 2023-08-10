package com.example.bangwool

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import com.example.bangwool.retrofit.AuthLoginRequest
import com.example.bangwool.retrofit.RetrofitUtil
import com.example.bangwool.retrofit.TokenResponse
import com.example.bangwool.retrofit.getPassword
import com.example.bangwool.retrofit.getUserId
import com.example.bangwool.retrofit.removePassword
import com.example.bangwool.retrofit.removeUserId
import com.example.bangwool.retrofit.saveAccessToken
import com.example.bangwool.ui.login.LoginActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val handler = Handler();
        Handler(Looper.getMainLooper()).postDelayed({
//            val i = Intent(this, LoginActivity::class.java)
            val userId = getUserId(this)
            val password = getPassword(this)
            if(!(userId.equals("0"))&&!(password.equals("0"))){
                val authLoginRequest = AuthLoginRequest(userId, password)
                RetrofitUtil.getLoginRetrofit().AuthLogin(authLoginRequest).enqueue(object :
                    Callback<TokenResponse> {
                    override fun onResponse(
                        call: Call<TokenResponse>,
                        response: Response<TokenResponse>
                    ) {
                        if (response.isSuccessful) {
                            val token = response.body()!!.token
                            saveAccessToken(this@SplashActivity, token)
                            RetrofitUtil.setAccessToken(token)
                            val i = Intent(this@SplashActivity, MainActivity::class.java)
                            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)//이미 있는거 수정
                            startActivity(i)
                            Toast.makeText(this@SplashActivity, "로그인 성공", Toast.LENGTH_SHORT).show()
                            finish()
                            if (LoginActivity.activity != null)
                                LoginActivity.activity!!.finish()
                        } else {
                            removeUserId(this@SplashActivity)
                            removePassword(this@SplashActivity)
                            val i = Intent(this@SplashActivity, LoginActivity::class.java)
                            startActivity(i)
                            finish()
                        }
                    }

                    override fun onFailure(call: Call<TokenResponse>, t: Throwable) {

                    }

                })
            } else {
                val i = Intent(this, LoginActivity::class.java)
                startActivity(i)
                finish()
            }
        }, 2000)
    }
}