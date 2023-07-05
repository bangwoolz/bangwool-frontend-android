package com.example.bangwool

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.*
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import java.util.regex.Pattern

class RegisterActivity : AppCompatActivity() {

    private lateinit var textInputLayoutEmail: TextInputLayout
    private lateinit var textInputLayoutName: TextInputLayout
    private lateinit var textInputLayoutNickname: TextInputLayout
    private lateinit var textInputLayoutPassword: TextInputLayout
    private lateinit var textInputLayoutConfirmPassword: TextInputLayout
    private lateinit var buttonContinue: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        textInputLayoutEmail = findViewById(R.id.textInputLayoutEmail)
        textInputLayoutName = findViewById(R.id.textInputLayoutName)
        textInputLayoutNickname = findViewById(R.id.textInputLayoutNickname)
        textInputLayoutPassword = findViewById(R.id.textInputLayoutPassword)
        textInputLayoutConfirmPassword = findViewById(R.id.textInputLayoutConfirmPassword)
        buttonContinue = findViewById(R.id.buttonContinue)

        val editTextEmail = textInputLayoutEmail.editText
        val editTextName = textInputLayoutName.editText
        val editTextNickname = textInputLayoutNickname.editText
        val editTextPassword = textInputLayoutPassword.editText
        val editTextConfirmPassword = textInputLayoutConfirmPassword.editText

        editTextEmail?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                validateEmail(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {
                if (s.isNullOrEmpty()) {
                    textInputLayoutEmail.hint = "이메일"
                } else {
                    textInputLayoutEmail.hint = ""
                }
                updateButtonState()
            }
        })

        editTextName?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                validateName(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {
                if (s.isNullOrEmpty()) {
                    textInputLayoutName.hint = "이름"
                } else {
                    textInputLayoutName.hint = ""
                }
                updateButtonState()
            }
        })

        editTextNickname?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                validateNickname(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {
                if (s.isNullOrEmpty()) {
                    textInputLayoutNickname.hint = "닉네임"
                } else {
                    textInputLayoutNickname.hint = ""
                }
                updateButtonState()
            }
        })

        editTextPassword?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                validatePassword(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {
                if (s.isNullOrEmpty()) {
                    textInputLayoutPassword.hint = "비밀번호"
                } else {
                    textInputLayoutPassword.hint = ""
                }
                updateButtonState()
            }
        })

        editTextConfirmPassword?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                validateConfirmPassword(editTextPassword?.text.toString(), s.toString())
            }

            override fun afterTextChanged(s: Editable?) {
                if (s.isNullOrEmpty()) {
                    textInputLayoutConfirmPassword.hint = "비밀번호 확인"
                } else {
                    textInputLayoutConfirmPassword.hint = ""
                }
                updateButtonState()
            }
        })
    }

    private fun updateButtonState() {
        val isFormValid = areAllFieldsValid()

        buttonContinue.isEnabled = isFormValid
        if (isFormValid) {
            val enabledColor = getColorStateList(this, R.color.enabledColor)
            buttonContinue.backgroundTintList = enabledColor

        } else {
            val disabledColor = getColorStateList(this, R.color.disabledColor)
            buttonContinue.backgroundTintList = disabledColor

        }
    }

    private fun areAllFieldsValid(): Boolean {
        val email = textInputLayoutEmail.editText?.text.toString()
        val name = textInputLayoutName.editText?.text.toString()
        val nickname = textInputLayoutNickname.editText?.text.toString()
        val password = textInputLayoutPassword.editText?.text.toString()
        val confirmPassword = textInputLayoutConfirmPassword.editText?.text.toString()

        return validateEmail(email) && validateName(name) && validateNickname(nickname) &&
                validatePassword(password) && validateConfirmPassword(password, confirmPassword)
    }

    private fun validateEmail(email: String): Boolean {
        val emailPattern = Pattern.compile("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")
        if (email.isEmpty()) {
            textInputLayoutEmail.error = "이메일을 입력하세요."
            textInputLayoutEmail.isErrorEnabled = true
            updateEndIcon(false)
            return false
        } else if (!emailPattern.matcher(email).matches()) {
            textInputLayoutEmail.error = "올바른 이메일 형식이 아니에요"
            textInputLayoutEmail.isErrorEnabled = true
            updateEndIcon(false)
            return false
        } else {
            textInputLayoutEmail.error = null
            textInputLayoutEmail.isErrorEnabled = false
            updateEndIcon(true)
            return true
        }
    }

    private fun validateName(name: String): Boolean {
        if (name.isEmpty()) {
            textInputLayoutName.error = "이름을 입력하세요."
            return false
        } else {
            textInputLayoutName.error = null
            return true
        }
    }

    private fun validateNickname(nickname: String): Boolean {
        if (nickname.isEmpty()) {
            textInputLayoutNickname.error = "닉네임을 입력하세요."
            return false
        } else {
            textInputLayoutNickname.error = null
            return true
        }
    }

    private fun validatePassword(password: String): Boolean {
        val passwordPattern =
            Pattern.compile("(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}")
        if (!passwordPattern.matcher(password).matches()) {
            textInputLayoutPassword.error = "패스워드 조건을 확인해주세요(8-12글자 사이)"
            textInputLayoutPassword.isErrorEnabled = true
            return false
        } else {
            textInputLayoutPassword.error = null
            return true
        }
    }

    private fun validateConfirmPassword(password: String, confirmPassword: String): Boolean {
        if (confirmPassword != password) {
            textInputLayoutConfirmPassword.error = "패스워드가 달라요"
            textInputLayoutConfirmPassword.isErrorEnabled = true
            return false
        } else {
            textInputLayoutConfirmPassword.error = null
            return true
        }
    }

    private fun updateEndIcon(isValid: Boolean) {
        if (isValid) {
            textInputLayoutEmail.endIconMode = TextInputLayout.END_ICON_CUSTOM
            textInputLayoutEmail.endIconDrawable = getDrawable(this, R.drawable.check)
        } else {
            textInputLayoutEmail.endIconMode = TextInputLayout.END_ICON_NONE
            textInputLayoutEmail.endIconDrawable = getDrawable(this,R.drawable.error_circle_outline)
        }
    }

    fun onContinueClicked(view: View) {
        Toast.makeText(this, "계속하기 클릭 클릭", Toast.LENGTH_SHORT).show()
        val intent = Intent(this@RegisterActivity, RegisterActivity2::class.java)
        startActivity(intent)
    }
}
