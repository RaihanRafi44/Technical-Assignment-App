package com.raihan.assignment.data.mapper

import com.raihan.assignment.data.model.Login
import com.raihan.assignment.data.source.network.model.LoginResponse

fun LoginResponse?.toLogin(): Login {
    return if (this?.code == "00") {
        Login(isSuccess = true, displayMessage = this.message)
    } else {
        Login(isSuccess = false, displayMessage = "Username or password incorrect.")
    }
}
