package com.example.bangwool.ui.mypage

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.bangwool.databinding.FragmentMypageBinding
import com.example.bangwool.ui.login.LoginActivity

class MyPageFragment : Fragment() {
    lateinit var binding: FragmentMypageBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMypageBinding.inflate(inflater, container, false)

        // 로그아웃 버튼 클릭 이벤트
        binding.textViewLogout.setOnClickListener {
            // 로그아웃 버튼을 클릭하면 LoginActivity로 이동하고 현재 액티비티를 종료
            val intent = Intent(activity, LoginActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
            startActivity(intent)
            activity?.finish()
        }

        binding.textViewAbout.setOnClickListener {
            AboutDialogUtils.showAboutDialog(requireContext())
        }

        binding.withdrawMenu.setOnClickListener {
            WithdrawDialogUtils.showAboutDialog(requireContext())
        }

        return binding.root
    }
}
