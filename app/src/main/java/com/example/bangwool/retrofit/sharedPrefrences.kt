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

fun saveGoalTime(context: Context, goalTime: Int) {
    val pref =
        context.getSharedPreferences("goalTime_spf", Context.MODE_PRIVATE) //shared key 설정
    val edit = pref.edit() // 수정모드
    edit.putString("goalTime", goalTime.toString()) // 값 넣기
    edit.apply() // 적용하기
}
fun getGoalTime(context: Context): String {
    val spf = context.getSharedPreferences("goalTime_spf", AppCompatActivity.MODE_PRIVATE)
    return spf.getString("goalTime", 0.toString())!!
}
fun removeGoalTime(context: Context) {
    val pref = context.getSharedPreferences("goalTime_spf", AppCompatActivity.MODE_PRIVATE)
    val edit = pref.edit() // 수정모드
    edit.remove("goalTime")
    edit.apply()
}