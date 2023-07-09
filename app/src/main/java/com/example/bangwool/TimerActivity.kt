package com.example.bangwool

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.bangwool.databinding.ActivityTimerBinding

class TimerActivity : AppCompatActivity() {
    lateinit var binding: ActivityTimerBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTimerBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_timer)
    }
}