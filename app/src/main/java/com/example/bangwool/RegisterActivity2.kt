package com.example.bangwool

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.CheckBox
import androidx.core.content.ContextCompat

class RegisterActivity2 : AppCompatActivity() {
    private lateinit var checkBoxAllAgreements: CheckBox
    private lateinit var checkBoxPrivacyPolicy: CheckBox
    private lateinit var checkBoxTermsOfUse: CheckBox
    private lateinit var buttonBack: Button
    private lateinit var buttonContinue: Button
    private lateinit var buttonPrivacyPolicy: Button

    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register2)

        checkBoxAllAgreements = findViewById(R.id.checkBoxAllAgreements)
        checkBoxPrivacyPolicy = findViewById(R.id.checkBoxPrivacyPolicy)
        checkBoxTermsOfUse = findViewById(R.id.checkBoxTermsOfUse)
        buttonBack = findViewById(R.id.buttonBack)
        buttonContinue = findViewById(R.id.buttonContinue)
        buttonPrivacyPolicy = findViewById(R.id.buttonPrivacyPolicy)

        buttonPrivacyPolicy.setOnClickListener {
            navigateToRegisterActivity3()
        }

        checkBoxAllAgreements.setOnCheckedChangeListener { _, isChecked ->
            checkBoxPrivacyPolicy.isChecked = isChecked
            checkBoxTermsOfUse.isChecked = isChecked
            updateButtonState()
        }

        checkBoxPrivacyPolicy.setOnCheckedChangeListener { _, _ ->
            updateButtonState()
        }

        checkBoxTermsOfUse.setOnCheckedChangeListener { _, _ ->
            updateButtonState()
        }

        buttonBack.setOnClickListener {
            onBackPressed()
        }

        updateButtonState()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish() // 현재 액티비티 종료
    }

    private fun updateButtonState() {
        val isAllAgreementsChecked = checkBoxAllAgreements.isChecked
        val isPrivacyPolicyChecked = checkBoxPrivacyPolicy.isChecked
        val isTermsOfUseChecked = checkBoxTermsOfUse.isChecked

        val isFormValid = isAllAgreementsChecked && isPrivacyPolicyChecked && isTermsOfUseChecked

        buttonContinue.isEnabled = isFormValid
        if (isFormValid) {
            val enabledColor = ContextCompat.getColorStateList(this, R.color.enabledColor)
            buttonContinue.backgroundTintList = enabledColor
        } else {
            val disabledColor = ContextCompat.getColorStateList(this, R.color.disabledColor)
            buttonContinue.backgroundTintList = disabledColor
        }
    }



    private fun navigateToRegisterActivity3() {
        val intent = Intent(this, RegisterActivity3::class.java)
        startActivity(intent)
    }

}
