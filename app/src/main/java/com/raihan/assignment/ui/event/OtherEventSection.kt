package com.raihan.assignment.ui.event

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.raihan.assignment.data.model.Event

// Extension function agar list ini menyatu dengan LazyColumn utama
fun LazyListScope.otherEventsSection(events: List<Event>) {
    if (events.isEmpty()) return

    item {
        Text(
            text = "Other events",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF757575),
            modifier = Modifier.padding(bottom = 12.dp)
        )
    }

    items(events) { event ->
        OtherEventCard(event = event)
        Spacer(modifier = Modifier.height(12.dp))
    }
}

@Composable
fun OtherEventCard(event: Event) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, Color(0xFFE0E0E0), RoundedCornerShape(12.dp))
            .background(Color.White, RoundedCornerShape(12.dp))
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top
        ) {
            // Kolom Kiri
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = event.name,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = event.organizer,
                    fontSize = 14.sp,
                    color = Color(0xFF757575)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Kolom Kanan
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = event.startDateTime,
                    fontSize = 14.sp,
                    color = Color(0xFF757575),
                    textAlign = TextAlign.End
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = event.location,
                    fontSize = 14.sp,
                    color = Color(0xFF757575),
                    textAlign = TextAlign.End
                )
            }
        }
    }
}