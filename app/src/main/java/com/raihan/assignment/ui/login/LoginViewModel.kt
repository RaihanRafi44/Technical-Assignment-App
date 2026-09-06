package com.raihan.assignment.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raihan.assignment.data.model.Login
import com.raihan.assignment.data.repository.LoginRepository
import com.raihan.assignment.utils.ResultWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import okhttp3.Dispatcher

class LoginViewModel(private val repository: LoginRepository) : ViewModel() {

    private val _loginResult = MutableStateFlow<ResultWrapper<Login>>(ResultWrapper.Idle())
    val loginResult: StateFlow<ResultWrapper<Login>> = _loginResult.asStateFlow()

    fun doLogin(username: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.doLogin(username, password).collect {
                _loginResult.value = it
            }
        }
    }

    fun resetLoginState() {
        _loginResult.value = ResultWrapper.Idle()
    }
}
