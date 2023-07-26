package com.example.bangwool

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.util.Patterns
import android.view.View
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
        val userId = intent.getStringExtra("loginId")
        Log.d("loginId", userId.toString())
        binding.apply {
            backBtn.setOnClickListener {
                finish()
            }
            binding.passwordFindPwBtn.setOnClickListener {
                val intent = Intent(this@PasswordActivity, FindPasswordActivity::class.java)
                startActivity(intent)
                finish()
            }



            loginBtn.setBackgroundResource(R.drawable.long_normal_btn)
            loginBtn.backgroundTintList = getColorStateList(R.color.gray_300)

            pwTextInputLayout.error = null
            passwordEt.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

                override fun afterTextChanged(s: Editable?) {
                    val pw = s.toString()

                    if (pw.isEmpty()) {
                        loginBtn.setBackgroundResource(R.drawable.long_normal_btn)
                        loginBtn.backgroundTintList = getColorStateList(R.color.gray_300)

//                        pwTextInputLayout.error = "비밀번호를 입력하세요." // -> 피그마엔 없음
                    } else {
                        loginBtn.setBackgroundResource(R.drawable.long_normal_btn)
                        loginBtn.backgroundTintList = getColorStateList(R.color.primary)

                        loginBtn.setOnClickListener {
                            if (pw.equals(uesr_password)) {
                                pwTextInputLayout.error = null
//                                pwTextInputLayout.error = "비밀번호 동일함"
                                val i = Intent(this@PasswordActivity, MainActivity::class.java)
                                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                                startActivity(i)
                                finish()
                                if(LoginActivity.activity != null)
                                    LoginActivity.activity!!.finish()
                            } else {
                                loginIcErrorPassword.visibility = View.VISIBLE
                                pwTextInputLayout.error = "    비밀번호가 달라요.다시 입력해주세요"
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