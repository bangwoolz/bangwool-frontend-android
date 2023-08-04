package com.example.bangwool.ui.home

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.bangwool.databinding.ActivityTimerBinding
import java.util.Timer
import kotlin.concurrent.timer


class TimerActivity : AppCompatActivity() {
    lateinit var binding:ActivityTimerBinding
    private var time = 100 * 60 * 0 + 100 * 15
    private var timerTask : Timer? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTimerBinding.inflate(layoutInflater)
        binding.btnContinue.setOnClickListener{
            binding.btnContinue.visibility = View.INVISIBLE
            binding.btnClear.visibility = View.INVISIBLE
            binding.btnStop.visibility=View.VISIBLE
            binding.ivHappyTomato.visibility=View.GONE
            binding.ivStudyTomato.visibility=View.VISIBLE
            startTimer()    //타이머 작동
        }
        binding.btnClear.setOnClickListener{
            binding.btnClear.visibility = View.INVISIBLE
            binding.btnContinue.visibility = View.INVISIBLE
            binding.btnStart.visibility = View.VISIBLE
            clearTime()
        }
        binding.icXBtn.setOnClickListener{
            finish()
        }
        binding.btnStop.setOnClickListener{
            binding.btnContinue.visibility = View.VISIBLE
            binding.btnClear.visibility = View.VISIBLE
            binding.btnStop.visibility=View.INVISIBLE
            binding.ivHappyTomato.visibility=View.VISIBLE
            binding.ivStudyTomato.visibility=View.GONE
            stopTimer()
        }
        binding.btnStart.setOnClickListener{
            binding.btnStart.visibility = View.INVISIBLE
            binding.btnStop.visibility=View.VISIBLE
            binding.ivHappyTomato.visibility=View.GONE
            binding.ivStudyTomato.visibility=View.VISIBLE
            startTimer()    //타이머 작동
        }
        clearTime()
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
            val sec = time % 6000 / 100
            val milli = time % 100

            runOnUiThread {
                var upgradedMin = min.toString()
                if(min<10){
                    upgradedMin= "0"+min.toString()
                }
                var upgradedSec = sec.toString()
                if(sec<10){
                    upgradedSec= "0"+sec.toString()
                }
                if(milli==0){
                    binding.tvTimerMain?.text = "${upgradedMin} : ${upgradedSec}"
                    binding.progressbar.progress=time
                }

            }
        }
    }
    private fun stopTimer() {
        timerTask?.cancel()
    }
    private fun showTimeOnTimer() {
        val min = time / 6000
        val sec = time % 6000 / 100
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
    private fun clearTime() {
        time = 100 * 60 * 0 + 100 * 15
        showTimeOnTimer()
        binding.progressbar.max = time
        binding.progressbar.progress=time
    }
}