package com.example.bangwool.ui.login

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.bangwool.databinding.ActivityPrivacypolicyBinding

class PrivacyPolicyActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPrivacypolicyBinding
    private var checkboxState = false
    private var isScrolledEnd = false
//    val webView = binding.webView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPrivacypolicyBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.buttonClose.setOnClickListener {
            finish()
        }


        binding.webView.loadUrl("file:///android_asset/privacy_policy.html")
    }
}
