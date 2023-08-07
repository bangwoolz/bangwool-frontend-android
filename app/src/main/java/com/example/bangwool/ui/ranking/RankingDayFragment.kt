package com.example.bangwool.ui.ranking

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bangwool.databinding.FragmentRankingDayBinding
import com.example.bangwool.databinding.FragmentRankingWeekBinding
import com.example.bangwool.retrofit.DailyRankingRequest
import com.example.bangwool.retrofit.RankingResponse
import com.example.bangwool.retrofit.RetrofitInterface
import com.example.bangwool.retrofit.RetrofitUtil
import com.example.bangwool.retrofit.WeeklyRankingRequest
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

        fetchDailyRankingData()

        return binding.root
    }

    private fun initLayout() {
        binding.rvDayRanking.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rvDayRanking.adapter = adapter
    }

    fun fetchDailyRankingData() {
        RetrofitUtil.setAccessToken(getAccessToken(requireContext()))
        val apiService = RetrofitUtil.getRetrofit()

        // 현재 시간 , 오전 6시 시간 계산
        val currentTime = Calendar.getInstance().timeInMillis
        val sixAM = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 6)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }.timeInMillis

        // 랭킹 조회 범위 계산
        val start = if (currentTime < sixAM) 0
            // 만약 현재 시간이 오전 6시 이전이라면 랭킹 조회 시작 시간을 0으로 설정
            // 오전 6시 이전에는 랭킹 데이터를 업데이트하지 않으..
        else ((currentTime - sixAM) / (1000 * 60)).toInt()
            // 현재 시간이 오전 6시 이후라면 시간 차이를 분 단위로 계산하여 시작 시간으로 설정함
        val end = if (currentTime < sixAM) ((sixAM - currentTime) / (1000 * 60)).toInt()
            // 만약 현재 시간이 오전 6시 이전이라면 랭킹 조회 끝 시간을 현재 시간과의 시간 차이로 설정
        else 24 * 60 - ((currentTime - sixAM) / (1000 * 60)).toInt() - 1
        // 현재 시간이 오전 6시 이후라면 랭킹 조회 끝 시간을 오전 5시 59분까지의 분으로 설정
        //하루 전체 시간을 대상으로 하지만 현재 시간을 기준으로 랭킹을 조회하므로, 끝 시간을 설정할 때에는 24시간에서 오전 6시부터 현재 시간까지의 시간 차이를 뺌

        // 랭킹 조회 요청 보내기
        apiService.getDailyRanking(DailyRankingRequest(start, end)).enqueue(object : Callback<RankingResponse> {
            override fun onResponse(call: Call<RankingResponse>, response: Response<RankingResponse>) {
                if (response.isSuccessful) {
                    val rankingItems = response.body()?.rankingResponses
                    // 기존에 저장되어 있던 랭킹 정보를 모두 지우고 초기화
                    rankingList.clear()
                    rankingItems?.let {
                        for (item in it) {
                            // 랭킹 정보를 변환하여 리스트에 추가
                            rankingList.add(RankingInfo(item.rank, 0, item.nickname, item.workedHour * 60 + item.workedMin))
                        }
                    }
                    // 어댑터에 데이터 변경을 알리고 리사이클러뷰 업데이트
                    adapter.notifyDataSetChanged()
                } else {
                    // API 응답 실패 시 로그 출력
                    Log.e("RankingWeekFragment", "API 응답 실패: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<RankingResponse>, t: Throwable) {
                // 통신 실패 시 로그 출력
                Log.e("RankingWeekFragment", "통신 실패: ${t.message}")
            }
        })

    }
}
