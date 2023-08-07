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
        binding.rvWeekRanking.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rvWeekRanking.adapter = adapter
    }

    fun fetchWeeklyRankingData() {
        RetrofitUtil.setAccessToken(getAccessToken(requireContext()))
        val apiService = RetrofitUtil.getRetrofit()

        val currentTime = Calendar.getInstance().timeInMillis
        val cal = Calendar.getInstance()
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY)
        cal.set(Calendar.HOUR_OF_DAY, 0)
        cal.set(Calendar.MINUTE, 0)
        cal.set(Calendar.SECOND, 0)
        val start = cal.timeInMillis
        val end = currentTime

        apiService.getWeeklyRanking(WeeklyRankingRequest(start.toInt(), end.toInt())).enqueue(object : Callback<RankingResponse> {
            override fun onResponse(call: Call<RankingResponse>, response: Response<RankingResponse>) {
                if (response.isSuccessful) {
                    val rankingItems = response.body()?.rankingResponses
                    rankingList.clear()
                    rankingItems?.let {
                        for (item in it) {
                            rankingList.add(RankingInfo(item.rank, 0, item.nickname, item.workedHour * 60 + item.workedMin))
                        }
                    }
                    adapter.notifyDataSetChanged()
                } else {
                    Log.e("RankingWeekFragment", "API 응답 실패: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<RankingResponse>, t: Throwable) {
                Log.e("RankingWeekFragment", "통신 실패: ${t.message}")
            }
        })
    }
}