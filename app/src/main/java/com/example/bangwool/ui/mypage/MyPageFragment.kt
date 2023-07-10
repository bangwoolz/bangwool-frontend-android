package com.example.bangwool.ui.mypage

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.bangwool.LoginActivity
import com.example.bangwool.databinding.FragmentMypageBinding

class MyPageFragment : Fragment() {
    lateinit var binding : FragmentMypageBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMypageBinding.inflate(inflater, container, false)


        init()

        return binding.root
    }

    private fun init() {
        binding.loginBtn.setOnClickListener {
            Log.d("LoginBtn", "click!")
            val intent = Intent(requireContext(), LoginActivity::class.java)
            startActivity(intent)
        }
    }
}