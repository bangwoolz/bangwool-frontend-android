package com.example.bangwool.retrofit

import com.google.gson.annotations.SerializedName

data class MemberSignUpRequest(
    @SerializedName("email") val id: String,
    @SerializedName("name") val image: String,
    @SerializedName("nickname") val name: String,
    @SerializedName("password") val eng_name: String
)

data class MemberSignUpResponse(
    @SerializedName("id") val id: Int
)

data class AuthLoginRequest(
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String
)
data class TokenResponse(
    @SerializedName("token") val token: String
)
//Ranking 데이터 클래스
data class RankingRequest(
    @SerializedName("start") val start: Int,
    @SerializedName("end") val end: Int
)

data class RankingResponse(
    @SerializedName("rankingResponses") val rankingResponses: List<RankingItem>
)

data class RankingItem(
    @SerializedName("rank") val rank: Int,
    @SerializedName("nickname") val nickname: String,
    @SerializedName("workedHour") val workedHour: Int,
    @SerializedName("workedMin") val workedMin: Int
)

//{
//    "email": "uLTr2ZSzAxdbgfOU2nsh@lHlSNIDK94kj41TUtrvr9nEuHgZeWW.xi7.Ym.2VwK0clpM0.RRzCJwM1p-RwvfPGD2V9Re8BMkFYsAT8PxGnfBCOhO-TeX75x4kJUYjYyIriYL8mK",
//    "name": "",
//    "nickname": "GJg",
//    "password": "~ho|\\1# "
//}
//package com.example.hackatonkuit.retrofit2
//
//import android.media.Image
//import com.google.gson.annotations.SerializedName
//
//data class Category(
//    @SerializedName("id") val id: Long,
//    @SerializedName("image") val image: String,
//    @SerializedName("name") val name: String,
//    @SerializedName("eng_name") val eng_name: String
//)
//
//data class MenuPreview(
//    @SerializedName("menuId") val menuId: Long,
//    @SerializedName("image") val image: String,
//    @SerializedName("name") val name: String,
//    @SerializedName("eng_name") val eng_name: String,
//    @SerializedName("price") val price: Int,
//    @SerializedName("menuStatus") val menuStatus: String
//)
//
//data class Menu(
//    @SerializedName("menuId") val menuId: Long,
//    @SerializedName("image") val image: String,
//    @SerializedName("name") val name: String,
//    @SerializedName("eng_name") val eng_name: String,
//    @SerializedName("description") val description: String,
//    @SerializedName("price") val price: Int,
//    @SerializedName("menuStatus") val menuStatus: String
//)
//
//data class NewMenu(
//    @SerializedName("menuId") val menuId: Long,
//    @SerializedName("image") val image: String,
//    @SerializedName("name") val name: String
//)
//
//data class CartItem(
//    @SerializedName("id") val id: Long,
//    @SerializedName("temp") val temp: String,
//    @SerializedName("size") val size: String,
//    @SerializedName("cup") val cup: String,
//    @SerializedName("count") val count: Int,
//    @SerializedName("price") val price: Int,
//    @SerializedName("menuImage") val menuImage: String,
//    @SerializedName("menuName") val menuName: String,
//    @SerializedName("menuEngName") val menuEngName: String,
//    @SerializedName("menuPrice") val menuPrice: String,
//    @SerializedName("optionItemReadResponseDtos") val optionItemReadResponseDtos: List<Option>
//)
//
//data class Option(
//    @SerializedName("name") val name: String,
//    @SerializedName("price") val price: Int,
//)