package com.example.bangwool.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.bangwool.TimerActivity
import com.example.bangwool.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    lateinit var binding : FragmentHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        binding.textHome.setOnClickListener({
            val intent = Intent(activity, TimerActivity::class.java) //지금 액티비티에서 다른 액티비티로 이동하는 인텐트 설정
            startActivity(intent)
        })
        return binding.root
    }
}