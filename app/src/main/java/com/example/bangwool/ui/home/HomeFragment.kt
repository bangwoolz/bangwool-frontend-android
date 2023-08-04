package com.example.bangwool.ui.home

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bangwool.R
import com.example.bangwool.databinding.FragmentHomeBinding
import com.google.gson.Gson

class HomeFragment : Fragment() {
    lateinit var binding: FragmentHomeBinding
    var itemList: ArrayList<HomeItem> = arrayListOf()
    lateinit var homeAdapter: HomeAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        binding.dDay.setOnClickListener {
        }
        /*
        binding.homeRecyclerView.setOnClickListener {
            val intent = Intent(activity, TimerActivity::class.java)
            startActivity(intent)
        }
         */

        initDummyData()
        init()
        return binding.root
    }

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


            homeAdapter = HomeAdapter(requireContext(), itemList)

            homeRecyclerView.apply {
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                adapter = homeAdapter

                setOnTouchListener { _, _ ->
                    swipeHelperCallback.removePreviousClamp(this)
                    false
                }

            }


            homeAdapter.setOnClickListener(object : HomeAdapter.OnItemClickListener{
                override fun onDeleteItemClick(homeItem: HomeItem) {
//                    Log.d("CLICK!", "deleteBtn")

                    val bundle = Bundle()
                    val taskName = homeItem.taskName
                    Log.d("taskName_HomeFragment", taskName)


//                    val taskData = HomeItem(0, taskName, "", 0)
                    val dataJson = Gson().toJson(taskName)
                    bundle.putString("taskData", dataJson)

                    // OrderDialog 호출
                    val dialog = TimerDeleteDialog()
                    dialog.setOnDeleteItemClickListener(object :TimerDeleteDialog.OnDeleteItemClickListener{
                        override fun onDeleteItemClicked() {
                            //아이템 삭제
                            homeAdapter.removeItem(homeItem)
                        }
                    })
                    dialog.arguments = bundle
                    dialog.show(parentFragmentManager, "TimerDeleteDialog")
                }

            })



            homeAddTaskBtn.setOnClickListener {
                val i = Intent(requireContext(), TimerEditActivity::class.java)
                startActivity(i)
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
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.resources.displayMetrics)
    }

    fun listDialog(){
        val builder = AlertDialog.Builder(requireContext())
        val menu = arrayOf("알림 설정", "추가 설정")
        builder.setItems(menu) { dialog, which ->
            when (which) {
                0 -> {
                    // 알림 설정 화면으로 이동
                }
                1 -> {
                    // 추가 설정 화면으로 이동
                }
                else -> {
                    // 예외 처리 - 이 외의 인덱스에 대한 동작 구현 (필요한 경우)
                }
            }
        }
        val dialog = builder.create()
        dialog.show()

        // 다이얼로그의 너비를 직접 지정 (예: 70%의 너비로 지정)
        val width = (resources.displayMetrics.widthPixels * 0.5).toInt()
        dialog.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)

        // 다이얼로그의 배경 테두리 설정
        dialog.window?.setBackgroundDrawableResource(R.drawable.dialog_delete_layout)
    }


    fun initDummyData() {
        val dummydata = HomeItem(R.color.primary_100, "잠자기", "03:33", 0)
        itemList.add(dummydata)
        itemList.add(dummydata)
        itemList.add(dummydata)
    }
}