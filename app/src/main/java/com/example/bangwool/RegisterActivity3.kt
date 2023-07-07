package com.example.bangwool

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.bangwool.databinding.ActivityRegister3Binding

class RegisterActivity3 : AppCompatActivity() {
    private lateinit var binding: ActivityRegister3Binding
    private var checkboxState = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegister3Binding.inflate(layoutInflater)
        setContentView(binding.root)

        checkboxState = intent.getBooleanExtra("checkboxState", false)

        binding.buttonClose.setOnClickListener {
            finish()
        }
    }

    override fun onBackPressed() {
        val intent = intent
        setResult(RESULT_OK, intent)
        super.onBackPressed()
    }
}



