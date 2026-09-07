package com.raihan.assignment.ui.event

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.raihan.assignment.data.model.Event

@Composable
fun EventScreen(
    events: List<Event>,
    viewModel: EventViewModel,
    onConfirmLogout: () -> Unit = {},
    onNewEventClick: () -> Unit = {}
) {
    // State untuk mengontrol visibilitas bottom sheet
    var showLogoutSheet by remember { mutableStateOf(false) }
    var showNewEventSheet by remember { mutableStateOf(false) }

    Scaffold(
        containerColor = Color.White,
        bottomBar = {
            StickyBottomBar(
                onLogoutClick = { showLogoutSheet = true},
                onNewEventClick = { showNewEventSheet = true}
            )
        },
    ) { innerPadding ->
        if (events.isEmpty()) {
            EmptyStateView(modifier = Modifier.padding(innerPadding))
        } else {
            val mainEvent = events.firstOrNull { it.isMainEvent }
            val otherEvents = events.filter { !it.isMainEvent }

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(horizontal = 16.dp),
                contentPadding = PaddingValues(top = 24.dp, bottom = 24.dp)
            ) {
                // Judul Event Hub
                item {
                    Text(
                        text = "Event Hub",
                        fontSize = 28.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.Black,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                }

                // Memanggil section Main Event
                if (mainEvent != null) {
                    item {
                        MainEventSection(event = mainEvent)
                        Spacer(modifier = Modifier.height(24.dp))
                    }
                }

                // Memanggil section Other Events (Extension LazyListScope)
                otherEventsSection(events = otherEvents)
            }
        }
    }

    // Bottom sheet logout jika state-nya true
    if (showLogoutSheet) {
        LogoutConfirmationBottomSheet(
            onDismiss = {
                // Tutup sheet jika tombol cancel atau area luar diklik
                showLogoutSheet = false
            },
            onConfirmLogout = {
                // Aksi logout, lalu tutup sheet
                onConfirmLogout()
                showLogoutSheet = false
            }
        )
    }

    // Bottom sheet new event jika state-nya true
    if (showNewEventSheet) {
        NewEventBottomSheet(
            onDismiss = { showNewEventSheet = false },
            onSubmit = { name, desc, loc, startDate, startTime, endDate, endTime, organizer, imgUri, timestamp ->
                // Panggil ViewModel untuk save data ke Room
                viewModel.addEvent(
                    name, desc, loc, startDate, startTime, endDate, endTime, organizer, imgUri, timestamp
                )
                showNewEventSheet = false
            }
        )
    }
}

@Composable
fun StickyBottomBar(onLogoutClick: () -> Unit, onNewEventClick: () -> Unit) {
    Surface(
        color = Color.White,
        modifier = Modifier
            .fillMaxWidth()
            .navigationBarsPadding(),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, top = 20.dp, bottom = 12.dp, end = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextButton(onClick = onLogoutClick, colors = ButtonDefaults.buttonColors(containerColor = Color(
                0xFFFFFFFF
            )
            )) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                    contentDescription = "Logout",
                    tint = Color(0xFFD32F2F),
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "Logout", color = Color(0xFFD32F2F), fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.width(32.dp))

            Button(
                onClick = onNewEventClick,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1644EF)),
                shape = RoundedCornerShape(
                    topStart = 32.dp,
                    bottomStart = 32.dp,
                    topEnd = 0.dp,
                    bottomEnd = 0.dp
                ),
                modifier = Modifier
                    .weight(1f) // Membuat tombol memanjang mengisi sisa ruang ke kanan
                    .height(56.dp),
                contentPadding = PaddingValues(horizontal = 24.dp, vertical = 12.dp)
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add", tint = Color.White)
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "New Event", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
            }
        }
    }
}

@Composable
fun EmptyStateView(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "No events available at the moment.\nClick 'New Event' to create one!",
            color = Color(0xFF757575),
            textAlign = TextAlign.Center,
            fontSize = 16.sp
        )
    }
}
