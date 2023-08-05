package com.example.bangwool.retrofit

import android.content.Context
import android.util.Base64
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONObject

fun saveAccessToken(context: Context, accessToken: String) {
    val pref =
        context.getSharedPreferences("accessToken_spf", Context.MODE_PRIVATE) //shared key 설정
    val edit = pref.edit() // 수정모드
    edit.putString("accessToken", accessToken) // 값 넣기
    edit.apply() // 적용하기
}
fun getAccessToken(context: Context): String {
    val spf = context.getSharedPreferences("accessToken_spf", AppCompatActivity.MODE_PRIVATE)
    return spf.getString("accessToken", 1.toString())!!
}
fun getMemberId(JWTEncoded:String): Int {
    val splitStr:List<String> = JWTEncoded.split(".")
    val decodedBytes: ByteArray = Base64.decode(splitStr[1], Base64.URL_SAFE)
    val decodedDataStr =  String(decodedBytes, charset("UTF-8"))
    val decodedData = JSONObject(decodedDataStr)
    val memberId:Int = decodedData["sub"].toString().toInt()
    return memberId
}