package com.example.bangwool.retrofit

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.PUT

interface RetrofitInterface {

    //Ppomodoro 뽀모도로
    @GET("/ppomodoros")
    fun GetPpomodoro(): Call<Ppomodoros>

    @POST("/ppomodoros")
    fun PostPpomodoro(
        @Body requestBody: Ppomodoro
    ): Call<PpomodorosResponse>

    @GET("/ranking/day")
    fun getDailyRanking(
    ): Call<RankingResponses>

    @GET("/ranking/week")
    fun getWeeklyRanking(
    ): Call<RankingResponses>

    @PUT("/ppomodoros/{ppomodoroId}")
    fun PutPpomodoro(
        @Path("ppomodoroId") ppomodoroId: Int,
        @Body requestBody: Ppomodoro
    ): Call<PpomodorosResponse>

    @DELETE("/ppomodoros/{ppomodoroId}")
    fun DeletePpomodoro(
        @Path("ppomodoroId") ppomodoroId: Int
    ): Call<Void>


    //Work 작업
    @POST("/work/{ppomodoroId}")
    fun RecordWork(
        @Path("ppomodoroId") ppomodoroId: Int,
        @Body requestbody: WorkRequest
    ): Call<WorkResponse>

    @GET("/work/today")
    fun GetWork(): Call<WorksTodayResponse>

    @POST("/work/month")
    fun GetMonthWorkStatistic(
        @Body requestBody: MonthWorkStatisticRequest
    ): Call<MonthWorkStatisticResponse>

    @GET("/work/week")
    fun GetWeekWorkStatistic(): Call<WeekWorkStatisticResponse>

}

interface RetrofitLoginInterface {

    @POST("/members")
    fun MemberSignUp(
        @Body requestbody: MemberSignUpRequest
    ): Call<MemberSignUpResponse>

    @POST("/login")
    fun AuthLogin(
        @Body requestbody: AuthLoginRequest
    ): Call<TokenResponse>

    @GET("/members/exist/email")
    fun ExistEmail(
        @Query("email") email: String
    ): Call<ExistResponse>

    @GET("/members/exist/nickname")
    fun ExistNickname(
        @Query("nickname") nickname: String
    ): Call<ExistResponse>

    @POST("/kakao/login")
    fun KakaoLogin(
        @Body requestBody: KakaoLoginRequest
    ): Call<OAuthTokenResponse>

}