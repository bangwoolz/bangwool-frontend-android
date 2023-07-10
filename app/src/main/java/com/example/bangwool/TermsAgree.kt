package com.example.bangwool

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.bangwool.databinding.ActivityTermsagreeBinding

class TermsAgree : AppCompatActivity() {
    private lateinit var binding: ActivityTermsagreeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTermsagreeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val checkBoxAllAgreements = binding.checkBoxAllAgreements
        val checkBoxPrivacyPolicy = binding.checkBoxPrivacyPolicy
        val checkBoxTermsOfUse = binding.checkBoxTermsOfUse
        val buttonBack = binding.buttonBack
        val buttonContinue = binding.buttonContinue
        val buttonPrivacyPolicy = binding.buttonPrivacyPolicy
        val buttonTermsOfUse = binding.buttonTermsOfUse

        val intent = intent

        buttonPrivacyPolicy.setOnClickListener {
            val intent = Intent(this, PrivacyPolicy::class.java)
            intent.putExtra("checkBoxPrivacyPolicy", checkBoxPrivacyPolicy.isChecked)
            intent.putExtra("checkBoxTermsOfUse", checkBoxTermsOfUse.isChecked)
            intent.putExtra("checkBoxAllAgreements", checkBoxAllAgreements.isChecked)
            startActivity(intent)
        }

        buttonTermsOfUse.setOnClickListener {
            val intent = Intent(this, textViewAllAgreements::class.java)
            intent.putExtra("checkboxState", checkBoxTermsOfUse.isChecked)
            intent.putExtra("checkBoxPrivacyPolicy", checkBoxPrivacyPolicy.isChecked)
            startActivity(intent)
        }

        checkBoxAllAgreements.setOnCheckedChangeListener { _, isChecked ->
            checkBoxPrivacyPolicy.isChecked = isChecked
            checkBoxTermsOfUse.isChecked = isChecked
            updateButtonState()
        }

        checkBoxPrivacyPolicy.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked && checkBoxTermsOfUse.isChecked) {
                checkBoxAllAgreements.isChecked = true
            } else {
                checkBoxAllAgreements.isChecked = false
            }
            updateButtonState()
        }

        checkBoxTermsOfUse.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked && checkBoxPrivacyPolicy.isChecked) {
                checkBoxAllAgreements.isChecked = true
            } else {
                checkBoxAllAgreements.isChecked = false
            }
            updateButtonState()
        }

        buttonBack.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
            finish()
        }

        buttonContinue.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        updateButtonState()
    }

    private fun updateButtonState() {
        val isAllAgreementsChecked = binding.checkBoxAllAgreements.isChecked
        val isPrivacyPolicyChecked = binding.checkBoxPrivacyPolicy.isChecked
        val isTermsOfUseChecked = binding.checkBoxTermsOfUse.isChecked

        val isFormValid = isAllAgreementsChecked && isPrivacyPolicyChecked && isTermsOfUseChecked

        binding.buttonContinue.isEnabled = isFormValid
        if (isFormValid) {
            val enabledColor = ContextCompat.getColorStateList(this, R.color.enabledColor)
            binding.buttonContinue.backgroundTintList = enabledColor
        } else {
            val disabledColor = ContextCompat.getColorStateList(this, R.color.disabledColor)
            binding.buttonContinue.backgroundTintList = disabledColor
        }
    }

    private fun navigateToRegisterActivity3() {
        val intent = Intent(this, PrivacyPolicy::class.java)
        startActivity(intent)
    }
}
