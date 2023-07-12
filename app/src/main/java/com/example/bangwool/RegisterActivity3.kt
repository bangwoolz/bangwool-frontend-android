package com.example.bangwool

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.bangwool.databinding.ActivityRegister3Binding

class RegisterActivity3 : AppCompatActivity() {
    private lateinit var binding: ActivityRegister3Binding
    private var checkboxState = false
    private var isScrolledEnd = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegister3Binding.inflate(layoutInflater)
        setContentView(binding.root)

        checkboxState = intent.getBooleanExtra("checkboxState", false)

        binding.buttonClose.setOnClickListener {
            finish()
        }

        val scrollView = binding.scrollable
        scrollView.viewTreeObserver.addOnScrollChangedListener {
            val scrollY = scrollView.scrollY
            val scrollViewHeight = scrollView.height
            val contentViewHeight = scrollView.getChildAt(0).height

            isScrolledEnd = scrollY + scrollViewHeight >= contentViewHeight
            updateButtonState()
        }

        binding.buttonContinue.setOnClickListener {
            finish()
        }
    }

    private fun updateButtonState() {
        if (isScrolledEnd) {
            val enabledColor = ContextCompat.getColorStateList(this, R.color.enabledColor)
            binding.buttonContinue.backgroundTintList = enabledColor
            binding.buttonContinue.isEnabled = true
        } else {
            val disabledColor = ContextCompat.getColorStateList(this, R.color.disabledColor)
            binding.buttonContinue.backgroundTintList = disabledColor
            binding.buttonContinue.isEnabled = false
        }
    }

    override fun onBackPressed() {
        setResult(RESULT_OK)
        super.onBackPressed()
    }
}
