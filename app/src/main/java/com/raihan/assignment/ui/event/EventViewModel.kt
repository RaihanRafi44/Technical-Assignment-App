package com.raihan.assignment.ui.event

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raihan.assignment.data.mapper.toDomain
import com.raihan.assignment.data.model.Event
import com.raihan.assignment.data.repository.EventRepository
import com.raihan.assignment.data.source.local.database.entity.EventEntity
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class EventViewModel(private val repository: EventRepository) : ViewModel() {

    val eventList: StateFlow<List<Event>> = repository.getAllEvents().map { entities ->

        // Waktu saat ini dalam milidetik
        val currentTime = System.currentTimeMillis()

        // Mencari "Closest Upcoming Event"
        val upcomingMainEvent = entities.firstOrNull { it.startTimestamp >= currentTime }

        // 3. Mapping data ke UI Model
        val mappedList = entities.map { entity ->
            entity.toDomain(
                // Jadikan Main Event HANYA JIKA event ini adalah upcomingMainEvent
                isMainEvent = upcomingMainEvent != null && entity.id == upcomingMainEvent.id
            )
        }

        mappedList
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList<Event>()
    )

    fun addEvent(
        name: String, description: String, location: String,
        startDate: String, startTime: String, endDate: String, endTime: String,
        organizer: String, imageUri: String, startTimestamp: Long
    ) {
        viewModelScope.launch {
            val newEvent = EventEntity(
                name = name,
                description = description,
                location = location,
                startDate = startDate,
                startTime = startTime,
                endDate = endDate,
                endTime = endTime,
                organizer = organizer,
                thumbnailUri = imageUri,
                startTimestamp = startTimestamp
            )
            repository.createEvent(newEvent)
        }
    }
}