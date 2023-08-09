package com.example.bangwool

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.example.bangwool.ui.login.LoginActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val handler = Handler();
        Handler(Looper.getMainLooper()).postDelayed({
//            val i = Intent(this, LoginActivity::class.java)
            val i = Intent(this, LoginActivity::class.java)
            startActivity(i)
            finish()
        }, 2000)
    }
}