package com.example.bangwool

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.bangwool.databinding.ActivityFindpasswordBinding
import java.util.regex.Pattern
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.PorterDuff


class FindPasswordActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFindpasswordBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFindpasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.buttonCheck.setOnClickListener {
                DialogUtils.showFindPasswordDialog(this)
        }
        binding.buttonBack.setOnClickListener {
            val intent = Intent(this, PasswordActivity::class.java)
            startActivity(intent)
            finish()
        }

        with(binding) {

            textInputLayoutEmail.boxStrokeErrorColor = getColorStateList(R.color.secondary)
            textInputLayoutEmail.hint = ""
            editTextEmail.hint = "ex) banwol@google.com"
            editTextEmail.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
                if (!hasFocus && editTextEmail.text.isNullOrEmpty()) {
                    editTextEmail.hint = "ex) banwol@google.com"
                } else {
                    editTextEmail.hint = ""
                }
            }
            editTextEmail.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    validateEmail(s.toString())
                }

                override fun afterTextChanged(s: Editable?) {
                    updateButtonState()
                }
            })

            textInputLayoutName.boxStrokeErrorColor = getColorStateList(R.color.secondary)
            textInputLayoutName.hint = ""
            editTextName.hint = "이름을 입력해주세요"
            editTextName.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
                if (!hasFocus && editTextName.text.isNullOrEmpty()) {
                    editTextName.hint = "이름을 입력해주세요"
                } else {
                    editTextName.hint = ""
                }
            }
            editTextName.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {

                }
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
                override fun afterTextChanged(s: Editable?) {
                    updateButtonState()
                }
            })

        }
    }
    private fun updateButtonState() {
        val isFormValid = areAllFieldsValid()

        binding.buttonCheck.isEnabled = isFormValid
        if (isFormValid) {
            val enabledColor =
                ContextCompat.getColorStateList(this, R.color.enabledColor)
            binding.buttonCheck.backgroundTintList = enabledColor

        } else {
            val disabledColor =
                ContextCompat.getColorStateList(this, R.color.disabledColor)
            binding.buttonCheck.backgroundTintList = disabledColor
        }
    }

    private fun areAllFieldsValid(): Boolean {
        val email = binding.textInputLayoutEmail.editText?.text.toString()
        val name = binding.textInputLayoutName.editText?.text.toString()

        return validateEmail(email)&&validName(name)
    }

    private fun validateEmail(email: String): Boolean {
        val emailPattern = Pattern.compile("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")
        val errorColor = getColor(R.color.secondary)
        if (email.isEmpty()) {
            binding.textInputLayoutEmail.error = "이메일을 입력하세요."
            binding.textInputLayoutEmail.defaultHintTextColor = ColorStateList.valueOf(errorColor)
            binding.textInputLayoutEmail.isErrorEnabled = true
            updateEndIcon(false)
            return false
        } else if (!emailPattern.matcher(email).matches()) {
            binding.textInputLayoutEmail.error = "올바른 이메일 형식이 아니에요"
            binding.textInputLayoutEmail.defaultHintTextColor = ColorStateList.valueOf(errorColor)
            binding.textInputLayoutEmail.isErrorEnabled = true
            updateEndIcon(false)
            return false
        } else {
            binding.textInputLayoutEmail.error = null
            binding.textInputLayoutEmail.isErrorEnabled = true
            updateEndIcon(true)
            return true
        }
    }

    private fun validName(name: String): Boolean {
        val namePattern = Pattern.compile("^[a-zA-Z0-9가-힣]{1,10}\$")
        val errorColor = getColor(R.color.secondary)
        if (!namePattern.matcher(name).matches()) {
            binding.editTextName.error = "이름의 형식을 확인해 주세요"
            binding.textInputLayoutName.defaultHintTextColor = ColorStateList.valueOf(errorColor)
            binding.textInputLayoutName.isErrorEnabled = true
            updateEndIcon(false)
            return false
        } else {
            updateEndIcon(true)
            binding.textInputLayoutName.error = null
            binding.textInputLayoutName.isErrorEnabled = false
            return true
        }
    }

    private fun updateEndIcon(isValid: Boolean) {
        val endIconDrawable =
            if (isValid) {
                ContextCompat.getDrawable(this, R.drawable.round_check_24)
            } else {
                ContextCompat.getDrawable(this, R.drawable.ic_error_circle_outline)
            }

        if (isValid) {
            val tintColor = ContextCompat.getColor(this, R.color.gray_700)
            endIconDrawable?.setColorFilter(tintColor, PorterDuff.Mode.SRC_IN)
        }else{
            val tintColor = ContextCompat.getColor(this, R.color.secondary)
            endIconDrawable?.setColorFilter(tintColor, PorterDuff.Mode.SRC_IN)
        }

        binding.textInputLayoutEmail.setErrorIconDrawable(endIconDrawable)
        binding.textInputLayoutName.setErrorIconDrawable(endIconDrawable)
    }


}

