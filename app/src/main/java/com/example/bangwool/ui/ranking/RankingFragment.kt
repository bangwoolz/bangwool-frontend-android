package com.example.bangwool.ui.ranking

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.bangwool.databinding.FragmentRankingBinding
import com.example.bangwool.ui.home.TimerActivity
import com.example.bangwool.ui.home.TimerEditActivity

class RankingFragment : Fragment() {
    lateinit var binding : FragmentRankingBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRankingBinding.inflate(inflater, container, false)
        binding.textNotifications.setOnClickListener{
            val intent = Intent(activity, TimerEditActivity::class.java)
            startActivity(intent)
        }
        return binding.root
    }
}