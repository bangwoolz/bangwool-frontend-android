package com.example.bangwool

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.bangwool.databinding.ActivityTimerSettingBinding

class TimerSettingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTimerSettingBinding
    private lateinit var timeDialogUtils: TimeDialogUtils
    private lateinit var restTimerDialogUtil: RestTimerDialogUtil

    private fun onAutoTimerStateChanged(isChecked: Boolean) {
        if (isChecked) {
            // Switch가 켜진 상태일 때의 로직 추가욤
            Toast.makeText(this, "자동 타이머가 활성화되었습니다.", Toast.LENGTH_SHORT).show()
        } else {
            // Switch가 꺼진 상태일 때의 로직 추가욤
            Toast.makeText(this, "자동 타이머가 비활성화되었습니다.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupAutoTimerAlarm() {
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val restTimeText = binding.textViewRestTime.text.toString()
        val restTimeMinutes = extractMinutesFromTimeText(restTimeText)

        // 알람 시간 설정
        val alarmTimeMillis = System.currentTimeMillis() + (restTimeMinutes * 60 * 1000)

        // 알람이 발생했을 때 실행할 BroadcastReceiver 지정
        val intent = Intent(this, AlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE) // FLAG_IMMUTABLE 추가

        // 알람 설정
        alarmManager.set(AlarmManager.RTC_WAKEUP, alarmTimeMillis, pendingIntent)

    }

    private fun extractMinutesFromTimeText(timeText: String): Int {
        // "X분" 형식의 텍스트에서 분 값을 추출하여 반환하는 함수입니다.
        val minutesText = timeText.replace(Regex("[^\\d]"), "")
        return minutesText.toIntOrNull() ?: 0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTimerSettingBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        timeDialogUtils = TimeDialogUtils(this)
        restTimerDialogUtil=RestTimerDialogUtil(this)

        binding.textViewWorkTime.setOnClickListener {
            timeDialogUtils.showWorkTimeDialog(binding.textViewWorkTime)
        }

        binding.textViewRestTime.setOnClickListener {
            restTimerDialogUtil.showRestTimeDialog(binding.textViewRestTime)
        }

        binding.switchAutoTimer.setOnCheckedChangeListener { _, isChecked ->
            onAutoTimerStateChanged(isChecked)
        }

        binding.switchAutoTimer.setOnCheckedChangeListener { _, isChecked ->
            onAutoTimerStateChanged(isChecked)
            if (isChecked) {
                setupAutoTimerAlarm()
            }
        }

    }
}
