package com.example.bangwool

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import com.example.bangwool.databinding.ActivityPasswordBinding

class PasswordActivity : AppCompatActivity() {
    lateinit var binding: ActivityPasswordBinding
    val uesr_password = "1234"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
    }

    private fun init() {
        binding.apply {
            backBtn.setOnClickListener {
                finish()
            }
            passwordEt.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

                override fun afterTextChanged(s: Editable?) {
                    val pw = s.toString()

                    if (pw.isEmpty()) {
                        loginBtn.setBackgroundColor(getColor(R.color.gray))
                        pwTextInputLayout.error = "비밀번호를 입력하세요." // -> 피그마엔 없음
                    } else {
                        pwTextInputLayout.error = null
                        loginBtn.setBackgroundColor(getColor(R.color.bangwol_red))
                        loginBtn.setOnClickListener {
                            if (pw.equals(uesr_password)) {
                                pwTextInputLayout.error = "비밀번호 동일함"
                            } else {
                                pwTextInputLayout.error = "비밀번호가 달라요. 다시 입력해주세요"
                            }
                        }
                    }
                }
            })

        }
    }

//    private fun isValidPw(passwrod: String): Boolean {
//        val passwordPattern = Regex("^(?=.*[a-zA-Z0-9])(?=.*[!@#$%^&*()])(?=\\S+$).{8,12}$")
//        return passwordPattern.matches(passwrod)
//    }

}