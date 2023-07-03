package com.example.bangwool

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.regex.Pattern


class RegisterActivity : AppCompatActivity() {

    private lateinit var editTextEmail: EditText
    private lateinit var editTextName: EditText
    private lateinit var editTextNickname: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var editTextConfirmPassword: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register_activity)

        val imageViewBack = findViewById<ImageView>(R.id.imageViewBack)
        val buttonDuplicateCheck = findViewById<Button>(R.id.buttonDuplicateCheck)
        val buttonContinue = findViewById<Button>(R.id.buttonContinue)

        editTextEmail = findViewById(R.id.editTextEmail)
        editTextName = findViewById(R.id.editTextName)
        editTextNickname = findViewById(R.id.editTextNickname)
        editTextPassword = findViewById(R.id.editTextPassword)
        editTextConfirmPassword = findViewById(R.id.editTextConfirmPassword)

        val textViewDuplicateMessage = findViewById<TextView>(R.id.textViewDuplicateMessage)




        imageViewBack.setOnClickListener {

        }

        buttonDuplicateCheck.setOnClickListener {
            // 중복 확인 로직 추가
        }

        buttonContinue.setOnClickListener {
            if (validateEmail() && validateName() && validateNickname() && validatePassword()) {
                // 계속하기 버튼 클릭 로직 추가
            }
        }
    }

    private fun validateEmail(): Boolean {
        val email = editTextEmail.text.toString().trim()
        if (email.isEmpty()) {
            Toast.makeText(this, "이메일을 입력해주세요.", Toast.LENGTH_SHORT).show()
            return false
        } else if (!Pattern.matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+", email)) {
            Toast.makeText(this, "올바른 이메일 형식이 아닙니다.", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    private fun validateName(): Boolean {
        val name = editTextName.text.toString().trim()
        if (name.isEmpty()) {
            Toast.makeText(this, "이름을 입력해주세요.", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    private fun validateNickname(): Boolean {
        val nickname = editTextNickname.text.toString().trim()
        if (nickname.isEmpty()) {
            Toast.makeText(this, "닉네임을 입력해주세요.", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    private fun validatePassword(): Boolean {
        val password = editTextPassword.text.toString().trim()
        val confirmPassword = editTextConfirmPassword.text.toString().trim()
        if (password.isEmpty()) {
            Toast.makeText(this, "패스워드를 입력해주세요.", Toast.LENGTH_SHORT).show()
            return false
        } else if (password.length < 8 || password.length > 12) {
            Toast.makeText(this, "패스워드는 8~12자 이내로 입력해주세요.", Toast.LENGTH_SHORT).show()
            return false
        } else if (!Pattern.matches("^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[@#$%^&+=]).*$", password)) {
            Toast.makeText(this, "패스워드는 영어, 숫자, 특수문자를 포함해야 합니다.", Toast.LENGTH_SHORT).show()
            return false
        } else if (password != confirmPassword) {
            Toast.makeText(this, "패스워드가 일치하지 않습니다.", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

}
