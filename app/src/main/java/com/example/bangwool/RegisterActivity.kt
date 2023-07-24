package com.example.bangwool

import android.graphics.PorterDuff
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.bangwool.databinding.ActivityRegisterBinding
import java.util.regex.Pattern

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private val textColorFocused = Color.parseColor("#111111")
    private val textColorUnFocused = Color.parseColor("#616161")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)


        with(binding) {

            textInputLayoutEmail.boxStrokeErrorColor = getColorStateList(R.color.secondary)
            textInputLayoutEmail.hint = ""
            editTextEmail.hint = "ex) banwol@google.com"
            editTextEmail.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
                if (!hasFocus && editTextEmail.text.isNullOrEmpty()) {
                    editTextEmail.setTextColor(textColorFocused)
                    editTextEmail.hint = "ex) banwol@google.com"
                } else {
                    editTextNickname.setTextColor(textColorUnFocused)
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
            editTextName.hint = "실명을 입력하세요"
            editTextName.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
                if (!hasFocus && editTextName.text.isNullOrEmpty()) {
                    editTextEmail.setTextColor(textColorFocused)
                    editTextName.hint = "실명을 입력하세요"
                } else {
                    editTextNickname.setTextColor(textColorUnFocused)
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
                    validName(s.toString())
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
                override fun afterTextChanged(s: Editable?) {
                    updateButtonState()
                }
            })

            textInputLayoutNickname.boxStrokeErrorColor = getColorStateList(R.color.secondary)
            textInputLayoutNickname.hint = ""
            editTextNickname.hint = "5글자 이하로 입력해주세요"
            editTextNickname.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
                if (!hasFocus && editTextNickname.text.isNullOrEmpty()) {
                    editTextEmail.setTextColor(textColorFocused)
                    editTextNickname.hint = "5글자 이하로 입력해주세요"
                } else {
                    editTextNickname.setTextColor(textColorUnFocused)
                    editTextNickname.hint = ""
                }
            }
            editTextNickname.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(
                    s: CharSequence?,
                    start: Int,
                    before: Int,
                    count: Int
                ) {
                    validatePassword(s.toString())
                }

                override fun afterTextChanged(s: Editable?) {
                    updateButtonState()
                    updateConfirmButtonState()
                }
            })

            textInputLayoutPassword.boxStrokeErrorColor = getColorStateList(R.color.secondary)
            textInputLayoutPassword.hint = ""
            editTextPassword.hint = "8~12자 사이로 입력해주세요"
            editTextPassword.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
                if (!hasFocus && editTextPassword.text.isNullOrEmpty()) {
                    editTextEmail.setTextColor(textColorFocused)
                    editTextPassword.hint = "8~12자 사이로 입력해주세요"
                } else {
                    editTextNickname.setTextColor(textColorUnFocused)
                    editTextPassword.hint = ""
                }
            }
            editTextPassword.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(
                    s: CharSequence?,
                    start: Int,
                    before: Int,
                    count: Int
                ) {
                    validatePassword(s.toString())
                }

                override fun afterTextChanged(s: Editable?) {
                    updateButtonState()
                }
            })

            textInputLayoutConfirmPassword.boxStrokeErrorColor = getColorStateList(R.color.secondary)
            textInputLayoutConfirmPassword.hint = ""
            editTextConfirmPassword.hint = "패스워드를 확인해주세요"
            editTextConfirmPassword.onFocusChangeListener =
                View.OnFocusChangeListener { _, hasFocus ->
                    if (!hasFocus && editTextConfirmPassword.text.isNullOrEmpty()) {
                        editTextEmail.setTextColor(textColorFocused)
                        editTextConfirmPassword.hint = "패스워드를 확인해주세요"
                    } else {
                        editTextNickname.setTextColor(textColorUnFocused)
                        editTextConfirmPassword.hint = ""
                    }
                }
            editTextConfirmPassword.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(
                    s: CharSequence?,
                    start: Int,
                    before: Int,
                    count: Int
                ) {
                    validateConfirmPassword(
                        editTextPassword.text.toString(),
                        s.toString()
                    )
                }

                override fun afterTextChanged(s: Editable?) {
                    updateButtonState()
                }
            })


            buttonContinue.setOnClickListener {
                val intent = Intent(this@RegisterActivity, TermsAgreeActivity::class.java)
                startActivity(intent)
            }

            buttonDuplicateCheck.setOnClickListener {
                val nickname = textInputLayoutNickname.editText?.text.toString()
                if (validateNickname(nickname)) {
                }
            }

            buttonBack.setOnClickListener {
                finish()
            }

            buttonDuplicateCheck.setOnClickListener {
                // 중복 확인 로직을 수행하는 함수를 호출~
                checkDuplicateNickname()
            }

        }
    }

    private fun updateButtonState() {
        val isFormValid = areAllFieldsValid()
        binding.buttonContinue.isEnabled = isFormValid
        if (isFormValid) {
            val enabledColor =
                ContextCompat.getColorStateList(this@RegisterActivity, R.color.enabledColor)
            binding.buttonContinue.backgroundTintList = enabledColor

        } else {
            val disabledColor =
                ContextCompat.getColorStateList(this@RegisterActivity, R.color.disabledColor)
            binding.buttonContinue.backgroundTintList = disabledColor
        }
    }

    private fun updateConfirmButtonState() {
        val isFormValid = nicknameFieldsValid()
        binding.buttonDuplicateCheck.isEnabled = isFormValid
        val buttonBackground = if (isFormValid) {
            ContextCompat.getDrawable(this, R.drawable.enabled)
        } else {
            ContextCompat.getDrawable(this, R.drawable.disabled)
        }
        binding.buttonDuplicateCheck.background = buttonBackground
    }

    private fun nicknameFieldsValid(): Boolean {
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
                validateConfirmPassword(password, confirmPassword) &&
                validName(name)
    }



    private fun validateEmail(email: String): Boolean {
        val emailPattern = Pattern.compile("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")

        val grayColor = ContextCompat.getColor(this, R.color.gray_600)
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
            binding.textInputLayoutEmail.isErrorEnabled = true
            binding.textInputLayoutConfirmPassword.boxStrokeErrorColor = ColorStateList.valueOf(grayColor)
            updateEndIcon(true)
            return true
        }
    }

    private fun validName(name: String): Boolean {
        val namePattern = Pattern.compile("^[a-zA-Z0-9가-힣]{1,10}\$")
        if (!namePattern.matcher(name).matches()) {
            binding.editTextName.error = "이름의 형식을 확인해 주세요"
            val errorColor = ContextCompat.getColor(this, R.color.secondary)
            binding.textInputLayoutName.defaultHintTextColor = ColorStateList.valueOf(errorColor)
            binding.textInputLayoutName.isErrorEnabled = true
            updateEndIconElse(false)
            return false
        } else {
            binding.textInputLayoutName.error = null
            binding.textInputLayoutName.isErrorEnabled = false
            return true
        }
    }

    private fun validateNickname(nickname: String): Boolean {
        val nicknamePattern = Pattern.compile("^[a-zA-Z0-9가-힣]{1,5}\$")
        if (nickname.isEmpty()) {
            binding.textInputLayoutNickname.error = "닉네임을 입력하세요."
            binding.textInputLayoutNickname.isErrorEnabled = true
            updateEndIconElse(false)
            return false
        } else if (nickname.length > 5) {
            binding.textInputLayoutNickname.error = "닉네임은 5글자 이하여야해요."
            binding.textInputLayoutNickname.isErrorEnabled = true
            updateEndIconElse(false)
            return false
        } else if (!nicknamePattern.matcher(nickname).matches()) {
            binding.textInputLayoutNickname.error = "닉네임 형식을 확인해주세요."
            binding.textInputLayoutNickname.isErrorEnabled = true
            updateEndIconElse(false)
            return false
        } else {
            binding.textInputLayoutNickname.error = null
            binding.textInputLayoutNickname.isErrorEnabled = false
            updateEndIconElse(true)
            return true
        }
    }


    private fun validatePassword(password: String): Boolean {
        val passwordPattern =
            Pattern.compile("(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,12}")
        if (!passwordPattern.matcher(password).matches()) {
            binding.textInputLayoutPassword.error = "패스워드 조건을 확인해주세요(8-12글자 사이)"
            binding.textInputLayoutPassword.isErrorEnabled = true
            updateEndIconElse(false)
            return false
        } else {
            binding.textInputLayoutPassword.error = null
            binding.textInputLayoutPassword.isErrorEnabled = false
            updateEndIconElse(true)
            return true
        }
    }

    private fun validateConfirmPassword(password: String, confirmPassword: String): Boolean {
        val errorColor = ContextCompat.getColor(this, R.color.secondary)
        if (confirmPassword != password) {
            binding.textInputLayoutConfirmPassword.error = "패스워드가 달라요"
            binding.textInputLayoutConfirmPassword.isErrorEnabled = true
            binding.textInputLayoutEmail.boxStrokeErrorColor = ColorStateList.valueOf(errorColor)
            updateEndIconElse(false)
            return false
        } else {
            binding.textInputLayoutConfirmPassword.error = null
            binding.textInputLayoutConfirmPassword.isErrorEnabled = false
            updateEndIconElse(true)
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
    }

    private fun updateEndIconElse(isValid: Boolean) {
        val endIconDrawable =
            if (isValid) {
                    ColorDrawable(Color.TRANSPARENT)
            } else {
                ColorDrawable(Color.TRANSPARENT)
            }
        binding.textInputLayoutPassword.setErrorIconDrawable(endIconDrawable)
        binding.textInputLayoutConfirmPassword.setErrorIconDrawable(endIconDrawable)
        binding.textInputLayoutNickname.setErrorIconDrawable(endIconDrawable)
    }

    private fun checkDuplicateNickname() {
        val nickname = binding.textInputLayoutNickname.editText?.text.toString()

        // 여기에서 중복 확인 로직을 수행할것.

        if (nickname.isNotEmpty()) {
            // 만약 닉네임이 비어있지 않다면, 중복 확인 중임을 알리는 토스트 메시지를 표시.
            Toast.makeText(this, "닉네임 중복 확인 중: $nickname", Toast.LENGTH_SHORT).show()
        } else {

        }
    }


}