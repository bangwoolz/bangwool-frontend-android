package com.example.bangwool.ui.login

import android.app.Application
import android.util.Log
import com.example.bangwool.BuildConfig
import com.example.bangwool.R
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.common.util.Utility

class KaKaoAuthApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        var keyHash = Utility.getKeyHash(this)
        Log.d("Hash", keyHash)
        KakaoSdk.init(this, BuildConfig.KAKAO_NATIVE_KEY)
    }

}