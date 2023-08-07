package com.example.bangwool.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
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


        //필요한 곳에 옮겨서 사용 ㄱㄱ
        val Id = intent.getStringExtra("id")!!.toInt()
        val name = intent.getStringExtra("name")
        val color = intent.getStringExtra("color")
        val workHour = intent.getStringExtra("workHour")!!.toInt()
        val workMin = intent.getStringExtra("workMin")!!.toInt()
        val restTime = intent.getStringExtra("restTime")!!.toInt()

        Log.d("getStringExtra", Id.toString()+name+color+workHour.toString()+workMin.toString()+restTime.toString())


        binding.btnContinue.setOnClickListener{
            binding.btnContinue.visibility = View.INVISIBLE
            binding.btnClear.visibility = View.INVISIBLE
            binding.btnStop.visibility=View.VISIBLE
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
            stopTimer()
        }
        binding.btnStart.setOnClickListener{
            binding.btnStart.visibility = View.INVISIBLE
            binding.btnStop.visibility=View.VISIBLE
            startTimer()    //타이머 작동
        }
        binding.icSetting.setOnClickListener {
            val i = Intent(this, TimerEditActivity::class.java)
            i.putExtra("timerTitle", "타이머 수정")
            startActivity(i)
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