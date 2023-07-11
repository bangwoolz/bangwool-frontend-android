package com.example.bangwool

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.bangwool.databinding.ActivityRegisterBinding
import java.util.regex.Pattern

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {
            textInputLayoutEmail.editText?.let { editTextEmail ->
                editTextEmail.hint = ""
                editTextEmail.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
                    if (hasFocus) {
                        textInputLayoutEmail.hint = ""
                    }
                }
                editTextEmail.addTextChangedListener(object : TextWatcher {
                    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                        if (s.isNullOrEmpty()) {
                            textInputLayoutEmail.hint = ""
                        }
                    }

                    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                        validateEmail(s.toString())
                    }

                    override fun afterTextChanged(s: Editable?) {
                        if (s.isNullOrEmpty()) {
                            textInputLayoutEmail.hint = ""
                        } else {
                            textInputLayoutEmail.hint = ""
                        }
                        updateButtonState()
                    }
                })
            }

            textInputLayoutName.editText?.let { editTextName ->
                editTextName.hint = ""
                editTextName.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
                    if (hasFocus) {
                        textInputLayoutName.hint = ""
                    }
                }
                editTextName.addTextChangedListener(object : TextWatcher {
                    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                        if (s.isNullOrEmpty()) {
                            textInputLayoutEmail.hint = ""
                        }
                    }
                    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
                    override fun afterTextChanged(s: Editable?) {
                        if (s.isNullOrEmpty()) {
                            textInputLayoutName.hint = ""
                        } else {
                            textInputLayoutName.hint = ""
                        }
                        updateButtonState()
                    }
                })
            }
            textInputLayoutNickname.editText?.let { editTextNickname ->
                editTextNickname.hint = ""
                editTextNickname.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
                    if (hasFocus) {
                        textInputLayoutNickname.hint = ""
                    }
                }
                editTextNickname.addTextChangedListener(object : TextWatcher {
                    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                        if (s.isNullOrEmpty()) {
                            textInputLayoutEmail.hint = ""
                        }
                    }

                    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                        validateNickname(s.toString())
                    }

                    override fun afterTextChanged(s: Editable?) {
                        if (s.isNullOrEmpty()) {
                            textInputLayoutNickname.hint = ""
                        } else {
                            textInputLayoutNickname.hint = ""
                        }
                        updateButtonState()
                        updateConfirmButtonState()
                    }
                })
            }

            textInputLayoutPassword.editText?.let { editTextPassword ->
                editTextPassword.hint = ""
                editTextPassword.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
                    if (hasFocus) {
                        textInputLayoutPassword.hint = ""
                    }
                }
                editTextPassword.addTextChangedListener(object : TextWatcher {
                    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                        if (s.isNullOrEmpty()) {
                            textInputLayoutEmail.hint = ""
                        }
                    }

                    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                        validatePassword(s.toString())
                    }

                    override fun afterTextChanged(s: Editable?) {
                        if (s.isNullOrEmpty()) {
                            textInputLayoutPassword.hint = ""
                        } else {
                            textInputLayoutPassword.hint = ""
                        }
                        updateButtonState()
                    }
                })
            }

            textInputLayoutConfirmPassword.editText?.let { editTextConfirmPassword ->
                editTextConfirmPassword.hint = ""
                editTextConfirmPassword.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
                    if (hasFocus) {
                        textInputLayoutConfirmPassword.hint = ""
                    }
                }
                editTextConfirmPassword.addTextChangedListener(object : TextWatcher {
                    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                        validateConfirmPassword(
                            textInputLayoutPassword.editText?.text.toString(),
                            s.toString()
                        )
                    }

                    override fun afterTextChanged(s: Editable?) {
                        if (s.isNullOrEmpty()) {
                            textInputLayoutConfirmPassword.hint = ""
                        } else {
                            textInputLayoutConfirmPassword.hint = ""
                        }
                        updateButtonState()
                    }
                })
            }

            buttonContinue.setOnClickListener {
                val intent = Intent(this@RegisterActivity, TermsAgree::class.java)
                startActivity(intent)
            }

            buttonDuplicateCheck.setOnClickListener {
                val nickname = textInputLayoutNickname.editText?.text.toString()
                if (validateNickname(nickname)) {
                    // 여기에 닉네임 로직 추가
                }
            }
        }
    }

    private fun updateButtonState() {
        val isFormValid = areAllFieldsValid()

        binding.buttonContinue.isEnabled = isFormValid
        if (isFormValid) {
            val enabledColor = ContextCompat.getColorStateList(this@RegisterActivity, R.color.enabledColor)
            binding.buttonContinue.backgroundTintList = enabledColor

        } else {
            val disabledColor = ContextCompat.getColorStateList(this@RegisterActivity, R.color.disabledColor)
            binding.buttonContinue.backgroundTintList = disabledColor
        }
    }

    private fun updateConfirmButtonState() {
        val isFormValid = fieldsValid()
        binding.buttonDuplicateCheck.isEnabled = isFormValid
        val buttonBackground = if (isFormValid) {
            ContextCompat.getDrawable(this, R.drawable.enabled)
        } else {
            ContextCompat.getDrawable(this, R.drawable.disabled)
        }
        binding.buttonDuplicateCheck.background = buttonBackground
    }

    private fun fieldsValid(): Boolean {
        val nickname = binding.textInputLayoutNickname.editText?.text.toString()
        return validateNickname(nickname)
    }

    private fun areAllFieldsValid(): Boolean {
        val email = binding.textInputLayoutEmail.editText?.text.toString()
        val name = binding.textInputLayoutName.editText?.text.toString()
        val nickname = binding.textInputLayoutNickname.editText?.text.toString()
        val password = binding.textInputLayoutPassword.editText?.text.toString()
        val confirmPassword = binding.textInputLayoutConfirmPassword.editText?.text.toString()

        return validateEmail(email) &&
                validateNickname(nickname) &&
                validatePassword(password) &&
                validateConfirmPassword(password, confirmPassword)
    }

    private fun validateEmail(email: String): Boolean {
        val emailPattern = Pattern.compile("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")
        if (email.isEmpty()) {
            binding.textInputLayoutEmail.error = "이메일을 입력하세요."
            binding.textInputLayoutEmail.isErrorEnabled = true
            updateEndIcon(false)
            return false
        } else if (!emailPattern.matcher(email).matches()) {
            binding.textInputLayoutEmail.error = "올바른 이메일 형식이 아니에요"
            binding.textInputLayoutEmail.isErrorEnabled = true
            updateEndIcon(false)
            return false
        } else {
            binding.textInputLayoutEmail.error = null
            binding.textInputLayoutEmail.isErrorEnabled = false
            updateEndIcon(true)
            return true
        }
    }

    private fun validateNickname(nickname: String): Boolean {
        val nicknamePattern = Pattern.compile("^[a-zA-Z0-9가-힣]{1,5}\$")
        if (!nicknamePattern.matcher(nickname).matches()) {
            binding.textInputLayoutNickname.error = "닉네임 조건을 확인하세요."
            binding.textInputLayoutNickname.isErrorEnabled = true
            return false
        } else if (nickname.isEmpty()) {
            binding.textInputLayoutNickname.error = "닉네임을 입력하세요."
            binding.textInputLayoutNickname.isErrorEnabled = true
            return false
        } else {
            binding.textInputLayoutNickname.error = null
            return true
        }
    }

    private fun validatePassword(password: String): Boolean {
        val passwordPattern = Pattern.compile("(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}")
        if (!passwordPattern.matcher(password).matches()) {
            binding.textInputLayoutPassword.error = "패스워드 조건을 확인해주세요(8-12글자 사이)"
            binding.textInputLayoutPassword.isErrorEnabled = true
            return false
        } else {
            binding.textInputLayoutPassword.error = null
            return true
        }
    }

    private fun validateConfirmPassword(password: String, confirmPassword: String): Boolean {
        if (confirmPassword != password) {
            binding.textInputLayoutConfirmPassword.error = "패스워드가 달라요"
            binding.textInputLayoutConfirmPassword.isErrorEnabled = true
            return false
        } else {
            binding.textInputLayoutConfirmPassword.error = null
            return true
        }
    }

    private fun updateEndIcon(isValid: Boolean) {
        val endIconDrawable = if (isValid) {
            ContextCompat.getDrawable(this, R.drawable.check)
        } else {
            ContextCompat.getDrawable(this, R.drawable.error_circle_outline)
        }
        binding.textInputLayoutEmail.endIconDrawable = endIconDrawable

        val endIconTint = if (isValid) {
            ColorStateList.valueOf(Color.parseColor("#888888"))
        } else {
            ColorStateList.valueOf(Color.parseColor("#888888"))
        }
        binding.textInputLayoutEmail.setEndIconTintList(endIconTint)
    }
}

fun onContinueClicked(view: View) {
    val intent = Intent(view.context, TermsAgree::class.java)
    view.context.startActivity(intent)
}
