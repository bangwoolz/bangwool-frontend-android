package com.example.bangwool.ui.home

import com.google.gson.annotations.SerializedName

data class HomeItem (
    @SerializedName("taskColor") val taskColor: Int,
    @SerializedName("taskName") val taskName: String,
    @SerializedName("taskTime") val taskTime: String,
    @SerializedName("taskState") val taskState: Int
)