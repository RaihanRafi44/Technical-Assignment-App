package com.raihan.assignment.ui.event

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MainEventSection(event: EventModel) {
    Column(modifier = Modifier.fillMaxWidth()) {
        // Thumbnail Image & Badge
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(Color.LightGray) // Placeholder sebelum gambar dari API dimuat
        ) {
            // Badge Upcoming
            Box(
                modifier = Modifier
                    .padding(12.dp)
                    .background(Color(0xFF3B68FF), shape = RoundedCornerShape(16.dp))
                    .padding(horizontal = 12.dp, vertical = 6.dp)
            ) {
                Text(
                    text = "Upcoming",
                    color = Color.White,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Event Details
        Text(
            text = event.name,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = "${event.startDateTime} - ${event.endDateTime ?: ""}",
            fontSize = 14.sp,
            color = Color(0xFF757575)
        )

        Text(
            text = "${event.organizer}  •  ${event.location}",
            fontSize = 14.sp,
            color = Color(0xFF757575)
        )

        Spacer(modifier = Modifier.height(8.dp))

        event.description?.let {
            Text(
                text = it,
                fontSize = 14.sp,
                color = Color.DarkGray,
                lineHeight = 20.sp
            )
        }
    }
}