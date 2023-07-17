package com.example.bangwool

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        // 알람이 발생했을 때 실행되는 코드를 여기에 작성.
        Toast.makeText(context, "알람이 울립니다!", Toast.LENGTH_SHORT).show()
    }
}