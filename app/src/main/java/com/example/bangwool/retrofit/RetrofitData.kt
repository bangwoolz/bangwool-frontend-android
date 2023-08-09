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

data class ExistResponse(
    @SerializedName("exist") val exist: Boolean
)

data class WorkRequest(
    @SerializedName("workedHour") val workedHour: Int,
    @SerializedName("workedMin") val workedMin : Int,
)
data class WorkResponse(
    @SerializedName("id") val id: Int,
)
data class Works(
    @SerializedName("ppomodoroId") val ppomodoroId: Int,
    @SerializedName("name") val name: String,
    @SerializedName("workHour") val workHour: Int,
    @SerializedName("workedMin") val workedMin: Int,
    )

data class PpomodoroId(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("color") val color: String,
    @SerializedName("workHour") val workHour: Int,
    @SerializedName("workMin") val workMin: Int,
    @SerializedName("restTime") val restTime: Int
)
data class Ppomodoro(
    @SerializedName("name") val name: String,
    @SerializedName("color") val color: String,
    @SerializedName("workHour") val workHour: Int,
    @SerializedName("workMin") val workMin: Int,
    @SerializedName("restTime") val restTime: Int
)

data class Ppomodoros(
    @SerializedName("ppomodoros") val ppomodoros: List<PpomodoroId>
)


data class PpomodorosResponse(
    @SerializedName("id") val id: Int
)

data class KakaoLoginRequest(
    @SerializedName("token") val token: String
)
data class OAuthTokenResponse(
    @SerializedName("token") val token: String,
    @SerializedName("platform") val platform: Platform,
    @SerializedName("platformId") val platformId: Long,
    @SerializedName("id") val id: Int
)

enum class Platform{
    KAKAO
}
