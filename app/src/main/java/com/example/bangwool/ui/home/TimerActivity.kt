package com.example.bangwool.ui.home

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.bangwool.R
import com.example.bangwool.databinding.ActivityTimerBinding
import com.example.bangwool.retrofit.RetrofitUtil
import com.example.bangwool.retrofit.WorkRequest
import com.example.bangwool.retrofit.WorkResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Timer
import kotlin.concurrent.timer


class TimerActivity : AppCompatActivity() {
    lateinit var binding: ActivityTimerBinding

    //타이머 intent 값
    private var ppomodoroId = 0
    private var color = "purple"
    private var workHour = 0
    private var workMin = 2
    private var restTime = 0

    //
    private val workTime = 100 * 60 * workMin + 100 * 60 * 60 * workHour
    private var time = workTime
    private var sendingTime = 0
    private var timerTask: Timer? = null
    var isWorking = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTimerBinding.inflate(layoutInflater)

        //전달할 데이터를 받을 Intent
        val intent = intent

        ppomodoroId = intent.getStringExtra("id")!!.toInt()
        val name = intent.getStringExtra("name")
        color = intent.getStringExtra("color")!!
        workHour = intent.getStringExtra("workHour")!!.toInt()
        workMin = intent.getStringExtra("workMin")!!.toInt()
        restTime = intent.getStringExtra("restTime")!!.toInt()

        // 타이머 이름 연동
        binding.tvTimerName.text = name

        // 타이머 색깔 연동
        when (color) {
            "red" -> binding.ivTimerColor.imageTintList =
                ColorStateList.valueOf(resources.getColor(com.example.bangwool.R.color.timer_color_red))

            "pink" -> binding.ivTimerColor.imageTintList =
                ColorStateList.valueOf(resources.getColor(com.example.bangwool.R.color.timer_color_pink))

            "orange" -> binding.ivTimerColor.imageTintList =
                ColorStateList.valueOf(resources.getColor(com.example.bangwool.R.color.timer_color_orange))

            "yellow" -> binding.ivTimerColor.imageTintList =
                ColorStateList.valueOf(resources.getColor(com.example.bangwool.R.color.timer_color_yellow))

            "purple" -> binding.ivTimerColor.imageTintList =
                ColorStateList.valueOf(resources.getColor(com.example.bangwool.R.color.timer_color_purple))

            "blue" -> binding.ivTimerColor.imageTintList =
                ColorStateList.valueOf(resources.getColor(com.example.bangwool.R.color.timer_color_blue))

            "skyblue" -> binding.ivTimerColor.imageTintList =
                ColorStateList.valueOf(resources.getColor(com.example.bangwool.R.color.timer_color_skyblue))

            "green" -> binding.ivTimerColor.imageTintList =
                ColorStateList.valueOf(resources.getColor(com.example.bangwool.R.color.timer_color_green))

            else -> binding.ivTimerColor.imageTintList =
                ColorStateList.valueOf(resources.getColor(com.example.bangwool.R.color.timer_color_red))
        }
        Log.d(
            "getStringExtra",
            ppomodoroId.toString() + name + color + workHour.toString() + workMin.toString() + restTime.toString()
        )


        binding.btnStart.setOnClickListener {
            binding.btnStart.visibility = View.INVISIBLE
            binding.btnStop.visibility = View.VISIBLE
            binding.ivHappyTomato.visibility = View.GONE
            binding.ivStudyTomato.visibility = View.VISIBLE
            startTimer()    //타이머 작동
        }
        binding.btnContinue.setOnClickListener {
            binding.btnContinue.visibility = View.INVISIBLE
            binding.btnClear.visibility = View.INVISIBLE
            binding.btnStop.visibility = View.VISIBLE
            binding.ivHappyTomato.visibility = View.GONE
            binding.ivStudyTomato.visibility = View.VISIBLE
            startTimer()    //타이머 작동
        }
        binding.btnStop.setOnClickListener {
            binding.btnContinue.visibility = View.VISIBLE
            binding.btnClear.visibility = View.VISIBLE
            binding.btnStop.visibility = View.INVISIBLE
            binding.ivHappyTomato.visibility = View.VISIBLE
            binding.ivStudyTomato.visibility = View.GONE
            stopTimer()

            // 시간 서버로 전송처리
            if (isWorking) {
                val m = sendingTime / 6000
                sendingTime -= m * 6000
                Log.d("qwerty1234", m.toString())
                timerHandler.sendEmptyMessage(m)
            }
        }

        binding.btnClear.setOnClickListener {
            clearBtns()
            if (isWorking) {
                clearToWorkTime()
            } else {
                clearToRestTime()
            }
        }
        binding.icXBtn.setOnClickListener {
            val m = sendingTime / 6000
            Log.d("qwerty1234", m.toString())
            timerHandler.sendEmptyMessage(m)
            finish()
        }

        binding.icSetting.setOnClickListener {
            val i = Intent(this, TimerEditActivity::class.java)
            i.putExtra("timerTitle", "타이머 수정")
            startActivity(i)
        }

        clearToWorkTime()
        binding.ivHappyTomato.visibility = View.GONE
        binding.ivStudyTomato.visibility = View.VISIBLE
        setContentView(binding.root)
    }

    val timerHandler: Handler = @SuppressLint("HandlerLeak")
    object : Handler() {
        override fun handleMessage(msg: Message) {
            var m = msg.what
            val h = m / 60
            m %= 60
            Log.d("qwerty2", "${h} : ${m}")
            if (m == 0 && h == 0) {
                Log.i("TimerActivity | sendToServerWorkTime", "NOT SEND")
            } else {
                Log.i("TimerActivity | sendToServerWorkTime", "SEND")

                sendToServerWorkTime(h,m)
            }
        }
    }

    //타이머 작동
    private fun startTimer() {
        timerTask = timer(period = 1) {
            if (time <= 0) {
                if (isWorking) {
                    clearToRestTime()
                    val m = sendingTime / 6000
                    sendingTime -= m * 6000
                    Log.d("qwerty1234", m.toString())
                    timerHandler.sendEmptyMessage(m)
                    isWorking = false
                } else {
                    clearToWorkTime()
                    isWorking = true
                }

                runOnUiThread {
                    binding.btnContinue.visibility = View.INVISIBLE
                    binding.btnClear.visibility = View.INVISIBLE
                    binding.btnStop.visibility = View.INVISIBLE
                    binding.btnStart.visibility = View.VISIBLE
                }
                cancel()
            }
            time--
            if (isWorking)
                sendingTime++
            val min = time / 6000
            val sec = time % 6000 / 100
            val milli = time % 100

            if (isWorking) {
                if (time / 100 % 2 == 0) {
                    binding.ivStudyTomato.setImageResource(R.drawable.studying_ppomo_mdpi)
                } else {
                    binding.ivStudyTomato.setImageResource(R.drawable.studying_ppomo2_mdpi)
                }
            } else {
                if (time / 100 % 2 == 0) {
                    binding.ivStudyTomato.setImageResource(R.drawable.happy_ppomo1_mdpi)
                } else {
                    binding.ivStudyTomato.setImageResource(R.drawable.happy_ppomo2_mdpi)
                }
            }

            runOnUiThread {
                var upgradedMin = min.toString()
                if (min < 10) {
                    upgradedMin = "0" + min.toString()
                }
                var upgradedSec = sec.toString()
                if (sec < 10) {
                    upgradedSec = "0" + sec.toString()
                }
                if (milli == 0) {
                    binding.tvTimerMain?.text = "${upgradedMin} : ${upgradedSec}"
                    binding.progressbar.progress = time
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
        if (min < 10) {
            upgradedMin = "0" + min.toString()
        }
        var upgradedSec = sec.toString()
        if (sec < 10) {
            upgradedSec = "0" + sec.toString()
        }
        Log.d("qwerty123", "${upgradedMin} : ${upgradedSec}")
        runOnUiThread {
            binding.tvTimerMain!!.text = upgradedMin + " : " + upgradedSec
        }
    }


    private fun clearToWorkTime() {
        runOnUiThread {
            binding.tvTimerType.text = "집중 시간"
        }
        time = 100 * 60 * workMin + 100 * 60 * 60 * workHour
        showTimeOnTimer()
        binding.progressbar.max = time
        binding.progressbar.progress = time
    }

    private fun clearToRestTime() {
        runOnUiThread {
            binding.tvTimerType.text = "휴식 시간"
        }
        time = 100 * 60 * restTime
        showTimeOnTimer()
        binding.progressbar.max = time
        binding.progressbar.progress = time
    }

    private fun sendToServerWorkTime(newWorkHour: Int, newWorkMin: Int) {
        val recordWorkRequest = WorkRequest(newWorkHour, newWorkMin)
        RetrofitUtil.getRetrofit().RecordWork(ppomodoroId, recordWorkRequest).enqueue(object :
            Callback<WorkResponse> {
            override fun onResponse(
                call: Call<WorkResponse>,
                response: Response<WorkResponse>
            ) {
                if (response.isSuccessful) {
                    val workId = response.body()!!.id
                    Log.d("", "성공함 id:${workId}")
                } else {
                    Log.d("", "실패함")

                }
            }

            override fun onFailure(call: Call<WorkResponse>, t: Throwable) {
                Log.d("", "실패함 onFailure")

            }
        })
    }

}