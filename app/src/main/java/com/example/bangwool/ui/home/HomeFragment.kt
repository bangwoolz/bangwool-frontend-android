package com.example.bangwool.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bangwool.R
import com.example.bangwool.ui.home.TimerActivity
import com.example.bangwool.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    lateinit var binding: FragmentHomeBinding
    var itemList: ArrayList<HomeItem> = arrayListOf()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        binding.homeRecyclerView.setOnClickListener {
            val intent = Intent(activity, TimerActivity::class.java)
            startActivity(intent)
        }

        initDummyData()
        init()
        return binding.root
    }

    fun init() {
        binding.homeRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.homeRecyclerView.adapter = HomeAdapter(itemList)

        binding.homeAddTaskBtn.setOnClickListener {
            Toast.makeText(requireContext(), "+ 버튼 클릭됨", Toast.LENGTH_SHORT).show()
        }
    }


    fun initDummyData() {
        val dummydata = HomeItem(R.color.primary_100, "잠자기", "03:33", 0)
        itemList.add(dummydata)
        itemList.add(dummydata)
        itemList.add(dummydata)
        itemList.add(dummydata)
        itemList.add(dummydata)
        itemList.add(dummydata)
    }
}