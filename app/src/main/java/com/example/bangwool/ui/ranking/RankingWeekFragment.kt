package com.example.bangwool.ui.ranking

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bangwool.databinding.FragmentRankingWeekBinding
import com.example.bangwool.retrofit.RankingResponse
import com.example.bangwool.retrofit.RankingResponses
import com.example.bangwool.retrofit.RetrofitInterface
import com.example.bangwool.retrofit.RetrofitUtil
import com.example.bangwool.retrofit.WeeklyRankingRequest
import com.example.bangwool.retrofit.getAccessToken
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class RankingWeekFragment : Fragment() {
    lateinit var binding: FragmentRankingWeekBinding
    var rankingList = arrayListOf<RankingInfo>()
    private val adapter = RankingAdapter(rankingList)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRankingWeekBinding.inflate(inflater, container, false)
        initLayout()
        fetchWeeklyRankingData()
        return binding.root
    }

    private fun initLayout() {
        binding.rvWeekRanking.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rvWeekRanking.adapter = adapter
    }

    //시간 범위를 계산하고 이용하기에 용이하데서 Calendar 클래스사용..
    //날짜 다뤄야해서
    fun fetchWeeklyRankingData() {
        RetrofitUtil.setAccessToken(getAccessToken(requireContext()))
        RetrofitUtil.getRetrofit().getWeeklyRanking().enqueue(object : Callback<RankingResponses> {
            override fun onResponse(
                call: Call<RankingResponses>,
                response: Response<RankingResponses>
            ) {
                if (response.isSuccessful) {
                    val rankingItems = response.body()?.rankingResponses
                    rankingList.clear()
                    for (i in 0 until rankingItems!!.size) {
                        rankingList.add(
                            RankingInfo(
                                i + 1,
                                rankingItems[i].nickname,
                                rankingItems[i].workedMinute
                            )
                        )
                    }
                    adapter.notifyDataSetChanged()
                } else {
                    //로그 찍기1
                    Log.e("RankingWeekFragment", "API 응답 실패: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<RankingResponses>, t: Throwable) {
                //로그 찍기2
                Log.e("RankingWeekFragment", "통신 실패: ${t.message}")
            }
        })
    }
}