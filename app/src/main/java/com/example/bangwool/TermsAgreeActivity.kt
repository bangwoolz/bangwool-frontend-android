package com.example.bangwool

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.bangwool.databinding.ActivityTermsagreeBinding

class TermsAgreeActivity : AppCompatActivity() {
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
            ButtonPrivacyPolicy.setOnClickListener {
                navigateToPrivacyPolicy()
            }
            textViewPrivacyPolicy.setOnClickListener {
                navigateToPrivacyPolicy()
            }
            ButtonViewAllAgreements.setOnClickListener {
                navigateToAllAgreements()
            }
            textViewAllAgreements.setOnClickListener {
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
                finish()
            }

            buttonContinue.setOnClickListener {
                val i = Intent(this@TermsAgreeActivity, LoginActivity::class.java)
                i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(i)
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
                ContextCompat.getColorStateList(this@TermsAgreeActivity, R.color.enabledColor)
            } else {
                ContextCompat.getColorStateList(this@TermsAgreeActivity, R.color.disabledColor)
            }
        }
    }

    private fun navigateToPrivacyPolicy() {
        val intent = Intent(this, PrivacyPolicyActivity::class.java)
        startActivity(intent)
    }

    private fun navigateToAllAgreements() {
        val intent = Intent(this, textViewAllAgreementsActivity::class.java)
        startActivity(intent)
    }
}
//완전히 끝났을때