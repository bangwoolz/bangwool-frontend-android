package com.example.bangwool

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.bangwool.databinding.ActivityTimerSettingBinding

class TimerSettingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTimerSettingBinding
    private lateinit var timeDialogUtils: TimeDialogUtils

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTimerSettingBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        timeDialogUtils = TimeDialogUtils(this)

        binding.textViewWorkTime.setOnClickListener {
            timeDialogUtils.showWorkTimeDialog(binding.textViewWorkTime)
        }
    }
}
