package com.raihan.assignment.data.repository

import com.raihan.assignment.data.mapper.toLogin
import com.raihan.assignment.data.model.Login
import com.raihan.assignment.data.source.network.model.LoginRequest
import com.raihan.assignment.data.source.network.service.AssignAppApiService
import com.raihan.assignment.utils.ResultWrapper
import com.raihan.assignment.utils.proceedFlow
import kotlinx.coroutines.flow.Flow

interface LoginRepository {
    fun doLogin(username: String, password: String): Flow<ResultWrapper<Login>>
}

class LoginRepositoryImpl(private val apiService: AssignAppApiService) : LoginRepository {
    override fun doLogin(username: String, password: String): Flow<ResultWrapper<Login>> {
        return proceedFlow { apiService.login(LoginRequest(username, password)).toLogin() }
    }
}
