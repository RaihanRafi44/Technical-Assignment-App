package com.raihan.assignment.ui.event

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Image
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewEventBottomSheet(
    onDismiss: () -> Unit,
    onSubmit: (name: String, desc: String, loc: String) -> Unit // Sesuaikan parameter dengan kebutuhan
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    // State untuk form
    var eventName by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }
    var startDate by remember { mutableStateOf("") }
    var startTime by remember { mutableStateOf("") }
    var endDate by remember { mutableStateOf("") }
    var endTime by remember { mutableStateOf("") }
    var organizer by remember { mutableStateOf("") }

    // State untuk URI gambar (Thumbnail)
    var imageUri by remember { mutableStateOf<Uri?>(null) }

    // Launcher untuk membuka galeri/file manager bawaan device
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imageUri = uri
    }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = Color(0xFF3B68FF), // Background biru sesuai gambar
        dragHandle = null, // Menghilangkan garis drag handle bawaan
        modifier = Modifier.fillMaxHeight(0.9f) // Memastikan sheet cukup tinggi
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 16.dp, start = 16.dp, end = 16.dp)
                .navigationBarsPadding() // Melindungi dari tombol navigasi device
        ) {
            // Header (Judul & Tombol Close)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "New Event",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                IconButton(
                    onClick = onDismiss,
                    modifier = Modifier.align(Alignment.CenterEnd)
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close",
                        tint = Color.White
                    )
                }
            }

            // Kartu Putih Berisi Form
            Surface(
                shape = RoundedCornerShape(24.dp),
                color = Color.White,
                modifier = Modifier.fillMaxSize()
            ) {
                Column(
                    modifier = Modifier
                        .padding(24.dp)
                        .verticalScroll(rememberScrollState()), // Agar form bisa di-scroll jika layar kecil
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    FormTextField(value = eventName, onValueChange = { eventName = it }, placeholder = "Event Name")
                    FormTextField(value = description, onValueChange = { description = it }, placeholder = "Description")
                    FormTextField(value = location, onValueChange = { location = it }, placeholder = "Location")

                    // Row Start Date & Time
                    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        FormTextField(
                            value = startDate,
                            onValueChange = { startDate = it },
                            placeholder = "Start Date",
                            modifier = Modifier.weight(1f),
                            leadingIcon = {
                                Icon(Icons.Outlined.DateRange, contentDescription = "Date", tint = Color.Gray)
                            }
                        )
                        FormTextField(
                            value = startTime,
                            onValueChange = { startTime = it },
                            placeholder = "Time",
                            modifier = Modifier.weight(1f)
                        )
                    }

                    // Row End Date & Time
                    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        FormTextField(
                            value = endDate,
                            onValueChange = { endDate = it },
                            placeholder = "End Date",
                            modifier = Modifier.weight(1f),
                            leadingIcon = {
                                Icon(Icons.Outlined.DateRange, contentDescription = "Date", tint = Color.Gray)
                            }
                        )
                        FormTextField(
                            value = endTime,
                            onValueChange = { endTime = it },
                            placeholder = "Time",
                            modifier = Modifier.weight(1f)
                        )
                    }

                    FormTextField(value = organizer, onValueChange = { organizer = it }, placeholder = "Organizer")

                    // Field Upload Thumbnail (Klik untuk membuka galeri)
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color(0xFFF5F5F5))
                            .clickable { galleryLauncher.launch("image/*") } // Trigger intent galeri
                            .padding(16.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Outlined.Image,
                                contentDescription = "Upload",
                                tint = Color.Gray
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(
                                text = if (imageUri != null) "Image Selected!" else "Upload Event Thumbnail",
                                color = if (imageUri != null) Color.Black else Color.Gray,
                                fontSize = 16.sp
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // Tombol Submit
                    Button(
                        onClick = { onSubmit(eventName, description, location) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4B42DE)), // Warna ungu/biru gelap seperti mockup
                        shape = RoundedCornerShape(50)
                    ) {
                        Text(
                            text = "Submit Event",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                    }
                }
            }
        }
    }
}

// Komponen Reusable untuk Text Field agar seragam dan rapi
@Composable
fun FormTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    modifier: Modifier = Modifier,
    leadingIcon: @Composable (() -> Unit)? = null
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = { Text(text = placeholder, color = Color.Gray) },
        leadingIcon = leadingIcon,
        singleLine = true,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color(0xFFF5F5F5),
            unfocusedContainerColor = Color(0xFFF5F5F5),
            focusedIndicatorColor = Color.Transparent, // Menghilangkan garis bawah default
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            cursorColor = Color.Black
        ),
        shape = RoundedCornerShape(12.dp),
        modifier = modifier.fillMaxWidth()
    )
}