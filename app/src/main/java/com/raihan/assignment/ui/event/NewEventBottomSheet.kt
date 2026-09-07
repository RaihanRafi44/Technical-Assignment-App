package com.raihan.assignment.ui.event

import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewEventBottomSheet(
    onDismiss: () -> Unit,
    onSubmit: (
        name: String, desc: String, loc: String,
        startDate: String, startTime: String,
        endDate: String, endTime: String,
        organizer: String, imageUri: String,
        startTimestamp: Long) -> Unit
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

    // State untuk DatePicker
    var showDateRangePicker by remember { mutableStateOf(false) }

    val dateFormatter = remember { SimpleDateFormat("dd MMM yyyy", Locale.getDefault()) }

    // State untuk TimePicker
    var showStartTimePicker by remember { mutableStateOf(false) }
    var showEndTimePicker by remember { mutableStateOf(false) }

    // State untuk URI gambar (Thumbnail)
    var imageUri by remember { mutableStateOf<Uri?>(null) }

    // Launcher untuk membuka galeri/file manager bawaan device
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imageUri = uri
    }

    val context = LocalContext.current

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = Color(0xFF3B68FF), // Background biru sesuai gambar
        dragHandle = null, // Menghilangkan garis drag handle bawaan
        //modifier = Modifier.fillMaxHeight(0.9f) // Memastikan sheet cukup tinggi
    ) {
        Column(
            modifier = Modifier
                //.fillMaxSize()
                .fillMaxWidth()
                .fillMaxHeight(0.95f)
                .padding(top = 16.dp, start = 16.dp, end = 16.dp, bottom = 40.dp)
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
                //modifier = Modifier.fillMaxSize()
                modifier = Modifier
                    .fillMaxWidth()
                    //.weight(1f)
                    .weight(1f, fill = false)
            ) {
                Column(
                    modifier = Modifier
                        .padding(24.dp)
                        //.padding(start = 24.dp, top = 24.dp, end = 24.dp, bottom = 16.dp)
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
                            modifier = Modifier
                                .weight(1.5f)
                                .clickable { showDateRangePicker = true },
                            leadingIcon = {
                                Icon(Icons.Outlined.DateRange, contentDescription = "Date", tint = Color.Gray)
                            },
                            readOnly = true,
                            enabled = false // Workaround to make the whole field clickable
                        )

                        FormTextField(
                            value = startTime,
                            onValueChange = { startTime = it },
                            placeholder = "Time",
                            modifier = Modifier
                                .weight(1f)
                                .clickable { showStartTimePicker = true }, // Buka picker saat diklik
                            readOnly = true, // Agar tidak memunculkan keyboard
                            enabled = false
                        )
                    }

                    // Row End Date & Time
                    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        FormTextField(
                            value = endDate,
                            onValueChange = { endDate = it },
                            placeholder = "End Date",
                            modifier = Modifier
                                .weight(1.5f)
                                .clickable { showDateRangePicker = true },
                            leadingIcon = {
                                Icon(Icons.Outlined.DateRange, contentDescription = "Date", tint = Color.Gray)
                            },
                            readOnly = true,
                            enabled = false // Workaround to make the whole field clickable
                        )

                        FormTextField(
                            value = endTime,
                            onValueChange = { endTime = it },
                            placeholder = "Time",
                            modifier = Modifier
                                .weight(1f)
                                .clickable { showEndTimePicker = true }, // ✅ Buka picker saat diklik
                            readOnly = true, // ✅ Agar tidak memunculkan keyboard
                            enabled = false
                        )
                    }

                    FormTextField(value = organizer, onValueChange = { organizer = it }, placeholder = "Organizer")

                    // Field Upload Thumbnail (Klik untuk membuka galeri)
//                    Box(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .height(160.dp)
//                            .clip(RoundedCornerShape(12.dp))
//                            .background(Color(0xFFF5F5F5))
//                            .clickable { galleryLauncher.launch("image/*") } // Trigger intent galeri
//                            .padding(16.dp)
//                    ) {
//                        Row(verticalAlignment = Alignment.CenterVertically) {
//                            Icon(
//                                imageVector = Icons.Outlined.Image,
//                                contentDescription = "Upload",
//                                tint = Color.Gray
//                            )
//                            Spacer(modifier = Modifier.width(12.dp))
//                            Text(
//                                text = if (imageUri != null) "Image Selected!" else "Upload Event Thumbnail",
//                                color = if (imageUri != null) Color.Black else Color.Gray,
//                                fontSize = 16.sp
//                            )
//                        }
//                    }

                    if (imageUri != null) {
                        // 1. Tampilkan Preview Gambar jika imageUri tidak null
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(160.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .clickable { galleryLauncher.launch("image/*") }
                        ) {
                            AsyncImage(
                                model = imageUri,
                                contentDescription = "Event Thumbnail Preview",
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )

                            // (Opsional) Tambahkan teks/overlay kecil agar user tahu gambar bisa diganti
                            Box(
                                modifier = Modifier
                                    .align(Alignment.BottomCenter)
                                    .fillMaxWidth()
                                    .background(Color.Black.copy(alpha = 0.5f))
                                    .padding(vertical = 6.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "Tap to change image",
                                    color = Color.White,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                        }
                    } else {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(12.dp))
                                .background(Color(0xFFF5F5F5))
                                .clickable { galleryLauncher.launch("image/*") }
                                .padding(16.dp),
                            //contentAlignment = Alignment.Center // Pusatkan konten ke tengah
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Outlined.Image,
                                    contentDescription = "Upload",
                                    tint = Color.Gray
                                )
                                Spacer(modifier = Modifier.width(12.dp))
                                Text(
                                    text = "Upload Event Thumbnail",
                                    color = Color.Gray,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // Tombol Submit
                    Button(
                        onClick = {
                            // Kalkulasi timestamp untuk pengurutan
                            val format = SimpleDateFormat("dd MMM yyyy hh:mm a", Locale.getDefault())
                            val dateString = "$startDate $startTime"
                            val startTimestamp = try {
                                format.parse(dateString)?.time ?: 0L
                            } catch (e: Exception) {
                                0L
                            }

                            // Salin gambar ke internal storage dan dapatkan URI permanennya
                            val permanentImageUri = imageUri?.let { uri ->
                                saveImageToInternalStorage(context, uri)
                            } ?: ""

                            onSubmit(
                                eventName, description, location,
                                startDate, startTime, endDate, endTime,
                                organizer, permanentImageUri,
                                startTimestamp
                            )
                        },
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

    // Date Range Picker Dialog
    if (showDateRangePicker) {
        DateRangePickerModal(
            onDateRangeSelected = { startMillis, endMillis ->
                if (startMillis != null) {
                    startDate = dateFormatter.format(Date(startMillis))
                }
                if (endMillis != null) {
                    endDate = dateFormatter.format(Date(endMillis))
                } else if (startMillis != null) {
                    // If only start date is selected, set end date to the same
                    //endDate = dateFormatter.format(Date(startMillis))
                    endDate = ""
                }
                showDateRangePicker = false
            },
            onDismiss = { showDateRangePicker = false }
        )
    }

    // Start Time Picker Dialog
    if (showStartTimePicker) {
        TimePickerModal(
            onTimeSelected = { time ->
                startTime = time
                showStartTimePicker = false
            },
            onDismiss = { showStartTimePicker = false }
        )
    }

    // End Time Picker Dialog
    if (showEndTimePicker) {
        TimePickerModal(
            onTimeSelected = { time ->
                endTime = time
                showEndTimePicker = false
            },
            onDismiss = { showEndTimePicker = false }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateRangePickerModal(
    onDateRangeSelected: (Long?, Long?) -> Unit,
    onDismiss: () -> Unit
) {
    val dateRangePickerState = rememberDateRangePickerState()

    // Formatter khusus untuk headline agar formatnya rapi
    val dateFormatter = remember { SimpleDateFormat("d MMM yyyy", Locale.getDefault()) }

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                onDateRangeSelected(
                    dateRangePickerState.selectedStartDateMillis,
                    dateRangePickerState.selectedEndDateMillis
                )
                onDismiss()
            }) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    ) {
        DateRangePicker(
            state = dateRangePickerState,
            showModeToggle = true,
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 400.dp, max = 600.dp)
                .padding(bottom = 16.dp),

            // 1. Kustomisasi Title (Bagian "Pilih tanggal")
            title = {
                Text(
                    text = "Pilih tanggal",
                    modifier = Modifier.padding(start = 24.dp, top = 16.dp, end = 24.dp, bottom = 4.dp),
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            },

            // 2. Kustomisasi Headline (Bagian "7 Sep 2026 - 9 Sep 2026")
            headline = {
                val start = dateRangePickerState.selectedStartDateMillis?.let {
                    SimpleDateFormat("d MMM yyyy", Locale.getDefault()).format(Date(it))
                } ?: "Start date"

                val end = dateRangePickerState.selectedEndDateMillis?.let {
                    SimpleDateFormat("d MMM yyyy", Locale.getDefault()).format(Date(it))
                } ?: "End date"

                Text(
                    text = "$start - $end",
                    modifier = Modifier.padding(start = 24.dp, end = 12.dp, bottom = 12.dp),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 1
                )
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimePickerModal(
    onTimeSelected: (String) -> Unit,
    onDismiss: () -> Unit
) {
    val currentTime = java.util.Calendar.getInstance()
    val timePickerState = rememberTimePickerState(
        initialHour = currentTime.get(java.util.Calendar.HOUR_OF_DAY),
        initialMinute = currentTime.get(java.util.Calendar.MINUTE),
        is24Hour = false // ✅ Ubah ke false agar tombol AM/PM muncul seperti di gambar
    )

    AlertDialog(
        onDismissRequest = onDismiss,
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        },

        confirmButton = {
            TextButton(onClick = {
                val hour24 = timePickerState.hour
                val minute = timePickerState.minute

                // Tentukan AM atau PM
                val isPm = hour24 >= 12
                val amPm = if (isPm) "PM" else "AM"

                // Konversi jam 0 atau 13-23 menjadi format 1-12
                val hour12 = if (hour24 % 12 == 0) 12 else hour24 % 12

                // Format hasil akhir menjadi "07:00 PM"
                val formattedTime = String.format(
                    Locale.getDefault(),
                    "%02d:%02d %s",
                    hour12,
                    minute,
                    amPm
                )
                onTimeSelected(formattedTime)
            }) {
                Text("OK")
            }
        },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Enter time",
                    modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
                    textAlign = TextAlign.Start,
                    fontSize = 14.sp
                )

                // Time picker
                TimeInput(
                    state = timePickerState,
                    colors = TimePickerDefaults.colors(
                        // Warna background AM/PM saat dipilih (Biru)
                        periodSelectorSelectedContainerColor = Color(0xFF3B68FF),
                        // Warna teks AM/PM saat dipilih (Putih agar kontras)
                        periodSelectorSelectedContentColor = Color.White,
                        // Warna garis tepi (border) kotak AM/PM
                        periodSelectorBorderColor = Color(0xFF3B68FF),

                        // (Opsional) Jika ingin warna kotak angka jam/menit ikut senada saat diklik:
                        timeSelectorSelectedContainerColor = Color(0xFFE0E8FF), // Biru sangat muda
                        timeSelectorSelectedContentColor = Color(0xFF3B68FF) // Teks biru tua
                    )
                )
            }
        }
    )
}

// Komponen Reusable untuk Text Field agar seragam dan rapi
@Composable
fun FormTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    modifier: Modifier = Modifier,
    leadingIcon: @Composable (() -> Unit)? = null,
    readOnly: Boolean = false,
    enabled: Boolean = true
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = { Text(text = placeholder, color = Color.Gray) },
        leadingIcon = leadingIcon,
        singleLine = true,
        readOnly = readOnly,
        enabled = enabled,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color(0xFFF5F5F5),
            unfocusedContainerColor = Color(0xFFF5F5F5),
            disabledContainerColor = Color(0xFFF5F5F5),
            focusedIndicatorColor = Color.Transparent, // Menghilangkan garis bawah default
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            cursorColor = Color.Black,
            disabledTextColor = Color.Black, // Ensure text is visible when disabled
            disabledPlaceholderColor = Color.Gray
        ),
        shape = RoundedCornerShape(12.dp),
        modifier = modifier
            .fillMaxWidth()
    )
}

fun saveImageToInternalStorage(context: Context, uri: Uri): String? {
    return try {
        val inputStream = context.contentResolver.openInputStream(uri) ?: return null
        // Buat nama file unik berdasarkan waktu
        val fileName = "event_thumbnail_${System.currentTimeMillis()}.jpg"
        val file = File(context.filesDir, fileName)

        val outputStream = FileOutputStream(file)

        // Salin data
        inputStream.copyTo(outputStream)

        // Tutup stream
        inputStream.close()
        outputStream.close()

        // Kembalikan URI lokal yang permanen (format: file:///...)
        Uri.fromFile(file).toString()
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}
