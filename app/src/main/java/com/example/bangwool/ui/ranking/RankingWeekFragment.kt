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

    //시간 범위를 계산하고 이용하기에 용이하데서 Calendar 클래스사용..
    //날짜 다뤄야해서
    fun fetchWeeklyRankingData() {
        RetrofitUtil.setAccessToken(getAccessToken(requireContext()))
        val apiService = RetrofitUtil.getRetrofit()

        //현재시간 밀리초로 가져옴
        val currentTime = Calendar.getInstance().timeInMillis
        //Calendar 객체를 생성
        val cal = Calendar.getInstance()
        //Calendar 객체의 요일을 월요일로 설정해서 주간 랭킹의 시작 시간을 현재 주의 월요일로 설정..매주 월요일부터 랭킹 시작
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY)
        //Calendar 객체의 시간 정보를 0시 0분 0초로 설정, 매주 월요일 자정부터 시작된데요
        cal.set(Calendar.HOUR_OF_DAY, 0)
        cal.set(Calendar.MINUTE, 0)
        cal.set(Calendar.SECOND, 0)
        //Calendar 객체의 시간 정보를 밀리초 단위로 가져와서 주간 랭킹의 시작 시간으로 설정
        val start = cal.timeInMillis
        //주간 랭킹의 끝 시간으로 현재 시간을 사용..12시부터 현재시간을 주간으로 쓰니까
        val end = currentTime
        //서버로 시작,끝시간 주기 끝..

        //enqueue 메서드를 호출하는부분은 구글링..
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
                    //로그 찍기1
                    Log.e("RankingWeekFragment", "API 응답 실패: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<RankingResponse>, t: Throwable) {
                //로그 찍기2
                Log.e("RankingWeekFragment", "통신 실패: ${t.message}")
            }
        })
    }
}