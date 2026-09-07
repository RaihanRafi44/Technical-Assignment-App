package com.raihan.assignment.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.raihan.assignment.ui.event.EventScreen
import com.raihan.assignment.ui.event.EventViewModel
import com.raihan.assignment.ui.login.LoginScreen
import com.raihan.assignment.ui.theme.RaihanAssignmentAppTheme
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RaihanAssignmentAppTheme {
                var currentScreen by remember { mutableStateOf("login") }
                val eventViewModel: EventViewModel = koinViewModel()

                if (currentScreen == "login") {
                    LoginScreen(onLoginSuccess = {
                        currentScreen = "event"
                    })
                } else {
                    // Observe data dari StateFlow ViewModel
                    val events by eventViewModel.eventList.collectAsState()
                    EventScreen(
                        events = events, // Menambahkan parameter events yang dibutuhkan
                        viewModel = eventViewModel,
                        onConfirmLogout = {
                            currentScreen = "login"
                        },
                        onNewEventClick = {

                        }
                    )
                }
            }
        }
    }
}
