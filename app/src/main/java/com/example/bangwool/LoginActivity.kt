package com.example.bangwool

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import androidx.core.content.ContentProviderCompat.requireContext
import com.example.bangwool.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding
    val isLoadingComplete = false
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
                        loginStartBtn.setBackgroundColor(getColor(R.color.bangwol_red))
                        idTextInputLayout.error = null
                        idTextInputLayout.hint = null
                        loginStartBtn.setOnClickListener {
                            idTextInputLayout.error = "잠시후 로그인 창으로 이동합니다"
                            //에러메세지 색상 변경
                            idTextInputLayout.setErrorTextAppearance(R.style.CustomTextInputLayout)
                            idTextInputLayout.boxStrokeErrorColor = getColorStateList(R.color.androidDefault)
                            //error Icon 변경
                            if (isLoadingComplete){
//                                idTextInputLayout.setErrorIconDrawable(R.drawable.round_check_24)
                                idTextInputLayout.setErrorIconDrawable(null)
                            } else{
                                //로딩 중 이미지로 변경해야함
//                                idTextInputLayout.setErrorIconDrawable(R.drawable.round_arrow_back_ios_24)
                                idTextInputLayout.setErrorIconDrawable(null)
                            }
                            //
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
        }
    }

    private fun isValidId(email: String): Boolean {
        val pattern = Patterns.EMAIL_ADDRESS
        return pattern.matcher(email).matches()
    }

}