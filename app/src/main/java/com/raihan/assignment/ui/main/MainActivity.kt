package com.raihan.assignment.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.raihan.assignment.ui.event.EventScreen
import com.raihan.assignment.ui.login.LoginScreen
import com.raihan.assignment.ui.theme.RaihanAssignmentAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RaihanAssignmentAppTheme {
                var currentScreen by remember { mutableStateOf("login") }

                if (currentScreen == "login") {
                    LoginScreen(onLoginSuccess = {
                        currentScreen = "event"
                    })
                } else {
                    EventScreen(onLogout = {
                        currentScreen = "login"
                    })
                }
            }
        }
    }
}
