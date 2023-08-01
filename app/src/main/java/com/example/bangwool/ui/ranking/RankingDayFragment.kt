package com.example.bangwool.ui.ranking

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bangwool.databinding.FragmentRankingDayBinding

class RankingDayFragment : Fragment() {
    lateinit var binding : FragmentRankingDayBinding
    var rankingList = arrayListOf<RankingInfo>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRankingDayBinding.inflate(inflater, container, false)
        initLayout()
        return binding.root
    }

    fun initLayout(){
        initRankingList()
        val adapter = RankingAdapter(rankingList)
        binding.rvDayRanking.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rvDayRanking.adapter = adapter
    }

    fun initRankingList(){
        rankingList.add(RankingInfo(1, 0, "도로", 120))
        rankingList.add(RankingInfo(2, 0, "루틴대장", 100))
        rankingList.add(RankingInfo(3, 0, "뽀모로", 80))
        rankingList.add(RankingInfo(4, 0, "도로로", 60))
        rankingList.add(RankingInfo(5, 0, "토마토", 40))
        rankingList.add(RankingInfo(6, 0, "도로도로", 20))
    }
}