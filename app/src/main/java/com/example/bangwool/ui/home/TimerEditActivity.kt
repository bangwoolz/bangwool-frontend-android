package com.example.bangwool.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.bangwool.R
import com.example.bangwool.databinding.ActivityTimerEditBinding

class TimerEditActivity : AppCompatActivity() {
    lateinit var binding : ActivityTimerEditBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTimerEditBinding.inflate(layoutInflater)
        binding.icTimerEditBack.setOnClickListener {
            finish()
        }
        setContentView(binding.root)
    }
}