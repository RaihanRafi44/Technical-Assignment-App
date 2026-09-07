package com.raihan.assignment.data.mapper

import com.raihan.assignment.data.model.Event
import com.raihan.assignment.data.source.local.database.entity.EventEntity

fun EventEntity.toDomain(isMainEvent: Boolean = false): Event {
    return Event(
        id = this.id.toString(),
        name = this.name,
        startDateTime = "${this.startDate}, ${this.startTime}",
        endDateTime = "${this.endDate}, ${this.endTime}",
        organizer = this.organizer,
        location = this.location,
        description = this.description,
        imageUri = this.thumbnailUri, // Tambahkan properti ini di EventModel Anda
        isMainEvent = isMainEvent
    )
}