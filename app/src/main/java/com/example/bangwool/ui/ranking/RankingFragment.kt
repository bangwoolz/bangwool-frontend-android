package com.example.bangwool.ui.ranking

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.bangwool.databinding.FragmentRankingBinding
import com.google.android.material.tabs.TabLayoutMediator

class RankingFragment : Fragment() {
    lateinit var binding : FragmentRankingBinding
    val tabList = listOf<String>("일간", "주간")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRankingBinding.inflate(inflater, container, false)
        initLayout()
        return binding.root
    }

    fun initLayout(){
        val adapter = RankingVPAdapter(this)
        binding.vpRanking.adapter = adapter
        TabLayoutMediator(binding.tlRanking, binding.vpRanking) {tab, position ->
            tab.text = tabList[position]
        }.attach()
    }
}