package com.example.bangwool.ui.ranking

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bangwool.databinding.FragmentRankingBinding
import com.example.bangwool.databinding.FragmentRankingDayBinding
import com.example.bangwool.databinding.FragmentRankingWeekBinding

class RankingWeekFragment : Fragment() {
    lateinit var binding : FragmentRankingWeekBinding
    var rankingList = arrayListOf<RankingInfo>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRankingWeekBinding.inflate(inflater, container, false)
        initLayout()
        return binding.root
    }

    fun initLayout(){
        initRankingList()
        val adapter = RankingAdapter(rankingList)
        binding.rvWeekRanking.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rvWeekRanking.adapter = adapter
    }

    fun initRankingList(){
        rankingList.add(RankingInfo(1, 0, "도로", 1200))
        rankingList.add(RankingInfo(2, 0, "루틴대장", 1000))
        rankingList.add(RankingInfo(3, 0, "뽀모로", 800))
        rankingList.add(RankingInfo(4, 0, "도로로", 600))
        rankingList.add(RankingInfo(5, 0, "토마토", 400))
        rankingList.add(RankingInfo(6, 0, "도로도로", 200))
    }
}