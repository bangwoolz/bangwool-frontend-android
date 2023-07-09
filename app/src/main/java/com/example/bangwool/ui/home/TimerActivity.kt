package com.example.bangwool.ui.home

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.bangwool.databinding.ActivityTimerBinding
import java.util.Timer
import kotlin.concurrent.timer


class TimerActivity : AppCompatActivity() {
    lateinit var binding:ActivityTimerBinding
    private var time = 2000
    private var timerTask : Timer? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTimerBinding.inflate(layoutInflater)
        binding.himan.setOnClickListener{
            startTimer()    //타이머 작동
        }
        binding.xBtn.setOnClickListener{
            finish()
        }
        setContentView(binding.root)
    }
    //타이머 작동
    private fun startTimer() {
        timerTask = timer(period = 10) {
            time--
            if(time==0){
                cancel()
            }
            val sec = time / 100
            val milli = time % 100

            runOnUiThread {
                binding.himan?.text = "${sec} : ${milli}"
            }
        }
    }
}