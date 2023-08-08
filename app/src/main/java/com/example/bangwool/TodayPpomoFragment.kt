package com.example.bangwool

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bangwool.databinding.FragmentTodayPpomoBinding
import com.example.bangwool.retrofit.RetrofitUtil
import com.example.bangwool.retrofit.WorkTodayResponse
import com.example.bangwool.retrofit.WorksTodayResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class TodayPpomoFragment : Fragment() {
    lateinit var binding: FragmentTodayPpomoBinding

    var ppomoList: ArrayList<WorkTodayResponse> = arrayListOf()
    lateinit var todayPpomoAdapter: TodayPpomoAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTodayPpomoBinding.inflate(inflater, container, false)

//        initDummyData()
        getTodayPpomo()
        init()

        return binding.root
    }

    fun init() {
        binding.apply {
            todayPpomoAdapter = TodayPpomoAdapter(requireContext(), ppomoList)

            todayppomoRecyclerView.apply {
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, true)
                adapter = todayPpomoAdapter

            }

//            topSetting.setOnClickListener {
//                // 설정으로 이동
//            }
//
//            closeBtn.setOnClickListener {
//                // 밑에 메뉴바가 있는데 닫기 버튼이 왜
//            }

        }
    }

    private fun getTodayPpomo() {
        RetrofitUtil.getRetrofit().GetWork().enqueue(object :
            Callback<WorksTodayResponse> {
            override fun onResponse(
                call: Call<WorksTodayResponse>,
                response: Response<WorksTodayResponse>
            ) {
                if (response.isSuccessful) {
                    Log.i("GETTodayPpomo/Success", response.body()!!.works.toString())
                    val data = response.body()!!.works
                    ppomoList.clear()
                    ppomoList.addAll(data)
                    todayPpomoAdapter.notifyDataSetChanged()
                    //뽀모도로 가져와서 리싸이클러뷰에 연동하기
                }
            }

            override fun onFailure(call: Call<WorksTodayResponse>, t: Throwable) {
                Log.i("GETTodayPpomo/Failure", "fail")

            }
        })
    }


    fun initDummyData() {
        val dummydata = WorkTodayResponse(0 ,"test", 2, 30)
        ppomoList.add(dummydata)
        ppomoList.add(dummydata)
        ppomoList.add(dummydata)
    }
}