package com.raihan.assignment.ui.event

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

//// --- Model Data ---
//data class EventModel(
//    val id: String,
//    val name: String,
//    val startDateTime: String,
//    val endDateTime: String? = null, // Hanya untuk event utama
//    val organizer: String,
//    val location: String,
//    val description: String? = null, // Hanya untuk event utama
//    val isMainEvent: Boolean = false
//)
//
//// --- Warna Kustom sesuai mockup ---
//val PrimaryBlue = Color(0xFF3B68FF)
//val DangerRed = Color(0xFFD32F2F)
//val TextGray = Color(0xFF757575)
//val DividerGray = Color(0xFFE0E0E0)
//
//@Composable
//fun EventDuplicateScreen(
//    events: List<EventModel>,
//    onLogoutClick: () -> Unit = {},
//    onNewEventClick: () -> Unit = {}
//) {
//    Scaffold(
//        containerColor = Color.White,
//        bottomBar = {
//            StickyBottomBar(
//                onLogoutClick = onLogoutClick,
//                onNewEventClick = onNewEventClick
//            )
//        }
//    ) { innerPadding ->
//        if (events.isEmpty()) {
//            EmptyStateView(modifier = Modifier.padding(innerPadding))
//        } else {
//            val mainEvent = events.firstOrNull { it.isMainEvent }
//            val otherEvents = events.filter { !it.isMainEvent }
//
//            LazyColumn(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .padding(innerPadding)
//                    .padding(horizontal = 16.dp),
//                contentPadding = PaddingValues(top = 24.dp, bottom = 24.dp)
//            ) {
//                // Judul Event Hub tetap di dalam halaman utama
//                item {
//                    Text(
//                        text = "Event Hub",
//                        fontSize = 28.sp,
//                        fontWeight = FontWeight.ExtraBold,
//                        color = Color.Black,
//                        modifier = Modifier.padding(bottom = 16.dp)
//                    )
//                }
//
//                // Memanggil section Main Event
//                if (mainEvent != null) {
//                    item {
//                        MainEventSection(event = mainEvent)
//                        Spacer(modifier = Modifier.height(24.dp))
//                    }
//                }
//
//                // Memanggil section Other Events (Extension LazyListScope)
//                otherEventsSection(events = otherEvents)
//            }
//        }
//    }
//}
//
//@Composable
//fun StickyBottomBar(onLogoutClick: () -> Unit, onNewEventClick: () -> Unit) {
//    Surface(
//        color = Color.White,
//        modifier = Modifier.fillMaxWidth()
//    ) {
//        Row(
//            modifier = Modifier
//                .fillMaxWidth()
//                //.padding(horizontal = 16.dp, vertical = 16.dp),
//                .padding(start = 16.dp, top = 20.dp, bottom = 12.dp, end = 4.dp),
//            horizontalArrangement = Arrangement.SpaceBetween,
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            TextButton(onClick = onLogoutClick, colors = ButtonDefaults.buttonColors(containerColor = Color(
//                0xFFFFFFFF
//            )
//            )) {
//                Icon(
//                    imageVector = Icons.AutoMirrored.Filled.ExitToApp,
//                    contentDescription = "Logout",
//                    tint = Color(0xFFD32F2F),
//                    modifier = Modifier.size(20.dp)
//                )
//                Spacer(modifier = Modifier.width(8.dp))
//                Text(text = "Logout", color = Color(0xFFD32F2F), fontWeight = FontWeight.Bold)
//            }
//
//            Spacer(modifier = Modifier.width(32.dp))
//
//            Button(
//                onClick = onNewEventClick,
//                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1644EF)),
//                //shape = RoundedCornerShape(24.dp),
//                shape = RoundedCornerShape(
//                    topStart = 32.dp,
//                    bottomStart = 32.dp,
//                    topEnd = 0.dp,
//                    bottomEnd = 0.dp
//                ),
//                modifier = Modifier
//                    .weight(1f) // Membuat tombol memanjang mengisi sisa ruang ke kanan
//                    .height(56.dp), // Menyesuaikan tinggi agar proporsional seperti di gambar
//                contentPadding = PaddingValues(horizontal = 24.dp, vertical = 12.dp)
//            ) {
//                Icon(Icons.Default.Add, contentDescription = "Add", tint = Color.White)
//                Spacer(modifier = Modifier.width(8.dp))
//                Text(text = "New Event", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
//            }
//        }
//    }
//}
//
//@Composable
//fun EmptyStateView(modifier: Modifier = Modifier) {
//    Box(
//        modifier = modifier.fillMaxSize(),
//        contentAlignment = Alignment.Center
//    ) {
//        Text(
//            text = "No events available at the moment.\nClick 'New Event' to create one!",
//            color = Color(0xFF757575),
//            textAlign = TextAlign.Center,
//            fontSize = 16.sp
//        )
//    }
//}
//
//// --- Preview untuk Testing ---
//@Preview(showBackground = true)
//@Composable
//fun EventDuplicateScreenPreview() {
//    val dummyEvents = listOf(
//        EventModel(
//            id = "1",
//            name = "Tech Conference 2025",
//            startDateTime = "Oct 15, 2025, 1:00 PM",
//            endDateTime = "Oct 16, 2025, 5:00 PM",
//            organizer = "Technovate, Inc.",
//            location = "Conference Room A",
//            description = "Join us at the Tech Conference hosted by Technovate, Inc., where innovation meets inspiration! Discover the latest trends in technology, network with industry leaders, and participate in engaging workshops. Don't miss this opportunity to elevate your tech knowledge!",
//            isMainEvent = true
//        ),
//        EventModel(
//            id = "2",
//            name = "Web Development Workshop",
//            startDateTime = "Apr 20, 2026, 1:00 PM",
//            organizer = "Innovation Hub",
//            location = "Room 202"
//        ),
//        EventModel(
//            id = "3",
//            name = "UI/UX Masterclass",
//            startDateTime = "May 05, 2026, 10:00 AM",
//            organizer = "Design Thinkers",
//            location = "Studio B"
//        )
//    )
//
//    MaterialTheme {
//        EventDuplicateScreen(events = dummyEvents)
//    }
//}