package com.example.bangwool.ui.mypage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
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
        val nickname = intent.getStringExtra("nickname")
        val profileImage = intent.getStringExtra("profileImage")
        val email = intent.getStringExtra("email")
        binding.tvNickname.text = nickname
        binding.tvEmail.text = email

        binding.btnEditPasswordBefore.setOnClickListener{
            binding.btnEditPasswordBefore.visibility = View.GONE
            binding.clPassword.visibility = View.VISIBLE
            binding.clCheckPassword.visibility = View.VISIBLE
            binding.btnEditPasswordAfter.visibility = View.VISIBLE
            binding.svMyInfo.isScrollbarFadingEnabled = false
        }

        setContentView(binding.root)
    }
}