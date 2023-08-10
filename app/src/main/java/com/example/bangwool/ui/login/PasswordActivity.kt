package com.example.bangwool.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import com.example.bangwool.MainActivity
import com.example.bangwool.R
import com.example.bangwool.databinding.ActivityPasswordBinding
import com.example.bangwool.retrofit.AuthLoginRequest
import com.example.bangwool.retrofit.RetrofitUtil
import com.example.bangwool.retrofit.TokenResponse
import com.example.bangwool.retrofit.saveAccessToken
import com.example.bangwool.retrofit.savePassword
import com.example.bangwool.retrofit.saveUserId
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PasswordActivity : AppCompatActivity() {
    lateinit var binding: ActivityPasswordBinding
    private var userId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    private fun init() {
        userId = intent.getStringExtra("loginId")
        binding.apply {
            backBtn.setOnClickListener {
                finish()
            }
            binding.passwordFindPwBtn.setOnClickListener {
                val intent = Intent(this@PasswordActivity, FindPasswordActivity::class.java)
                startActivity(intent)
            }

            loginBtn.setBackgroundResource(R.drawable.long_normal_btn)
            loginBtn.backgroundTintList = getColorStateList(R.color.gray_300)

            passwordEt.setOnFocusChangeListener { _, hasFocus ->
                if (hasFocus) {
                    pwTextInputLayout.hint = null
                    pwTextInputLayout.requestFocus()
                } else {
                    if (passwordEt.text.isNullOrEmpty()) {
                        pwTextInputLayout.hint = "     비밀번호를 입력하세요."
                    } else {
                        pwTextInputLayout.hint = null
                    }
                }
            }

            pwTextInputLayout.error = null
            passwordEt.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    checkPassword(s.toString().trim())
                }

                override fun afterTextChanged(s: Editable?) {
                }
            })

        }
    }

    private fun ActivityPasswordBinding.checkPassword(pw: String) {
        if (pw.isEmpty()) {
            loginBtn.setBackgroundResource(R.drawable.long_normal_btn)
            loginBtn.backgroundTintList = getColorStateList(R.color.gray_300)
//            loginIcErrorEmail.visibility = View.VISIBLE
//            pwTextInputLayout.error = "비밀번호를 입력하세요." // -> 피그마엔 없음
        } else {
            loginBtn.setBackgroundResource(R.drawable.long_normal_btn)
            loginBtn.backgroundTintList = getColorStateList(R.color.primary)

            loginBtn.setOnClickListener {
                binding.root.setOnTouchListener { _, _ -> true }
                requestAuthLogin()
            }
        }
    }

    private fun ActivityPasswordBinding.requestAuthLogin() {
        val authLoginRequest = AuthLoginRequest(userId!!, passwordEt.text.toString())
        loginIcErrorEmail.visibility = View.GONE
        pwTextInputLayout.isErrorEnabled = false
        RetrofitUtil.getLoginRetrofit().AuthLogin(authLoginRequest).enqueue(object :
            Callback<TokenResponse> {
            override fun onResponse(
                call: Call<TokenResponse>,
                response: Response<TokenResponse>
            ) {
                if (response.isSuccessful) {
                    val token = response.body()!!.token
                    saveAccessToken(this@PasswordActivity, token)
                    RetrofitUtil.setAccessToken(token)
                    saveUserId(this@PasswordActivity,userId!!)
                    savePassword(this@PasswordActivity,passwordEt.text.toString())
                    pwTextInputLayout.error = null
                    pwTextInputLayout.isErrorEnabled = false
                    loginIcErrorEmail.visibility = View.GONE
    //              pwTextInputLayout.error = "비밀번호 동일함"
                    val i = Intent(this@PasswordActivity, MainActivity::class.java)
                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)//이미 있는거 수정
                    startActivity(i)
                    Toast.makeText(this@PasswordActivity, "로그인 성공", Toast.LENGTH_SHORT).show()
                    finish()
                    if (LoginActivity.activity != null)
                        LoginActivity.activity!!.finish()
                } else {
                    loginIcErrorEmail.visibility = View.VISIBLE
                    pwTextInputLayout.isErrorEnabled = true
                    pwTextInputLayout.error = "        비밀번호가 달라요. 다시 입력해주세요"
                }
            }

            override fun onFailure(call: Call<TokenResponse>, t: Throwable) {
                loginIcErrorEmail.visibility = View.VISIBLE
                pwTextInputLayout.isErrorEnabled = true
                pwTextInputLayout.error = "        비밀번호가 달라요. 다시 입력해주세요"
            }

        })
    }

}