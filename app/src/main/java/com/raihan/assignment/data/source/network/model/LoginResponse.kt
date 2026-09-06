package com.raihan.assignment.data.source.network.model

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("code")
    val code: String,
    @SerializedName("message")
    val message: String
)
