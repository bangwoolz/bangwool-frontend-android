package com.example.bangwool.ui.ranking

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bangwool.databinding.FragmentRankingWeekBinding
import com.example.bangwool.retrofit.DailyRankingRequest
import com.example.bangwool.retrofit.RankingResponse
import com.example.bangwool.retrofit.RetrofitClient
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
        fetchWeeklyRankingData() // 주간 랭킹 데이터 가져오는 메서드 호출
        return binding.root
    }

    private fun initLayout() {
        binding.rvWeekRanking.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rvWeekRanking.adapter = adapter
    }

    fun fetchWeeklyRankingData() {
        val accessToken = getAccessToken(requireContext())
        val apiService = RetrofitClient.createApiService()

        // 주차 기간 계산 (월요일 오전 12시부터 현재시간까지하는 부분임..)
        val currentTime = Calendar.getInstance().timeInMillis
        val cal = Calendar.getInstance()
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY)
        cal.set(Calendar.HOUR_OF_DAY, 0)
        cal.set(Calendar.MINUTE, 0)
        cal.set(Calendar.SECOND, 0)
        val start = cal.timeInMillis
        val end = currentTime

        // 주간 랭킹 조회 요청하는 부분
        apiService.getWeeklyRanking(DailyRankingRequest(start.toInt(), end.toInt())).enqueue(object : Callback<RankingResponse> {
            override fun onResponse(call: Call<RankingResponse>, response: Response<RankingResponse>) {
                if (response.isSuccessful) {
                    // API 응답 성공 시 랭킹 리스트 업데이트..
                    val rankingItems = response.body()?.rankingResponses
                    rankingList.clear()
                    rankingItems?.let {
                        for (item in it) {
                            rankingList.add(RankingInfo(item.rank, 0, item.nickname, item.workedHour * 60 + item.workedMin))
                        }
                    }
                    adapter.notifyDataSetChanged()
                } else {
                    // API 응답 실패 시 처리하는 부분..?
                }
            }

            override fun onFailure(call: Call<RankingResponse>, t: Throwable) {
                // 통신 실패 시 처리하는 부분.. 필요한지..?
            }
        })
    }
}
