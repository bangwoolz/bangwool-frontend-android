package com.example.bangwool.ui.home

import android.content.ContentValues.TAG
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.bangwool.databinding.FragmentHomeBinding
import java.util.Timer
import java.util.TimerTask

class HomeFragment : Fragment() {
    lateinit var binding : FragmentHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        binding.btnTimer.setOnClickListener {
            var mSecond: Long = binding.etNumber.text.toString().toLong()
            val mTimer = Timer()
            // 반복적으로 사용할 TimerTask
            var mTimerTask = object : TimerTask() {
                override fun run() {
                    val mHandler = Handler(Looper.getMainLooper())
                    mHandler.postDelayed({
                        // 반복실행할 구문
                        mSecond--
                        Log.d(TAG,"$mSecond")
                        if (mSecond <= 0) {
                            mTimer.cancel()
                            Log.d(TAG,"타이머 종료")
                        }
                        binding.tvTime.text = "$mSecond 초"
                    }, 0)
                }
            }
            mTimer.schedule(mTimerTask, 0, 1000)
            Log.d(TAG,"${mSecond}초 타이머 시작")
        }
        return binding.root
    }
}