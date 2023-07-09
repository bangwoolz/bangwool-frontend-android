package com.example.bangwool.ui.home

import android.os.Bundle
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
        binding.tvTimerMain.setOnClickListener{
            startTimer()    //타이머 작동
        }
        binding.icXBtn.setOnClickListener{
            finish()
        }
        val sec = time / 100
        binding.tvTimerMain?.text = "${sec} : 00"
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
                var upgradedMilli = milli.toString()
                if(milli<10){
                    upgradedMilli= milli.toString()+"0"
                }
                binding.tvTimerMain?.text = "${sec} : ${upgradedMilli}"
            }
        }
    }
}