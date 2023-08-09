package com.example.bangwool.ui.etc

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        Toast.makeText(context, "알람이 울립니다요!", Toast.LENGTH_SHORT).show()
    }
}