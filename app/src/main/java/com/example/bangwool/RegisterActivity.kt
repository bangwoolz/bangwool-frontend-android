package com.example.bangwool

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

            override fun afterTextChanged(s: Editable?) {}
        })

        editTextName?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                validateName(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        editTextNickname?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                validateNickname(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        editTextPassword?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                validatePassword(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        editTextConfirmPassword?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                validateConfirmPassword(editTextPassword?.text.toString(), s.toString())
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun isFormValid(): Boolean {
        val email = textInputLayoutEmail.editText?.text.toString()
        val name = textInputLayoutName.editText?.text.toString()
        val nickname = textInputLayoutNickname.editText?.text.toString()
        val password = textInputLayoutPassword.editText?.text.toString()
        val confirmPassword = textInputLayoutConfirmPassword.editText?.text.toString()

        return validateEmail(email) &&
                validateName(name) &&
                validateNickname(nickname) &&
                validatePassword(password) &&
                validateConfirmPassword(password, confirmPassword)
    }

    private fun validateEmail(email: String): Boolean {
        val emailPattern = Pattern.compile("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")
        if (email.isEmpty()) {
            textInputLayoutEmail.error = "이메일을 입력하세요."
            buttonContinue.isEnabled = false
            return false
        } else if (!emailPattern.matcher(email).matches()) {
            textInputLayoutEmail.error = "유효한 이메일을 입력하세요."
            buttonContinue.isEnabled = false
            return false
        } else {
            textInputLayoutEmail.error = null
            return true
        }
    }

    private fun validateName(name: String): Boolean {
        if (name.isEmpty()) {
            textInputLayoutName.error = "이름을 입력하세요."
            buttonContinue.isEnabled = false
            return false
        } else {
            textInputLayoutName.error = null
            return true
        }
    }

    private fun validateNickname(nickname: String): Boolean {
        if (nickname.isEmpty()) {
            textInputLayoutNickname.error = "닉네임을 입력하세요."
            buttonContinue.isEnabled = false
            return false
        } else {
            textInputLayoutNickname.error = null
            return true
        }
    }

    private fun validatePassword(password: String): Boolean {
        val passwordPattern = Pattern.compile("(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}")
        if (password.isEmpty()) {
            textInputLayoutPassword.error = "비밀번호를 입력하세요."
            buttonContinue.isEnabled = false
            return false
        } else if (!passwordPattern.matcher(password).matches()) {
            textInputLayoutPassword.error = "비밀번호는 최소 8자 이상이며, 숫자, 소문자, 대문자, 특수문자를 포함해야 합니다."
            buttonContinue.isEnabled = false
            return false
        } else {
            textInputLayoutPassword.error = null
            return true
        }
    }

    private fun validateConfirmPassword(password: String, confirmPassword: String): Boolean {
        if (confirmPassword.isEmpty()) {
            textInputLayoutConfirmPassword.error = "비밀번호를 다시 입력하세요."
            buttonContinue.isEnabled = false
            return false
        } else if (password != confirmPassword) {
            textInputLayoutConfirmPassword.error = "비밀번호가 일치하지 않습니다."
            buttonContinue.isEnabled = false
            return false
        } else {
            textInputLayoutConfirmPassword.error = null
            return true
        }
    }
}
