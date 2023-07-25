package com.example.bangwool.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.bangwool.TimerSettingActivity
import com.example.bangwool.ui.home.TimerActivity
import com.example.bangwool.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    lateinit var binding : FragmentHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        binding.homeMenu.setOnClickListener {
            val i = Intent(requireContext(), TimerEditActivity::class.java)
            startActivity(i)
        }
        return binding.root
    }
}