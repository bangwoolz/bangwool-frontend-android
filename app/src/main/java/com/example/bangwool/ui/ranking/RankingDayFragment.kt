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
import com.example.bangwool.retrofit.RankingResponses
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
        binding.rvDayRanking.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rvDayRanking.adapter = adapter
    }

    fun fetchDailyRankingData() {
        RetrofitUtil.setAccessToken(getAccessToken(requireContext()))

        // 랭킹 조회 요청 보내기
        RetrofitUtil.getRetrofit().getDailyRanking()
            .enqueue(object : Callback<RankingResponses> {
                override fun onResponse(
                    call: Call<RankingResponses>,
                    response: Response<RankingResponses>
                ) {
                    if (response.isSuccessful) {
                        val rankingItems = response.body()?.rankingResponses
                        // 기존에 저장되어 있던 랭킹 정보를 모두 지우고 초기화
                        rankingList.clear()
                        for (i in 0 until rankingItems!!.size) {
                            // 랭킹 정보를 변환하여 리스트에 추가
                            rankingList.add(
                                RankingInfo(
                                    i+1,
                                    rankingItems[i].nickname,
                                    rankingItems[i].workedMinute,
                                    rankingItems[i].loginedUser
                                )
                            )
                        }
                        // 어댑터에 데이터 변경을 알리고 리사이클러뷰 업데이트
                        adapter.notifyDataSetChanged()
                    } else {
                        // API 응답 실패 시 로그 출력
                        Log.e("RankingWeekFragment", "API 응답 실패: ${response.code()}")
                    }
                }

                override fun onFailure(call: Call<RankingResponses>, t: Throwable) {
                    // 통신 실패 시 로그 출력
                    Log.e("RankingWeekFragment", "통신 실패: ${t.message}")
                }
            })

    }
}
