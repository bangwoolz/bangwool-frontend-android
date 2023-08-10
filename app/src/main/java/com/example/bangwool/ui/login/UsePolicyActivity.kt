package com.example.bangwool.ui.login

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.bangwool.databinding.ActivityTextviewallagreementsBinding

class UsePolicyActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTextviewallagreementsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTextviewallagreementsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonClose.setOnClickListener {
            finish()
        }

        binding.webView.loadUrl("file:///android_asset/use_policy.html")
    }
}
