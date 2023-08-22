package com.example.bangwool.ui.mypage

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.bangwool.BuildConfig
import com.example.bangwool.R
import com.example.bangwool.databinding.FragmentMypageBinding
import com.example.bangwool.retrofit.removePassword
import com.example.bangwool.retrofit.removeUserId
import com.example.bangwool.ui.login.LoginActivity
import com.example.bangwool.retrofit.ExistResponse
import com.example.bangwool.retrofit.MyPageResponse
import com.example.bangwool.retrofit.RetrofitInterface
import com.example.bangwool.retrofit.RetrofitUtil
import com.example.bangwool.retrofit.removeAccessToken
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class MyPageFragment : Fragment() {

    lateinit var binding: FragmentMypageBinding
    private lateinit var retrofitInterface: RetrofitInterface

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMypageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        retrofitInterface = RetrofitUtil.getRetrofit()
        fetchMyPageData()

        // 로그아웃 버튼 클릭 이벤트
        binding.textViewLogout.setOnClickListener {
            // 로그아웃 버튼을 클릭하면 LoginActivity로 이동하고 현재 액티비티를 종료
            removeUserId(requireContext())
            removePassword(requireContext())
            removeAccessToken(requireContext())
            RetrofitUtil.removeInstances()
            val intent = Intent(activity, LoginActivity::class.java)
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
            startActivity(intent)
            activity?.finish()
        }

        binding.textViewAbout.setOnClickListener {
            AboutDialogUtils.showAboutDialog(requireContext())
        }

        binding.withdrawMenu.setOnClickListener {
            WithdrawDialogUtils.showAboutDialog(requireContext())
        }
        binding.textViewQuestion.setOnClickListener {
            UpdateDialogUtils.showUpdateDialog(requireContext())
        }
        binding.appinfoMenu.setOnClickListener {
            UpdateDialogUtils.showUpdateDialog(requireContext())
        }
    }

    private fun fetchMyPageData() {
        RetrofitUtil.getRetrofit().getMyPage().enqueue(object : Callback<MyPageResponse> {
            override fun onResponse(call: Call<MyPageResponse>, response: Response<MyPageResponse>) {
                if (response.isSuccessful) {
                    val myPageResponse = response.body()!!
                    val nickname = myPageResponse.nickname
                    val profileImage = myPageResponse.profileImage


                    // 닉네임 업데이트 부분
                    binding.textViewUsername.text = "$nickname 님"

                    // 프로필 이미지 업데이트
                    if (!profileImage.isNullOrEmpty()) {
                        Glide.with(requireContext())
                            .load(profileImage)
                            .placeholder(R.drawable.profile_base) // 로딩 중에 보여줄 이미지
                            .error(R.drawable.happy_tomato) // 에러 발생 시 보여줄 이미지
                            .into(binding.imageViewProfile)//이미지 이거는 어디에..?
                    } else {
                        // 프로필 이미지가 없을 경우 이미지 표시 근데 이미지를 어디서 입력빋는..?
                        binding.imageViewProfile.setImageResource(R.drawable.happy_tomato)
                    }
                } else {
                    // 서버 응답이 올바르지 않을 때 로그 출력.. whffu...졸려..
                }
            }

            override fun onFailure(call: Call<MyPageResponse>, t: Throwable) {
                // 네트워크 요청 실패 때 로그 출력.. 졸리다..
            }
        })
    }
}
