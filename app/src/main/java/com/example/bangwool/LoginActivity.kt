package com.example.bangwool

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.view.View
import androidx.core.content.ContentProviderCompat.requireContext
import com.example.bangwool.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding
    val isLoadingComplete = false
    val delayMills = 300
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
    }

    private fun init() {
        binding.apply {
            loginIdEt.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    idTextInputLayout.hint = null
                }

                override fun afterTextChanged(s: Editable?) {
                    val id = s.toString().trim()

//                    if (id.isEmpty()){
//                        idTextInputLayout.error = "잘못된 이메일 형식이에요"
//                    }

                    if (isValidId(id)) {
                        loginStartBtn.setBackgroundColor(getColor(R.color.primary))
                        idTextInputLayout.error = null
                        idTextInputLayout.hint = null
                        loginStartBtn.setOnClickListener {
                            //error Icon 삭제
                            idTextInputLayout.setErrorIconDrawable(null)
                            // ProgressBar 보이도록 설정
                            loginProgressBar.visibility = View.VISIBLE
                            // 일정 시간(300ms) 후에 체크 이미지로 변경
                            Handler(Looper.getMainLooper()).postDelayed({
                                loginProgressBar.visibility = View.GONE
                                loginLoadingDone.visibility = View.VISIBLE
                                //로딩 완료 메세지
                                idTextInputLayout.error = "잠시후 로그인 창으로 이동합니다"
                                //에러메세지 색상 변경
                                idTextInputLayout.setErrorTextAppearance(R.style.CustomTextInputLayout)
                                idTextInputLayout.boxStrokeErrorColor = getColorStateList(R.color.androidDefault)
                            }, 300)


                            val intent = Intent(this@LoginActivity, PasswordActivity::class.java)
                            intent.putExtra("loginId", id)
                            startActivity(intent)
                        }
                    } else {
                        loginStartBtn.setBackgroundColor(getColor(R.color.gray))
                        idTextInputLayout.error = "잘못된 이메일 형식이에요"
                    }
                }

            })

            loginRegisterBtn.setOnClickListener {
                val i = Intent(this@LoginActivity, RegisterActivity::class.java)
                startActivity(i)
                finish()
            }
        }
    }

    private fun isValidId(email: String): Boolean {
        val pattern = Patterns.EMAIL_ADDRESS
        return pattern.matcher(email).matches()
    }

}