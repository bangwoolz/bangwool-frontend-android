package com.example.bangwool.ui.login

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.PorterDuff
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.service.notification.Condition.isValidId
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.util.Patterns
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.bangwool.MainActivity
import com.example.bangwool.R
import com.example.bangwool.databinding.ActivityLoginBinding
import com.example.bangwool.retrofit.AuthLoginRequest
import com.example.bangwool.retrofit.ExistResponse
import com.example.bangwool.retrofit.KakaoLoginRequest
import com.example.bangwool.retrofit.OAuthTokenResponse
import com.example.bangwool.retrofit.RetrofitUtil
import com.example.bangwool.retrofit.TokenResponse
import com.example.bangwool.retrofit.getPassword
import com.example.bangwool.retrofit.getUserId
import com.example.bangwool.retrofit.removePassword
import com.example.bangwool.retrofit.removeUserId
import com.example.bangwool.retrofit.saveAccessToken
import com.example.bangwool.retrofit.savePassword
import com.example.bangwool.retrofit.saveUserId
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.common.util.Utility
import com.kakao.sdk.user.UserApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
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
//        val userId = getUserId(this)
//        val password = getPassword(this)
//        if(!(userId.equals("0"))&&!(password.equals("0"))){
//            val authLoginRequest = AuthLoginRequest(userId, password)
//            RetrofitUtil.getLoginRetrofit().AuthLogin(authLoginRequest).enqueue(object :
//                Callback<TokenResponse> {
//                override fun onResponse(
//                    call: Call<TokenResponse>,
//                    response: Response<TokenResponse>
//                ) {
//                    if (response.isSuccessful) {
//                        val token = response.body()!!.token
//                        saveAccessToken(this@LoginActivity, token)
//                        RetrofitUtil.setAccessToken(token)
//                        val i = Intent(this@LoginActivity, MainActivity::class.java)
//                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)//이미 있는거 수정
//                        startActivity(i)
//                        Toast.makeText(this@LoginActivity, "로그인 성공", Toast.LENGTH_SHORT).show()
//                        finish()
//                        if (LoginActivity.activity != null)
//                            LoginActivity.activity!!.finish()
//                    } else {
//                        removeUserId(this@LoginActivity)
//                        removePassword(this@LoginActivity)
//                    }
//                }
//
//                override fun onFailure(call: Call<TokenResponse>, t: Throwable) {
//
//                }
//
//            })
//        }
        co.activity = this
        init()
    }

    private fun blockTouch() {
        window.setFlags(
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
        )
    }

    // 화면 터치 재활성화
    private fun unblockTouch() {
        window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
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

            loginIdEt.setOnEditorActionListener { textView, i, keyEvent ->
                if(loginStartBtn.isEnabled)
                    loginStartBtn.performClick()
                return@setOnEditorActionListener true
            }

            loginStartBtn.setBackgroundResource(R.drawable.long_normal_btn)
            loginStartBtn.backgroundTintList = getColorStateList(R.color.gray_300)
            loginStartBtn.setOnClickListener {
                //터치 막기
                blockTouch()
                checkEmail()
            }

            loginRegisterBtn.setOnClickListener {
                val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
                startActivity(intent)
            }


            btnKakaoLogin.setOnClickListener {
                sdk()
            }
        }
    }

    private fun sdk() {
        // 카카오계정으로 로그인 공통 callback 구성
        // 카카오톡으로 로그인 할 수 없어 카카오계정으로 로그인할 경우 사용됨

        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (error != null) {
                Log.e("LOGIN", "카카오계정으로 로그인 실패", error)
            } else if (token != null) {
                Log.i("LOGIN", "카카오계정으로 로그인 성공 ${token.accessToken}")
                afterGetKaKaoToken(token.accessToken)
            }
        }

        // 카카오톡이 설치되어 있으면 카카오톡으로 로그인, 아니면 카카오계정으로 로그인
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(this@LoginActivity)) {
            UserApiClient.instance.loginWithKakaoTalk(this@LoginActivity) { token, error ->
                if (error != null) {
                    Log.e("LOGIN", "카카오톡으로 로그인 실패", error)

                    // 사용자가 카카오톡 설치 후 디바이스 권한 요청 화면에서 로그인을 취소한 경우,
                    // 의도적인 로그인 취소로 보고 카카오계정으로 로그인 시도 없이 로그인 취소로 처리 (예: 뒤로 가기)
                    if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                        return@loginWithKakaoTalk
                    }

                    // 카카오톡에 연결된 카카오계정이 없는 경우, 카카오계정으로 로그인 시도
                    UserApiClient.instance.loginWithKakaoAccount(
                        this@LoginActivity,
                        callback = callback
                    )
                } else if (token != null) {
                    Log.i("LOGIN", "카카오톡으로 로그인 성공 ${token.accessToken}")
                    afterGetKaKaoToken(token.accessToken)
                }
            }
        } else {
            UserApiClient.instance.loginWithKakaoAccount(
                this@LoginActivity,
                callback = callback
            )
        }
    }

    private fun afterGetKaKaoToken(kakaoToken: String){

        val kakaoLoginRequest = KakaoLoginRequest(kakaoToken)
        RetrofitUtil.getLoginRetrofit().KakaoLogin(kakaoLoginRequest).enqueue(object: Callback<OAuthTokenResponse>{
            override fun onResponse(
                call: Call<OAuthTokenResponse>,
                response: Response<OAuthTokenResponse>
            ) {
                if(response.isSuccessful){
                    val token = response.body()!!.token
                    Log.d("LOGIN", token)
                    saveAccessToken(this@LoginActivity, token)
                    RetrofitUtil.setAccessToken(token)

                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
                    startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                    finish()
                }
            }

            override fun onFailure(call: Call<OAuthTokenResponse>, t: Throwable) {
                Log.d("LOGIN", t.message.toString())

            }

        })
    }


    private fun checkEmail() {
        binding.apply {
            //error Icon 삭제
            idTextInputLayout.setErrorIconDrawable(null)
            // ProgressBar 보이도록 설정
            loginProgressBar.visibility = View.VISIBLE

            RetrofitUtil.getLoginRetrofit().ExistEmail(loginIdEt.text.toString())
                .enqueue(object : Callback<ExistResponse> {
                    override fun onResponse(
                        call: Call<ExistResponse>,
                        response: Response<ExistResponse>
                    ) {
                        if (response.isSuccessful) {
                            if (response.body()!!.exist) {
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
                                    Handler(Looper.getMainLooper()).postDelayed({
                                        val intent =
                                            Intent(this@LoginActivity, PasswordActivity::class.java)
                                        val id = loginIdEt.text.toString()
                                        intent.putExtra("loginId", id)
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
                                        startActivity(intent)
                                        loginCl.requestFocus()
                                        idTextInputLayout.error = null
                                        idTextInputLayout.isErrorEnabled = false
                                        loginLoadingDone.visibility = View.GONE

                                        unblockTouch()

                                    }, 1000)
                                }, 2000)
                            } else {
                                loginProgressBar.visibility = View.GONE
                                Toast.makeText(
                                    this@LoginActivity,
                                    "가입되지 않은 이메일",
                                    Toast.LENGTH_SHORT
                                )
                                    .show()
                                unblockTouch()
                            }
                        }
                    }

                    override fun onFailure(call: Call<ExistResponse>, t: Throwable) {
                        Toast.makeText(this@LoginActivity, "네트워크 오류", Toast.LENGTH_SHORT).show()
                        unblockTouch()
                    }

                })


        }
    }

    private fun validateId(email: String): Boolean {
        val emailPattern = Pattern.compile("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")
        val grayColor = ContextCompat.getColor(this, R.color.gray_300)
        if (email.isEmpty()) {
            binding.idTextInputLayout.error = "        이메일을 입력하세요."
            binding.idTextInputLayout.isErrorEnabled = true
            binding.loginIcErrorEmail.visibility = View.VISIBLE
            updateEndIcon(false)
            isIdValid = false
            return false
        } else if (!emailPattern.matcher(email).matches()) {
            binding.idTextInputLayout.error = "        올바른 이메일 형식이 아니에요"
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