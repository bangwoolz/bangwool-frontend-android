package com.example.bangwool.ui.home

import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.bangwool.LoginActivity
import com.example.bangwool.MainActivity
import com.example.bangwool.databinding.ActivityTimerBinding
import com.example.bangwool.retrofit.RetrofitUtil
import com.example.bangwool.retrofit.TokenResponse
import com.example.bangwool.retrofit.WorkRequest
import com.example.bangwool.retrofit.WorkResponse
import com.example.bangwool.retrofit.saveAccessToken
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Timer
import kotlin.concurrent.timer


class TimerActivity : AppCompatActivity() {
    lateinit var binding:ActivityTimerBinding
    var workHour = 0
    var workMin = 5
    var testSec = 3
    var testSec2 = 5
    var restTime = 0
    var color = "purple"
    private var time = 100 * 60 * workMin + 100 * 60 * 60 * workHour + 100 * testSec
    private var recentTime = time
    private var timerTask : Timer? = null
    lateinit var ppomodoroId:String
    var isWorking = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTimerBinding.inflate(layoutInflater)

        val intent = intent //전달할 데이터를 받을 Intent
        ppomodoroId = intent.getStringExtra("ppomodoroId").toString()

        //타이머 색깔 설정
        when(color) {
            "red" -> binding.ivTimerColor.imageTintList = ColorStateList.valueOf(resources.getColor(com.example.bangwool.R.color.timer_color_red))
            "pink" -> binding.ivTimerColor.imageTintList = ColorStateList.valueOf(resources.getColor(com.example.bangwool.R.color.timer_color_pink))
            "orange" -> binding.ivTimerColor.imageTintList = ColorStateList.valueOf(resources.getColor(com.example.bangwool.R.color.timer_color_orange))
            "yellow" -> binding.ivTimerColor.imageTintList = ColorStateList.valueOf(resources.getColor(com.example.bangwool.R.color.timer_color_yellow))
            "purple" -> binding.ivTimerColor.imageTintList = ColorStateList.valueOf(resources.getColor(com.example.bangwool.R.color.timer_color_purple))
            "blue" -> binding.ivTimerColor.imageTintList = ColorStateList.valueOf(resources.getColor(com.example.bangwool.R.color.timer_color_blue))
            "skyblue" -> binding.ivTimerColor.imageTintList = ColorStateList.valueOf(resources.getColor(com.example.bangwool.R.color.timer_color_skyblue))
            "green" -> binding.ivTimerColor.imageTintList = ColorStateList.valueOf(resources.getColor(com.example.bangwool.R.color.timer_color_green))
            else -> binding.ivTimerColor.imageTintList = ColorStateList.valueOf(resources.getColor(com.example.bangwool.R.color.timer_color_red))
        }

        binding.btnContinue.setOnClickListener{
            binding.btnContinue.visibility = View.INVISIBLE
            binding.btnClear.visibility = View.INVISIBLE
            binding.btnStop.visibility=View.VISIBLE
            binding.ivHappyTomato.visibility=View.GONE
            binding.ivStudyTomato.visibility=View.VISIBLE
            startTimer()    //타이머 작동
        }
        binding.btnClear.setOnClickListener{
            clearBtns()
            if(isWorking){
                clearToWorkTime()
            } else {
                clearToRestTime()
            }
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

            // 시간 서버로 전송처리
            val diffMin = (recentTime-time)/(100*60)
            Log.d("",diffMin.toString())
            if(diffMin>0){
                recentTime = time -(100*60)*diffMin+(recentTime-time)%(100*60)
                if(diffMin>=60){
//                    sendToServerWorkTime(diffMin/60,diffMin%60)
                } else {
//                    sendToServerWorkTime(0,diffMin)
                }
            }
        }
        binding.btnStart.setOnClickListener{
            binding.btnStart.visibility = View.INVISIBLE
            binding.btnStop.visibility=View.VISIBLE
            binding.ivHappyTomato.visibility=View.GONE
            binding.ivStudyTomato.visibility=View.VISIBLE
            startTimer()    //타이머 작동
        }
        clearToWorkTime()

        setContentView(binding.root)
    }

    val timerHandler: Handler = object : Handler() {
        override fun handleMessage(msg: Message) {
            if (msg.what == 0) {
//                sendToServerWorkTime(workHour,workMin)
                clearBtns()
                binding.ivHappyTomato.visibility=View.VISIBLE
                binding.ivStudyTomato.visibility=View.GONE
            }
        }
    }

    //타이머 작동
    private fun startTimer() {
        timerTask = timer(period = 10) {
            if(time<=0){
                if(isWorking){
                    clearToRestTime()
                    timerHandler.sendEmptyMessage(0)
//                    clearBtns() 위줄 코드가 대신 실행
                    isWorking = false
                } else {
                    clearToWorkTime()
//                    clearBtns() 위줄 코드가 대신 실행
                    timerHandler.sendEmptyMessage(0)
                    isWorking = true
                }
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
    private fun clearBtns() {
        binding.btnClear.visibility = View.INVISIBLE
        binding.btnContinue.visibility = View.INVISIBLE
        binding.btnStart.visibility = View.VISIBLE
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
        time = 100 * 60 * workMin + 100 * 60 * 60 * workHour + 100 * testSec
        showTimeOnTimer()
        binding.progressbar.max = time
        binding.progressbar.progress=time
    }

    private fun clearToWorkTime() {
        time = 100 * 60 * workMin + 100 * 60 * 60 * workHour + 100 * testSec
        recentTime = time
        showTimeOnTimer()
        binding.progressbar.max = time
        binding.progressbar.progress=time
    }

    private fun clearToRestTime() {
        time = 100 * 60 * restTime + 100 * testSec2
        recentTime = time
        showTimeOnTimer()
        binding.progressbar.max = time
        binding.progressbar.progress=time
    }

    private fun sendToServerWorkTime(newWorkHour:Int, newWorkMin:Int) {
        val recordWorkRequest = WorkRequest(newWorkHour,newWorkMin)
        RetrofitUtil.getRetrofit().RecordWork(ppomodoroId.toInt(),recordWorkRequest).enqueue(object :
            Callback<WorkResponse> {
            override fun onResponse(
                call: Call<WorkResponse>,
                response: Response<WorkResponse>
            ) {
                if (response.isSuccessful) {
                    val workid = response.body()!!.id
                    Log.d("","성공함")
                } else {
                    Log.d("","실패함")

                }
            }

            override fun onFailure(call: Call<WorkResponse>, t: Throwable) {
                Log.d("","실패함 onFailure")

            }
            })
    }

}