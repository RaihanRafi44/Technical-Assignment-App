package com.raihan.assignment.ui.event

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.raihan.assignment.ui.event.otherEventsSection

//@Composable
//fun EventScreen(onLogout: () -> Unit) {
//    Column(
//        modifier = Modifier.fillMaxSize(),
//        verticalArrangement = Arrangement.Center,
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        Text(text = "Ini halaman utama event", fontSize = 20.sp)
//        Spacer(modifier = Modifier.height(24.dp))
//        Button(onClick = onLogout) {
//            Text(text = "Logout")
//        }
//    }
//}

// --- Model Data ---
data class EventModel(
    val id: String,
    val name: String,
    val startDateTime: String,
    val endDateTime: String? = null, // Hanya untuk event utama
    val organizer: String,
    val location: String,
    val description: String? = null, // Hanya untuk event utama
    val isMainEvent: Boolean = false
)

// --- Warna Kustom sesuai mockup ---
val PrimaryBlue = Color(0xFF3B68FF)
val DangerRed = Color(0xFFD32F2F)
val TextGray = Color(0xFF757575)
val DividerGray = Color(0xFFE0E0E0)

@Composable
fun EventScreen(
    events: List<EventModel>,
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
                // Judul Event Hub tetap di dalam halaman utama
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
                // Jalankan aksi logout, lalu tutup sheet
                onConfirmLogout()
                showLogoutSheet = false
            }
        )
    }

    // Bottom sheet new event jika state-nya true
    if (showNewEventSheet) {
        NewEventBottomSheet(
            onDismiss = { showNewEventSheet = false },
            onSubmit = { name, desc, loc ->
                // TODO: Lakukan aksi save/kirim ke ViewModel atau API di sini

                // Tutup sheet setelah submit
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
                //.padding(horizontal = 16.dp, vertical = 16.dp),
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
                //shape = RoundedCornerShape(24.dp),
                shape = RoundedCornerShape(
                    topStart = 32.dp,
                    bottomStart = 32.dp,
                    topEnd = 0.dp,
                    bottomEnd = 0.dp
                ),
                modifier = Modifier
                    .weight(1f) // Membuat tombol memanjang mengisi sisa ruang ke kanan
                    .height(56.dp), // Menyesuaikan tinggi agar proporsional seperti di gambar
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

// --- Preview untuk Testing ---
@Preview(showBackground = true)
@Composable
fun EventDuplicateScreenPreview() {
    val dummyEvents = listOf(
        EventModel(
            id = "1",
            name = "Tech Conference 2025",
            startDateTime = "Oct 15, 2025, 1:00 PM",
            endDateTime = "Oct 16, 2025, 5:00 PM",
            organizer = "Technovate, Inc.",
            location = "Conference Room A",
            description = "Join us at the Tech Conference hosted by Technovate, Inc., where innovation meets inspiration! Discover the latest trends in technology, network with industry leaders, and participate in engaging workshops. Don't miss this opportunity to elevate your tech knowledge!",
            isMainEvent = true
        ),
        EventModel(
            id = "2",
            name = "Web Development Workshop",
            startDateTime = "Apr 20, 2026, 1:00 PM",
            organizer = "Innovation Hub",
            location = "Room 202"
        ),
        EventModel(
            id = "3",
            name = "UI/UX Masterclass",
            startDateTime = "May 05, 2026, 10:00 AM",
            organizer = "Design Thinkers",
            location = "Studio B"
        )
    )

    MaterialTheme {
        EventScreen(events = dummyEvents)
    }
}
