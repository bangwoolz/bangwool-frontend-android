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
    @POST("/work/{ppomodoroId}")
    fun RecordWork(
        @Path("ppomodoroId") ppomodoroId: Int,
        @Body requestbody: WorkRequest
    ): Call<WorkResponse>
    @GET("/work/today")
    fun WorkToday(
    ): Call<Works>
    @GET("/ppomodoros")
    fun GetPpomodoro(): Call<Ppomodoros>

    @POST("/ppomodoros")
    fun PostPpomodoro(
        @Body requestBody: Ppomodoro
    ): Call<PpomodorosResponse>

    @POST("/ranking/day")
    fun getDailyRanking(
        @Body request: DailyRankingRequest
    ): Call<RankingResponse>

    @POST("/ranking/week")
    fun getWeeklyRanking(
        @Body request: WeeklyRankingRequest
    ): Call<RankingResponse>
    @PUT("/ppomodoros/{ppomodoroId}")
    fun PutPpomodoro(
        @Path("ppomodoroId") ppomodoroId: Int,
        @Body requestBody: Ppomodoro
    ): Call<PpomodorosResponse>

    @DELETE("/ppomodoros/{ppomodoroId}")
    fun DeletePpomodoro(
        @Path("ppomodoroId") ppomodoroId: Int
    ): Call<Void>

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
}


//package com.example.hackatonkuit.retrofit2
//
//import retrofit2.Call
//import retrofit2.http.GET
//import retrofit2.http.Query
//import retrofit2.http.Path
//
//interface RetrofitInterface {
//
//
//    @GET("/app/category")
//    fun requestCategories(
//    ): Call<List<Category>>
//
//    @GET("/menus/category/{category_id}")
//    fun requestMenuList(
//        @Path("category_id") category_id: Long
//    ): Call<List<MenuPreview>>
//
//    @GET("menus/{menu_id}")
//    fun requestMenu(
//        @Path("menu_id") menu_id: Long
//    ): Call<List<Menu>>
//
//
//    @GET("/menus")
//    fun requestMenus(
//        @Query("menu-status") menustatus: String
//    ): Call<List<NewMenu>>
//
//    @GET("/orderItems")
//    fun requestCarts(
//        @Query("memberId") memberId: Long
//    ): Call<List<CartItem>>
//
//
//
////    fun requestFriendsData(): Call<FriendsData>
////
////    @POST("/friendship")
////    fun addFriend(
////        @Body email: FriendEmailData
////    ): Call<AddFriend>
//
//}