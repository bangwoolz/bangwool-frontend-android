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
data class DailyRankingRequest(
    @SerializedName("start") val start: Int,
    @SerializedName("end") val end: Int
)

data class WeeklyRankingRequest(
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
//data class Works(
//    @SerializedName("ppomodoroId") val ppomodoroId: Int,
//    @SerializedName("name") val name: String,
//    @SerializedName("workHour") val workHour: Int,
//    @SerializedName("workedMin") val workedMin: Int,
//    )

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

data class WorkTodayResponse(
    @SerializedName("ppomodoroId") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("workHour") val workHour: Int,
    @SerializedName("workMin") val workMin: Int
)

data class WorksTodayResponse(
    @SerializedName("works") val works: List<WorkTodayResponse>
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
data class MonthWorkStatisticRequest(
    @SerializedName("year") val year: Int,
    @SerializedName("month") val month: Int
)

data class MonthWorkStatisticResponse(
    @SerializedName("works") val works: List<MonthWorkStatistic>,
    )

data class MonthWorkStatistic(
    @SerializedName("month") val month: Int,
    @SerializedName("day") val day: Int,
    @SerializedName("workHour") val workHour: Int,
    @SerializedName("workMin") val workMin: Int,
    @SerializedName("createDate") val createDate: String,
)

data class WeekWorkStatisticResponse(
    @SerializedName("works") val works: List<WeekWorkStatistic>,
)

data class WeekWorkStatistic(
    @SerializedName("dayOfWeek") val dayOfWeek: Int,
    @SerializedName("workHour") val workHour: Int,
    @SerializedName("workMin") val workMin: Int,
)

enum class Platform{
    KAKAO
}
