package com.example.bangwool

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.bangwool.databinding.ActivityTextviewallagreementsBinding

class textViewAllAgreementsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTextviewallagreementsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTextviewallagreementsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonClose.setOnClickListener {
            finish()
        }
    }




    override fun onBackPressed() {
        setResult(RESULT_OK)
        super.onBackPressed()
    }
}
