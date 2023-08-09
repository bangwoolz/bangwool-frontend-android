package com.example.bangwool

import android.app.Application
import android.util.Log
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.common.util.Utility

class KaKaoAuthApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        var keyHash = Utility.getKeyHash(this)
        Log.d("Hash", keyHash)
        KakaoSdk.init(this, getString(R.string.kakao_native_key))
    }

}