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

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

                override fun afterTextChanged(s: Editable?) {
                    val id = s.toString().trim()

//                    if (id.isEmpty()){
//                        idTextInputLayout.error = "잘못된 이메일 형식이에요"
//                    }

                    if (isValidId(id)) {
                        loginStartBtn.setBackgroundColor(getColor(R.color.bangwol_red))
                        idTextInputLayout.error = null
                        loginStartBtn.setOnClickListener {
                            val intent = Intent(this@LoginActivity, PasswordActivity::class.java)
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