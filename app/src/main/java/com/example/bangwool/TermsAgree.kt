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
        setContentView(binding.root)

        setupViews()
        updateButtonState()
    }

    private fun setupViews() {
        with(binding) {
            linearLayoutPrivacyPolicy.setOnClickListener {
                navigateToPrivacyPolicy()
            }

            linearLayoutAgreements.setOnClickListener {
                navigateToAllAgreements()
            }

            checkBoxAllAgreements.setOnCheckedChangeListener { _, isChecked ->
                checkBoxPrivacyPolicy.isChecked = isChecked
                checkBoxTermsOfUse.isChecked = isChecked
                updateButtonState()
            }

            checkBoxPrivacyPolicy.setOnCheckedChangeListener { _, isChecked ->
                checkBoxAllAgreements.isChecked = isChecked && checkBoxTermsOfUse.isChecked
                updateButtonState()
            }

            checkBoxTermsOfUse.setOnCheckedChangeListener { _, isChecked ->
                checkBoxAllAgreements.isChecked = isChecked && checkBoxPrivacyPolicy.isChecked
                updateButtonState()
            }

            buttonBack.setOnClickListener {
                val intent = Intent(this@TermsAgree, RegisterActivity::class.java)
                startActivity(intent)
                finish()
            }

            buttonContinue.setOnClickListener {
                val intent = Intent(this@TermsAgree, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }

    private fun updateButtonState() {
        val isAllAgreementsChecked = binding.checkBoxAllAgreements.isChecked
        val isPrivacyPolicyChecked = binding.checkBoxPrivacyPolicy.isChecked
        val isTermsOfUseChecked = binding.checkBoxTermsOfUse.isChecked

        val isFormValid = isAllAgreementsChecked && isPrivacyPolicyChecked && isTermsOfUseChecked

        with(binding.buttonContinue) {
            isEnabled = isFormValid
            backgroundTintList = if (isFormValid) {
                ContextCompat.getColorStateList(this@TermsAgree, R.color.enabledColor)
            } else {
                ContextCompat.getColorStateList(this@TermsAgree, R.color.disabledColor)
            }
        }
    }

    private fun navigateToPrivacyPolicy() {
        val intent = Intent(this, PrivacyPolicy::class.java).apply {
            putExtra("checkBoxPrivacyPolicy", binding.checkBoxPrivacyPolicy.isChecked)
            putExtra("checkBoxTermsOfUse", binding.checkBoxTermsOfUse.isChecked)
            putExtra("checkBoxAllAgreements", binding.checkBoxAllAgreements.isChecked)
        }
        startActivity(intent)
    }

    private fun navigateToAllAgreements() {
        val intent = Intent(this, textViewAllAgreements::class.java).apply {
            putExtra("checkboxState", binding.checkBoxTermsOfUse.isChecked)
            putExtra("checkBoxPrivacyPolicy", binding.checkBoxPrivacyPolicy.isChecked)
        }
        startActivity(intent)
    }
}
