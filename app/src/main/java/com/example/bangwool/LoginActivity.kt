package com.example.bangwool

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.PorterDuff
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.service.notification.Condition.isValidId
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.view.MotionEvent
import android.view.View
import androidx.core.content.ContextCompat
import com.example.bangwool.databinding.ActivityLoginBinding
import java.util.regex.Pattern

class LoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding
    private var isIdValid = false

    companion object co {
        var activity: LoginActivity? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        co.activity = this

        init()
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun init() {
        binding.apply {
            loginCl.requestFocus()
            idTextInputLayout.hint = null
            idTextInputLayout.setErrorIconDrawable(null)
            idTextInputLayout.boxStrokeErrorColor = getColorStateList(R.color.secondary)
            loginIdEt.hint = "ex) banwol@gmail.com"

            loginIdEt.setOnFocusChangeListener { _, hasFocus ->
                if (!hasFocus && loginIdEt.text.isNullOrEmpty()) {
                    loginIdEt.hint = "ex) banwol@gmail.com"
                } else {
                    loginIdEt.hint = null
                }

                if (hasFocus) {
                    loginIdEt.setTextColor(getColor(R.color.black))
                } else {
                    loginIdEt.setTextColor(getColor(R.color.gray_700))
                }
            }

            loginIdEt.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    validateId(s.toString().trim())
                    updateButtonState()
                }

                override fun afterTextChanged(s: Editable?) {
                }
            })

            loginStartBtn.setBackgroundResource(R.drawable.long_normal_btn)
            loginStartBtn.backgroundTintList = getColorStateList(R.color.gray_300)
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
                    idTextInputLayout.boxStrokeErrorColor =
                        getColorStateList(R.color.androidDefault)
                    val intent =
                        Intent(this@LoginActivity, PasswordActivity::class.java)
                    val id = loginIdEt.text
                    intent.putExtra("loginId", id)
                    startActivity(intent)
                    idTextInputLayout.error = null;
                    loginLoadingDone.visibility = View.GONE
                }, 2000)
            }

            loginRegisterBtn.setOnClickListener {
                val i = Intent(this@LoginActivity, RegisterActivity::class.java)
                startActivity(i)
            }
        }
    }

//    private fun validateId(id: String) {
//        if (isValidId(id)) {
//            updateEndIcon(true)
//            binding.apply {
//                loginIcErrorEmail.visibility = View.GONE
//                loginStartBtn.setBackgroundResource(R.drawable.long_normal_btn)
//                idTextInputLayout.error = null
//                idTextInputLayout.hint = null
//            }
//
//        } else {
//            updateEndIcon(false)
//            binding.apply {
//                loginIcErrorEmail.visibility = View.VISIBLE
//                loginStartBtn.setBackgroundResource(R.drawable.long_normal_btn)
//                loginStartBtn.backgroundTintList = getColorStateList(R.color.gray_300)
//                idTextInputLayout.error = "     잘못된 이메일 형식이에요"
//            }
//        }
//    }

    private fun validateId(email: String): Boolean {
        val emailPattern = Pattern.compile("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")
        val grayColor = ContextCompat.getColor(this, R.color.gray_300)
        if (email.isEmpty()) {
            binding.idTextInputLayout.error = "          이메일을 입력하세요."
            binding.idTextInputLayout.isErrorEnabled = true
            binding.loginIcErrorEmail.visibility = View.VISIBLE
            updateEndIcon(false)
            isIdValid = false
            return false
        } else if (!emailPattern.matcher(email).matches()) {
            binding.idTextInputLayout.error = "          올바른 이메일 형식이 아니에요"
            binding.idTextInputLayout.isErrorEnabled = true
            binding.loginIcErrorEmail.visibility = View.VISIBLE
            updateEndIcon(false)
            isIdValid = false
            return false
        } else {
            binding.idTextInputLayout.error = null
            binding.idTextInputLayout.isErrorEnabled = false
            binding.idTextInputLayout.boxStrokeErrorColor = ColorStateList.valueOf(grayColor)
            binding.loginIcErrorEmail.visibility = View.GONE
            updateEndIcon(true)
            isIdValid = true
            return true
        }
    }

    private fun updateEndIcon(isValid: Boolean) {
        val endIconDrawable =
            if (isValid) {
                ContextCompat.getDrawable(this, R.drawable.round_check_24)
            } else {
                null
            }

        if (isValid) {
            val tintColor = ContextCompat.getColor(this, R.color.gray_700)
            endIconDrawable?.setColorFilter(tintColor, PorterDuff.Mode.SRC_IN)
        } else {
            val tintColor = ContextCompat.getColor(this, R.color.secondary)
            endIconDrawable?.setColorFilter(tintColor, PorterDuff.Mode.SRC_IN)
        }
        binding.idTextInputLayout.setErrorIconDrawable(endIconDrawable)
    }

    private fun updateButtonState() {
        val isFormValid = isIdValid

        binding.loginStartBtn.isEnabled = isFormValid
        if (isFormValid) {
            val enabledColor =
                ContextCompat.getColorStateList(this@LoginActivity, R.color.enabledColor)
            binding.loginStartBtn.backgroundTintList = enabledColor

        } else {
            val disabledColor =
                ContextCompat.getColorStateList(this@LoginActivity, R.color.disabledColor)
            binding.loginStartBtn.backgroundTintList = disabledColor
        }
    }

}