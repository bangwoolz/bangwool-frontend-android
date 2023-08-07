package com.example.bangwool.ui.ranking

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bangwool.R
import com.example.bangwool.databinding.FragmentRankingDayBinding
import com.example.bangwool.retrofit.DailyRankingRequest
import com.example.bangwool.retrofit.RankingResponse
import com.example.bangwool.retrofit.RetrofitClient
import com.example.bangwool.retrofit.getAccessToken
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class RankingDayFragment : Fragment() {
    private lateinit var binding: FragmentRankingDayBinding
    private val rankingList = arrayListOf<RankingInfo>()
    private val adapter = RankingAdapter(rankingList)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRankingDayBinding.inflate(inflater, container, false)

        initLayout()

        // 일간 랭킹 데이터 가져오는 메서드 호출
        fetchDailyRankingData()

        return binding.root
    }

    private fun initLayout() {
        binding.rvDayRanking.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rvDayRanking.adapter = adapter
    }

    // 일간 랭킹 데이터 가져오는 메서드
    fun fetchDailyRankingData() {
        // 액세스 토큰
        val accessToken = getAccessToken(requireContext())

        // Retrofit API 서비스
        val apiService = RetrofitClient.createApiService()

        // 현재 시간 계산 (오전 6시부터 현재시간까지 이거 맞는지..??)
        val currentTime = Calendar.getInstance().timeInMillis
        val sixAM = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 6)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }.timeInMillis

        val start = if (currentTime < sixAM) 0 else ((currentTime - sixAM) / (1000 * 60)).toInt()
        val end = start // 일간 랭킹은 현재 시간까지..?

        // 일간 랭킹 조회
        apiService.getDailyRanking(DailyRankingRequest(start, end)).enqueue(object : Callback<RankingResponse> {
            override fun onResponse(call: Call<RankingResponse>, response: Response<RankingResponse>) {
                if (response.isSuccessful) {
                    // API 응답 성공 시 랭킹 리스트 업데이트..
                    val rankingItems = response.body()?.rankingResponses
                    rankingList.clear()
                    rankingItems?.let {
                        for (item in it) {
                            rankingList.add(RankingInfo(item.rank,0, item.nickname, item.workedHour * 60 + item.workedMin))
                        }
                    }
                    adapter.notifyDataSetChanged()
                } else {
                    // API 응답 실패시에 처리하는 부분임
                }
            }

            override fun onFailure(call: Call<RankingResponse>, t: Throwable) {
                // 통신 실패 시 처리하는 부분임
            }
        })
    }
}
