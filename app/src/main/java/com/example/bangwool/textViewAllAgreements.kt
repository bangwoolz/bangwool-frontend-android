package com.example.bangwool

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.bangwool.databinding.ActivityTextviewallagreementsBinding

class textViewAllAgreements : AppCompatActivity() {
    private lateinit var binding: ActivityTextviewallagreementsBinding
    private var checkboxState = false
    private var isScrolledEnd = false
    private var isCheckBoxPrivacyPolicyChecked: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTextviewallagreementsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        checkboxState = intent.getBooleanExtra("checkboxState", false)
        isCheckBoxPrivacyPolicyChecked = intent.getBooleanExtra("checkBoxPrivacyPolicy", false)

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
        updateButtonState()
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
