package com.example.bangwool.ui.mypage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.bangwool.R
import com.example.bangwool.databinding.ActivityEditPrivacyBinding
import com.example.bangwool.databinding.ActivityTimerBinding

class EditPrivacyActivity : AppCompatActivity() {
    lateinit var binding: ActivityEditPrivacyBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditPrivacyBinding.inflate(layoutInflater)
        binding.icBack.setOnClickListener{
            finish()
        }
        setContentView(binding.root)
    }
}