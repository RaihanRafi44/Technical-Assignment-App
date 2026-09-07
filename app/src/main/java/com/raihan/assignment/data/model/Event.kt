package com.raihan.assignment.data.model

data class Event(
    val id: String,
    val name: String,
    val startDateTime: String,
    val endDateTime: String? = null,
    val organizer: String,
    val location: String,
    val description: String? = null,
    val imageUri: String = "",
    val isMainEvent: Boolean = false
)
