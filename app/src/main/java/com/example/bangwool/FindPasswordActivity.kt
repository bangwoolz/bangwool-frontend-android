package com.example.bangwool

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.bangwool.databinding.ActivityFindpasswordBinding
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.PorterDuff
import java.util.regex.Pattern

class FindPasswordActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFindpasswordBinding
    private val textColorFocused = Color.parseColor("#FF000000")
    private val textColorUnFocused = Color.parseColor("#616161")
    private var isEmailFocused = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFindpasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 최초 포커스를 editTextName에 두기
        binding.editTextName.requestFocus()

        // 확인 버튼 클릭 시 다이얼로그 보여주기
        binding.buttonCheck.setOnClickListener {
            DialogUtils.showFindPasswordDialog(this)
        }

        // 뒤로 가기 버튼 클릭 시 PasswordActivity로 이동
        binding.buttonBack.setOnClickListener {
            val intent = Intent(this, PasswordActivity::class.java)
            startActivity(intent)
            finish()
        }

        // 이름 입력란 처리
        with(binding) {
            textInputLayoutName.boxStrokeErrorColor = getColorStateList(R.color.secondary)
            textInputLayoutName.hint = ""
            editTextName.hint = "실명을 입력하세요"
            editTextName.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
                if (!hasFocus && editTextName.text.isNullOrEmpty()) {
                    editTextName.setTextColor(textColorFocused)
                    editTextName.hint = "실명을 입력하세요"
                } else {
                    editTextName.setTextColor(textColorUnFocused)
                    editTextName.hint = ""
                }

                // 포커스를 받을 때 유효성 검사 실행
                if (hasFocus) {
                    validateName(editTextName.text.toString())
                }
            }
            editTextName.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                }


                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    // 이름이 변경된 후에 버튼 상태 업데이트
                    updateButtonState()
                }

                override fun afterTextChanged(s: Editable?) {
                }
            })

            // 이메일 입력란 처리
            textInputLayoutEmail.boxStrokeErrorColor = getColorStateList(R.color.secondary)
            textInputLayoutEmail.hint = ""
            editTextEmail.hint = "ex) banwol@google.com"
            editTextEmail.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
                if (!hasFocus && editTextEmail.text.isNullOrEmpty()) {
                    editTextEmail.setTextColor(textColorFocused)
                    editTextEmail.hint = "ex) banwol@google.com"
                } else {
                    editTextEmail.setTextColor(textColorUnFocused)
                    editTextEmail.hint = ""
                }

                // 포커스를 받을 때 유효성 검사 실행
                if (hasFocus) {
                    validateEmail(editTextEmail.text.toString())
                }
            }
            editTextEmail.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    // 이메일이 변경된 후에 버튼 상태 업데이트
                    updateButtonState()
                }

                override fun afterTextChanged(s: Editable?) {
                }
            })
        }
    }

    private fun updateButtonState() {
        val isFormValid = areAllFieldsValid()

        binding.buttonCheck.isEnabled = isFormValid
        if (isFormValid) {
            val enabledColor = ContextCompat.getColorStateList(this, R.color.enabledColor)
            binding.buttonCheck.backgroundTintList = enabledColor
        } else {
            val disabledColor = ContextCompat.getColorStateList(this, R.color.disabledColor)
            binding.buttonCheck.backgroundTintList = disabledColor
        }
    }


    private fun areAllFieldsValid(): Boolean {
        val email = binding.textInputLayoutEmail.editText?.text.toString()
        val name = binding.textInputLayoutName.editText?.text.toString()

        return validateName(name) && validateEmail(email)
    }

    // 이메일 유효성 검사 함수
    private fun validateEmail(email: String): Boolean {
        val emailPattern = Pattern.compile("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")
        val errorColor = getColor(R.color.secondary)
        if (!isEmailFocused && email.isEmpty()) {
            binding.textInputLayoutEmail.error = "이메일을 입력하세요."
            binding.textInputLayoutEmail.defaultHintTextColor = ColorStateList.valueOf(errorColor)
            binding.textInputLayoutEmail.isErrorEnabled = true
            updateEndIcon(false)
            return false
        } else if (!isEmailFocused && !emailPattern.matcher(email).matches()) {
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

    private fun validateName(name: String): Boolean {
        return name.isNotEmpty()
    }

    private fun updateEndIcon(isValid: Boolean) {
        val endIconDrawable = if (isValid) {
            ContextCompat.getDrawable(this, R.drawable.round_check_24)
        } else {
            ContextCompat.getDrawable(this, R.drawable.ic_error_circle_outline)
        }

        if (isValid) {
            val tintColor = ContextCompat.getColor(this, R.color.gray_700)
            endIconDrawable?.setColorFilter(tintColor, PorterDuff.Mode.SRC_IN)
        } else {
            val tintColor = ContextCompat.getColor(this, R.color.secondary)
            endIconDrawable?.setColorFilter(tintColor, PorterDuff.Mode.SRC_IN)
        }

        binding.textInputLayoutEmail.setErrorIconDrawable(endIconDrawable)
        binding.textInputLayoutName.setErrorIconDrawable(endIconDrawable)
    }
}

