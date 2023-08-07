package com.example.bangwool.ui.home

import com.google.gson.annotations.SerializedName

data class HomeItem(
    @SerializedName("name") val name: String,
    @SerializedName("color") val color: String,
    @SerializedName("workHour") val workHour: Int,
    @SerializedName("workMin") val workMin: Int,
    @SerializedName("restTime") val restTime: Int,
    @SerializedName("id") val id: Int
)
