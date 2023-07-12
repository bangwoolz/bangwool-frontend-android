package com.example.bangwool

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.bangwool.databinding.ActivityPrivacypolicyBinding

class PrivacyPolicy : AppCompatActivity() {
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

    override fun onBackPressed() {
        setResult(RESULT_OK)
        super.onBackPressed()
    }
}
