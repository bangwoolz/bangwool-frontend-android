package com.example.bangwool.ui.home

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bangwool.databinding.FragmentHomeBinding
import com.example.bangwool.retrofit.Ppomodoro
import com.example.bangwool.retrofit.PpomodoroId
import com.example.bangwool.retrofit.Ppomodoros
import com.example.bangwool.retrofit.PpomodorosResponse
import com.example.bangwool.retrofit.RetrofitUtil
import com.example.bangwool.retrofit.WorkTodayResponse
import com.example.bangwool.retrofit.WorksTodayResponse
import com.example.bangwool.retrofit.getAccessToken
import com.example.bangwool.retrofit.getUserId
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {
    lateinit var binding: FragmentHomeBinding

    //    var itemList: ArrayList<HomeItem> = arrayListOf()
    var ppomoList: ArrayList<PpomodoroId> = arrayListOf()
    var ppomoList_today: ArrayList<WorkTodayResponse> = arrayListOf()
    lateinit var homeAdapter: HomeAdapter
    private val REQUEST_EDIT_TIMER = 1
    val ppomoId: Int = 0

    val updatePpomodoro =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            getPpomo()
            getTodayPpomo()
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        binding.dDay.setOnClickListener {
        }
        Log.d("","등장")
        Log.d("","${getAccessToken(requireContext())}")
        binding.homeAddTaskBtn.setOnClickListener {
            val i = Intent(requireContext(), TimerEditActivity::class.java)
            i.putExtra("timerTitle", "타이머 추가")
            startActivityForResult(i, REQUEST_EDIT_TIMER)
        }


        /*
        binding.homeRecyclerView.setOnClickListener {
            val intent = Intent(activity, TimerActivity::class.java)
            startActivity(intent)
        }
         */


//        initDummyData()

//        getTodayPpomo()
        getPpomo()
        getTodayPpomo()
        init()
        return binding.root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_EDIT_TIMER && resultCode == Activity.RESULT_OK) {
            val name = data?.getStringExtra("name")
            val color = data?.getStringExtra("color")
            val workHour = data?.getIntExtra("workHour", 0)
            val workMin = data?.getIntExtra("workMin", 0)
            val restTime = data?.getIntExtra("restTime", 0)

            if (name != null && color != null && workHour != null && workMin != null && restTime != null) {
                // Process the data and update your UI
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    fun init() {
        binding.apply {
//            homeRecyclerView.layoutManager =
//                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
//            homeRecyclerView.adapter = HomeAdapter(requireContext(), itemList)

            val swipeHelperCallback = SwipeHelperCallback().apply {
                setClamp(dpToPx(160f, requireContext()))
            }
            val itemTouchHelper = ItemTouchHelper(swipeHelperCallback)
            itemTouchHelper.attachToRecyclerView(homeRecyclerView)

            homeAdapter = HomeAdapter(itemTouchHelper, this@HomeFragment, requireContext(), ppomoList)

            homeRecyclerView.apply {
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                adapter = homeAdapter

                setOnTouchListener { _, _ ->
                    swipeHelperCallback.removePreviousClamp(this)
                    false
                }

            }


            homeAdapter.setOnClickListener(object : HomeAdapter.OnItemClickListener {
                override fun onDeleteItemClick(homeItem: PpomodoroId) {
//                    Log.d("CLICK!", "deleteBtn")

                    val bundle = Bundle()
                    val taskName = homeItem.name
                    Log.d("taskName_HomeFragment", taskName)


//                    val taskData = HomeItem(0, taskName, "", 0)
                    val dataJson = Gson().toJson(taskName)
                    bundle.putString("taskData", dataJson)

                    // OrderDialog 호출
                    val dialog = TimerDeleteDialog(requireContext())
                    dialog.setOnDeleteItemClickListener(object :
                        TimerDeleteDialog.OnDeleteItemClickListener {
                        override fun onDeleteItemClicked() {
                            //아이템 삭제
//                            homeAdapter.removeItem(homeItem)
                            val ppomoId = homeAdapter.findId(homeItem)
                            deletePpomo(ppomoId)
                        }
                    })
                    dialog.arguments = bundle
                    dialog.show(parentFragmentManager, "TimerDeleteDialog")

                }

                override fun onEditItemClick(homeItem: PpomodoroId) {
//                    val ppomoId = homeAdapter.findId(homeItem)
//                    putPpomo(ppomoId)

                }

            })


            homeAddTaskBtn.setOnClickListener {
                val i = Intent(requireContext(), TimerEditActivity::class.java)
                i.putExtra("timerTitle", "타이머 추가")
//                startActivity(i)
                updatePpomodoro.launch(i)
            }

            homeMenu.setOnClickListener {
                Log.d("Click", "homeMenu Click")
//                listDialog()
                val homeMenuDialog = HomeMenuDialog()
                homeMenuDialog.show(parentFragmentManager, "HomeMenuDialog")

            }

        }
    }

    // dp 값을 px 값으로 변환하는 함수
    fun dpToPx(dp: Float, context: Context): Float {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dp,
            context.resources.displayMetrics
        )
    }

    private fun getPpomo() {
        RetrofitUtil.getRetrofit().GetPpomodoro().enqueue(object :
            Callback<Ppomodoros> {
            override fun onResponse(
                call: Call<Ppomodoros>,
                response: Response<Ppomodoros>
            ) {
                if (response.isSuccessful) {
                    Log.i("GETPpomo/Success", response.body()!!.ppomodoros.toString())
                    val data = response.body()!!.ppomodoros
                    ppomoList.clear()
                    ppomoList.addAll(data)
                    homeAdapter.notifyDataSetChanged()
                    //뽀모도로 가져와서 리싸이클러뷰에 연동하기
                }
            }

            override fun onFailure(call: Call<Ppomodoros>, t: Throwable) {
                Log.i("GETPpomo/Failure", "fail")

            }
        })
    }

    private fun deletePpomo(ppomoId: Int) {
        RetrofitUtil.getRetrofit().DeletePpomodoro(ppomoId).enqueue(object :
            Callback<Void> {
            override fun onResponse(
                call: Call<Void>,
                response: Response<Void>
            ) {
                if (response.isSuccessful) {
                    Log.i("DELETEPpomo/Success", "success")
                    getPpomo()
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.i("DELETEPpomo/Failure", "fail")

            }
        })
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

                    var total = 0;
                    var todayWorkHour = 0;
                    var todayWorkMin = 0;
                    if (data[0] != null) {
                        data.forEach {
                            todayWorkHour += it.workHour
                            todayWorkMin += it.workMin
                        }
                        todayWorkHour += todayWorkMin / 60;
                        todayWorkMin %= 60;

                    }
                    binding.todayWorkHourTv.text = todayWorkHour.toString()
                    binding.todayWorkMinTv.text = todayWorkMin.toString()

                }
            }

            override fun onFailure(call: Call<WorksTodayResponse>, t: Throwable) {
                Log.i("GETTodayPpomo/Failure", "fail")

            }
        })
    }


}