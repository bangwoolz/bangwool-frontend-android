package com.example.bangwool.ui.login

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.bangwool.databinding.ActivityPrivacypolicyBinding

class PrivacyPolicyActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPrivacypolicyBinding
    private var checkboxState = false
    private var isScrolledEnd = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPrivacypolicyBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.buttonClose.setOnClickListener {
            finish()
        }

    }
}
