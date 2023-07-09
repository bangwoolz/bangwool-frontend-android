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
        binding.btnContinue.setOnClickListener{
            startTimer()    //타이머 작동
        }
        binding.btnClear.setOnClickListener{
            stopTimer()

        }
        binding.icXBtn.setOnClickListener{
            finish()
        }
        val min = time / 6000
        val sec = time / 100
        var upgradedMin = min.toString()
        if(min<10){
            upgradedMin= "0"+min.toString()
        }
        var upgradedSec = sec.toString()
        if(sec<10){
            upgradedSec= "0"+sec.toString()
        }
        binding.tvTimerMain?.text = "${upgradedMin} : ${upgradedSec}"
        setContentView(binding.root)
    }
    //타이머 작동
    private fun startTimer() {
        timerTask = timer(period = 10) {
            if(time<=0){
                cancel()
            }
            time--
            val min = time / 6000
            val sec = time / 100
            val milli = time % 100

            runOnUiThread {
//                var upgradedMilli = milli.toString()
//                if(milli<10){
//                    upgradedMilli= milli.toString()+"0"
//                }
                var upgradedMin = min.toString()
                if(min<10){
                    upgradedMin= "0"+min.toString()
                }
                var upgradedSec = sec.toString()
                if(sec<10){
                    upgradedSec= "0"+sec.toString()
                }
                binding.tvTimerMain?.text = "${upgradedMin} : ${upgradedSec}"
            }
        }
    }
    private fun stopTimer() {
        timerTask?.cancel()
    }
}