package com.example.bangwool.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.bangwool.R
import com.example.bangwool.databinding.ActivityTermsagreeBinding
import com.example.bangwool.retrofit.MemberSignUpRequest
import com.example.bangwool.retrofit.MemberSignUpResponse
import com.example.bangwool.retrofit.RetrofitUtil
import retrofit2.Call
import retrofit2.Response

class TermsAgreeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTermsagreeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTermsagreeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupViews()
        updateButtonState()
    }

    private fun setupViews() {
        with(binding) {
            ButtonPrivacyPolicy.setOnClickListener {
                navigateToPrivacyPolicy()
            }
            textViewPrivacyPolicy.setOnClickListener {
                navigateToPrivacyPolicy()
            }
            ButtonViewAllAgreements.setOnClickListener {
                navigateToAllAgreements()
            }
            textViewAllAgreements.setOnClickListener {
                navigateToAllAgreements()
            }
            checkBoxAllAgreements.setOnCheckedChangeListener { _, isChecked ->
                checkBoxAllAgreementsOnCheckedChangeListener(isChecked)
            }

            checkBoxPrivacyPolicy.setOnCheckedChangeListener { _, isChecked ->
                //remove checkbox's checked change listener
                checkBoxAllAgreements.setOnCheckedChangeListener { _, isChecked ->
                }
                checkBoxAllAgreements.isChecked = isChecked && checkBoxTermsOfUse.isChecked
                //reallocate checkbox's checked change listener
                checkBoxAllAgreements.setOnCheckedChangeListener { _, isChecked ->
                    checkBoxAllAgreementsOnCheckedChangeListener(isChecked)
                }
                updateButtonState()
            }

            checkBoxTermsOfUse.setOnCheckedChangeListener { _, isChecked ->
                checkBoxAllAgreements.setOnCheckedChangeListener { _, isChecked ->
                    //remove checkbox's checked change listener
                }
                checkBoxAllAgreements.isChecked = isChecked && checkBoxPrivacyPolicy.isChecked
                checkBoxAllAgreements.setOnCheckedChangeListener { _, isChecked ->
                    checkBoxAllAgreementsOnCheckedChangeListener(isChecked)
                }
                updateButtonState()
            }

            buttonBack.setOnClickListener {
                finish()
            }

            buttonContinue.setOnClickListener {
                requestMemberSignUp()
            }
        }
    }

    private fun requestMemberSignUp() {
        val email = intent.getStringExtra("email")
        val name = intent.getStringExtra("name")
        val nickname = intent.getStringExtra("nickname")
        val password = intent.getStringExtra("password")
        Log.d("qwerty123","email : " + email + " name : " + name + " nickname " + nickname + " password : " + password)
        val memberSignUpRequest = MemberSignUpRequest(email!!, name!!, nickname!!, password!!)

        val retrofit = RetrofitUtil.getLoginRetrofit()
        retrofit.MemberSignUp(memberSignUpRequest)
            .enqueue(object : retrofit2.Callback<MemberSignUpResponse> {
                override fun onResponse(
                    call: Call<MemberSignUpResponse>,
                    response: Response<MemberSignUpResponse>
                ) {
                    if (response.isSuccessful) {
                        val id = response.body()!!
                        Toast.makeText(
                            this@TermsAgreeActivity,
                            "id : " + id.toString(),
                            Toast.LENGTH_SHORT
                        )
                            .show()
                        val i = Intent(this@TermsAgreeActivity, LoginActivity::class.java)
                        i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(i)
                    } else {
                        Toast.makeText(this@TermsAgreeActivity, "회원가입 실패", Toast.LENGTH_SHORT)
                            .show()
                    }
                }

                override fun onFailure(call: Call<MemberSignUpResponse>, t: Throwable) {
                    Toast.makeText(this@TermsAgreeActivity, "회원가입 실패", Toast.LENGTH_SHORT)
                        .show()
                }
            })
    }

    fun checkBoxAllAgreementsOnCheckedChangeListener(isChecked: Boolean) {
        binding.checkBoxPrivacyPolicy.isChecked = isChecked
        binding.checkBoxTermsOfUse.isChecked = isChecked
        updateButtonState()
    }

    private fun updateButtonState() {
        val isAllAgreementsChecked = binding.checkBoxAllAgreements.isChecked
        val isPrivacyPolicyChecked = binding.checkBoxPrivacyPolicy.isChecked
        val isTermsOfUseChecked = binding.checkBoxTermsOfUse.isChecked
        val isFormValid = isAllAgreementsChecked && isPrivacyPolicyChecked && isTermsOfUseChecked

        with(binding.buttonContinue) {
            isEnabled = isFormValid
            backgroundTintList = if (isFormValid) {
                ContextCompat.getColorStateList(this@TermsAgreeActivity, R.color.enabledColor)
            } else {
                ContextCompat.getColorStateList(this@TermsAgreeActivity, R.color.disabledColor)
            }
        }
    }

    private fun navigateToPrivacyPolicy() {
        val intent = Intent(this, PrivacyPolicyActivity::class.java)
        startActivity(intent)
    }

    private fun navigateToAllAgreements() {
        val intent = Intent(this, textViewAllAgreementsActivity::class.java)
        startActivity(intent)
    }
}
//완전히 끝났을때