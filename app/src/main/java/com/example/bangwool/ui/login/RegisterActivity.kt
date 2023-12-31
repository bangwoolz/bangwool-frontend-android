package com.example.bangwool.ui.login

import android.graphics.PorterDuff
import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.bangwool.R
import com.example.bangwool.databinding.ActivityRegisterBinding
import com.example.bangwool.retrofit.ExistResponse
import com.example.bangwool.retrofit.RetrofitUtil
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.regex.Pattern

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private var isEmailValid = false
    private var isNameValid = false
    private var isNickNameValid = false
    private var isNicknameExist = true
    private var isPasswordValid = false
    private var isConfirmPasswordValid = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {


            textInputLayoutEmail.boxStrokeErrorColor = getColorStateList(R.color.secondary)
            textInputLayoutEmail.hint = ""
            editTextEmail.hint = "ex) banwol@google.com"
            editTextEmail.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
                if (!hasFocus && editTextEmail.text.isNullOrEmpty()) {
                    editTextEmail.hint = "ex) banwol@google.com"
                } else {
                    editTextEmail.hint = ""
                }

                if (hasFocus) {
                    editTextEmail.setTextColor(getColor(R.color.black))
                } else {
                    editTextEmail.setTextColor(getColor(R.color.gray_700))
                }
            }
            editTextEmail.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    validateEmail(s.toString())
                }

                override fun afterTextChanged(s: Editable?) {
                    updateButtonState()
                }
            })
            //이름 경고문 주시면 뜨개하갰습니다!!!
            textInputLayoutName.boxStrokeErrorColor = getColorStateList(R.color.secondary)
            textInputLayoutName.hint = ""
            editTextName.hint = "실명을 입력하세요"
            editTextName.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
                if (!hasFocus && editTextName.text.isNullOrEmpty()) {
                    editTextName.hint = "실명을 입력하세요"
                } else {
                    editTextName.hint = ""
                }

                if (hasFocus) {
                    editTextName.setTextColor(getColor(R.color.black))
                } else {
                    editTextName.setTextColor(getColor(R.color.gray_700))
                }
            }
            editTextName.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    validateName(s.toString())
                }
                override fun afterTextChanged(s: Editable?) {
                    updateButtonState()
                }
            })

            textInputLayoutNickname.boxStrokeErrorColor = getColorStateList(R.color.secondary)
            textInputLayoutNickname.hint = ""
            editTextNickname.hint = "5글자 이하로 입력해주세요"
            editTextNickname.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
                if (!hasFocus && editTextNickname.text.isNullOrEmpty()) {
                    editTextNickname.hint = "5글자 이하로 입력해주세요"
                } else {
                    editTextNickname.hint = ""
                }

                if (hasFocus) {
                    editTextNickname.setTextColor(getColor(R.color.black))
                } else {
                    editTextNickname.setTextColor(getColor(R.color.gray_700))
                }
            }
            editTextNickname.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(
                    s: CharSequence?,
                    start: Int,
                    before: Int,
                    count: Int
                ) {
                    isNicknameExist = true
                    validateNickname(s.toString())
                    updateConfirmButtonState()
                    updateButtonState()
                }

                override fun afterTextChanged(s: Editable?) {
                }
            })

            buttonDuplicateCheck.setOnClickListener {
                RetrofitUtil.getLoginRetrofit().ExistNickname(editTextNickname.text.toString()).enqueue(object: Callback<ExistResponse> {
                    override fun onResponse(
                        call: Call<ExistResponse>,
                        response: Response<ExistResponse>
                    ) {
                        if(response.isSuccessful){
                            if(!response.body()!!.exist){
                                binding.textInputLayoutNickname.error = "닉네임이 존재하지 않습니다."
                                updateNickNameErrorIcon(true)
                                binding.textInputLayoutNickname.setErrorTextColor(ColorStateList.valueOf(resources.getColor(com.example.bangwool.R.color.gray_600)))
                                binding.textInputLayoutNickname.isErrorEnabled = true
                                isNicknameExist = false
                                updateButtonState()

                            } else {
                                binding.textInputLayoutNickname.error = "        닉네임이 이미 존재합니다."
                                updateNickNameErrorIcon(false)
                                binding.textInputLayoutNickname.setErrorTextColor(ColorStateList.valueOf(resources.getColor(com.example.bangwool.R.color.errorRed)))
                                binding.textInputLayoutNickname.isErrorEnabled = true
                                binding.icErrorNickName.visibility = View.VISIBLE
                            }
                        } else {
                            isNicknameExist = true
                            updateButtonState()
                        }
                    }

                    override fun onFailure(call: Call<ExistResponse>, t: Throwable) {
                        TODO("Not yet implemented")
                    }

                })
            }

            textInputLayoutPassword.boxStrokeErrorColor = getColorStateList(R.color.secondary)
            textInputLayoutPassword.hint = ""
            editTextPassword.hint = "8~12자 사이로 입력해주세요"
            editTextPassword.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
                if (!hasFocus && editTextPassword.text.isNullOrEmpty()) {
                    editTextPassword.hint = "8~12자 사이로 입력해주세요"
                } else {
                    editTextPassword.hint = ""
                }

                if (hasFocus) {
                    editTextPassword.setTextColor(getColor(R.color.black))
                } else {
                    editTextPassword.setTextColor(getColor(R.color.gray_700))
                }
            }
            editTextPassword.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(
                    s: CharSequence?,
                    start: Int,
                    before: Int,
                    count: Int
                ) {
                    validatePassword(s.toString())
                }

                override fun afterTextChanged(s: Editable?) {
                    updateButtonState()
                }
            })

            textInputLayoutConfirmPassword.boxStrokeErrorColor =
                getColorStateList(R.color.secondary)
            textInputLayoutConfirmPassword.hint = ""
            editTextConfirmPassword.hint = "패스워드를 확인해주세요"
            editTextConfirmPassword.onFocusChangeListener =
                View.OnFocusChangeListener { _, hasFocus ->
                    if (!hasFocus && editTextConfirmPassword.text.isNullOrEmpty()) {
                        editTextConfirmPassword.hint = "패스워드를 확인해주세요"
                    } else {
                        editTextConfirmPassword.hint = ""
                    }

                    if (hasFocus) {
                        editTextConfirmPassword.setTextColor(getColor(R.color.black))
                    } else {
                        editTextConfirmPassword.setTextColor(getColor(R.color.gray_700))
                    }
                }
            editTextConfirmPassword.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(
                    s: CharSequence?,
                    start: Int,
                    before: Int,
                    count: Int
                ) {
                    validateConfirmPassword(
                        editTextPassword.text.toString(),
                        s.toString()
                    )
                }

                override fun afterTextChanged(s: Editable?) {
                    updateButtonState()
                }
            })

            editTextNickname.setOnEditorActionListener { textView, i, keyEvent ->
                editTextPassword.requestFocus()
            }
            editTextPassword.setOnEditorActionListener { textView, i, keyEvent ->
                editTextConfirmPassword.requestFocus()
            }
            editTextConfirmPassword.setOnEditorActionListener { textView, i, keyEvent ->
                if(buttonContinue.isEnabled) {
                    buttonContinue.performClick()
                    return@setOnEditorActionListener true
                }
                return@setOnEditorActionListener false
            }

            buttonContinue.setOnClickListener {
                val email = binding.textInputLayoutEmail.editText?.text.toString()
                val name = binding.textInputLayoutName.editText?.text.toString()
                val nickname = binding.textInputLayoutNickname.editText?.text.toString()
                val password = binding.textInputLayoutPassword.editText?.text.toString()

                //이메일 체크부분
                RetrofitUtil.getLoginRetrofit().ExistEmail(email).enqueue(object : Callback<ExistResponse> {
                    override fun onResponse(call: Call<ExistResponse>, response: Response<ExistResponse>) {
                        if (response.isSuccessful) {
                            //추가 부분
                            if (response.body()?.exist == true) {
                                // 이메일이 이미 가입된 이메일일 때 안넘어가고 오류 발생
                                binding.textInputLayoutEmail.error = "이미 가입된 이메일이에요."
                                binding.textInputLayoutEmail.isErrorEnabled = true
                                updateEndIcon(false)
                                isEmailValid = false
                            } else {
                                //아니라면...
                                val intent = Intent(this@RegisterActivity, TermsAgreeActivity::class.java)
                                intent.putExtra("email", email)
                                intent.putExtra("name", name)
                                intent.putExtra("nickname", nickname)
                                intent.putExtra("password", password)
                                //플래그 사용
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
                                startActivity(intent)
                            }
                        } else {
                            binding.textInputLayoutEmail.error = "이메일 중복 확인에 실패했어욥."
                        }
                    }

                    override fun onFailure(call: Call<ExistResponse>, t: Throwable) {
                        binding.textInputLayoutEmail.error = "이메일 중복 확인에 실패했어요."
                    }
                })
            }


//            buttonContinue.setOnClickListener {
//                val intent = Intent(this@RegisterActivity, TermsAgreeActivity::class.java)
//                intent.putExtra("email", textInputLayoutEmail.editText?.text.toString())
//                intent.putExtra("name", textInputLayoutName.editText?.text.toString())
//                intent.putExtra("nickname", textInputLayoutNickname.editText?.text.toString())
//                intent.putExtra("password", textInputLayoutPassword.editText?.text.toString())
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
//                startActivity(intent)
//            }

            buttonBack.setOnClickListener {
                finish()
            }
        }
    }

    private fun updateButtonState() {
        val isFormValid = areAllFieldsValid()

        binding.buttonContinue.isEnabled = isFormValid
        if (isFormValid) {
            val enabledColor =
                ContextCompat.getColorStateList(this@RegisterActivity, R.color.enabledColor)
            binding.buttonContinue.backgroundTintList = enabledColor

        } else {
            val disabledColor =
                ContextCompat.getColorStateList(this@RegisterActivity, R.color.disabledColor)
            binding.buttonContinue.backgroundTintList = disabledColor
        }
    }

    private fun updateConfirmButtonState() {
        val isFormValid = isNickNameValid
        binding.buttonDuplicateCheck.isEnabled = isFormValid
        val buttonBackground = if (isFormValid) {
            ContextCompat.getDrawable(this, R.drawable.enabled)
        } else {
            ContextCompat.getDrawable(this, R.drawable.disabled)
        }
        binding.buttonDuplicateCheck.background = buttonBackground
    }


    private fun areAllFieldsValid(): Boolean {
        return isEmailValid && isNameValid && isNickNameValid && isPasswordValid && isConfirmPasswordValid && !isNicknameExist
    }


    private fun validateEmail(email: String): Boolean {
        val emailPattern = Pattern.compile("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")

        val grayColor = ContextCompat.getColor(this, R.color.gray_600)
        if (email.isEmpty()) {
            binding.textInputLayoutEmail.error = "이메일을 입력하세요."
            binding.textInputLayoutEmail.isErrorEnabled = true
            updateEndIcon(false)
            isEmailValid = false
            return false
        } else if (!emailPattern.matcher(email).matches()) {
            binding.textInputLayoutEmail.error = "올바른 이메일 형식이 아니에요"
            binding.textInputLayoutEmail.isErrorEnabled = true
            updateEndIcon(false)
            isEmailValid = false
            return false
        } else {
            binding.textInputLayoutEmail.error = null
            binding.textInputLayoutEmail.isErrorEnabled = true
            binding.textInputLayoutConfirmPassword.boxStrokeErrorColor =
                ColorStateList.valueOf(grayColor)
            updateEndIcon(true)
            isEmailValid = true
            return true
        }
    }

    private fun validateName(name: String): Boolean {
        val namePattern = Pattern.compile("^[a-zA-Z0-9가-힣]{1,10}\$")
        if (name.isEmpty()) {
            binding.textInputLayoutName.error = "        이름을 입력하세요."
            binding.textInputLayoutName.isErrorEnabled = true
            binding.icErrorName.visibility = View.VISIBLE
            isNameValid = false
            return false
        } else if (!namePattern.matcher(name).matches()) {
            binding.textInputLayoutName.error = "        이름의 형식을 확인해 주세요"
            binding.textInputLayoutName.isErrorEnabled = true
            binding.icErrorName.visibility = View.VISIBLE
            isNameValid = false
            return false
        } else if (name.length > 10) {
            binding.textInputLayoutName.error = "        이름은 10글자 이하여야해요."
            binding.textInputLayoutName.isErrorEnabled = true
            binding.icErrorName.visibility = View.VISIBLE
            isNameValid = false
            return false
        } else {
            binding.textInputLayoutName.error = null
            binding.textInputLayoutName.isErrorEnabled = false
            binding.icErrorName.visibility = View.GONE
            isNameValid = true
            return true
        }
    }

    private fun validateNickname(nickname: String): Boolean {
        val nicknamePattern = Pattern.compile("^[a-zA-Z0-9가-힣]{1,5}\$")
        if (nickname.isEmpty()) {
            updateNickNameErrorIcon(false)
            binding.textInputLayoutNickname.setErrorTextColor(ColorStateList.valueOf(resources.getColor(com.example.bangwool.R.color.errorRed)))
            binding.textInputLayoutNickname.error = "        닉네임을 입력하세요."
            binding.textInputLayoutNickname.isErrorEnabled = true
            binding.icErrorNickName.visibility = View.VISIBLE
            isNickNameValid = false
            return false
        } else if (!nicknamePattern.matcher(nickname).matches()) {
            updateNickNameErrorIcon(false)
            binding.textInputLayoutNickname.setErrorTextColor(ColorStateList.valueOf(resources.getColor(com.example.bangwool.R.color.errorRed)))
            binding.textInputLayoutNickname.error = "        닉네임 형식을 확인해주세요."
            binding.textInputLayoutNickname.isErrorEnabled = true
            binding.icErrorNickName.visibility = View.VISIBLE
            isNickNameValid = false
            return false
        } else if (nickname.length > 5) {
            updateNickNameErrorIcon(false)
            binding.textInputLayoutNickname.setErrorTextColor(ColorStateList.valueOf(resources.getColor(com.example.bangwool.R.color.errorRed)))
            binding.textInputLayoutNickname.error = "        닉네임은 5글자 이하여야해요."
            binding.textInputLayoutNickname.isErrorEnabled = true
            binding.icErrorNickName.visibility = View.VISIBLE
            isNickNameValid = false
            return false
        } else {
            binding.textInputLayoutNickname.error = null
            binding.textInputLayoutNickname.isErrorEnabled = false
            binding.icErrorNickName.visibility = View.GONE
            isNickNameValid = true
            return true
        }
    }

    private fun validatePassword(password: String): Boolean {
        val passwordPattern =
            Pattern.compile("(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,12}")

        if (password.isEmpty()) {
            binding.textInputLayoutPassword.error = "        패스워드를 입력하세요."
            binding.textInputLayoutPassword.isErrorEnabled = true
            binding.icErrorPassword.visibility = View.VISIBLE
            isPasswordValid = false
            return false
        } else if (!passwordPattern.matcher(password).matches()) {
            binding.textInputLayoutPassword.error = "        패스워드 조건을 확인해주세요(8-12글자 사이)"
            binding.textInputLayoutPassword.isErrorEnabled = true
            binding.icErrorPassword.visibility = View.VISIBLE
            isPasswordValid = false
            return false
        } else {
            binding.textInputLayoutPassword.error = null
            binding.textInputLayoutPassword.isErrorEnabled = false
            binding.icErrorPassword.visibility = View.GONE
            isPasswordValid = true
            return true
        }
    }

    private fun validateConfirmPassword(password: String, confirmPassword: String): Boolean {
        if (confirmPassword != password) {
            binding.textInputLayoutConfirmPassword.error = "        패스워드가 달라요"
            binding.textInputLayoutConfirmPassword.isErrorEnabled = true
            binding.icErrorConfirmPassword.visibility = View.VISIBLE
            isConfirmPasswordValid = false
            return false
        } else {
            binding.textInputLayoutConfirmPassword.error = null
            binding.textInputLayoutConfirmPassword.isErrorEnabled = false
            binding.icErrorConfirmPassword.visibility = View.GONE
            isConfirmPasswordValid = true
            return true
        }
    }


    private fun updateEndIcon(isValid: Boolean) {
        val endIconDrawable =
            if (isValid) {
                ContextCompat.getDrawable(this, R.drawable.round_check_24)
            } else {
                ContextCompat.getDrawable(this, R.drawable.ic_error_circle_outline)
            }

        if (isValid) {
            val tintColor = ContextCompat.getColor(this, R.color.gray_700)
            endIconDrawable?.setColorFilter(tintColor, PorterDuff.Mode.SRC_IN)
        } else {
            val tintColor = ContextCompat.getColor(this, R.color.secondary)
            endIconDrawable?.setColorFilter(tintColor, PorterDuff.Mode.SRC_IN)
        }

        binding.textInputLayoutEmail.setErrorIconDrawable(endIconDrawable)
    }

    private fun updateNickNameErrorIcon(isValid: Boolean) {
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

        binding.textInputLayoutNickname.setErrorIconDrawable(endIconDrawable)
    }
}